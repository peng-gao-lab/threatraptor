package query.tbql.executor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import org.postgresql.core.BaseConnection;
import query.tbql.executor.constraint.TBQLEventRelationConstraint;
import query.tbql.executor.dbadaptor.DBAdapter;
import query.tbql.executor.dbadaptor.Neo4jDBConnectionProvider;
import query.tbql.executor.dbadaptor.Neo4jDBProperties;
import query.tbql.executor.dbadaptor.PostgresDBConnectionProvider;
import query.tbql.executor.dbadaptor.PostgresDBProperties;
import query.tbql.executor.handler.TBQLExecutorErrorHandler;
import query.tbql.executor.handler.TBQLExecutorResultHandler;

class Key implements Comparable<Key> { // define a natural order
	private long id;
	private String hostName;

	Key() {}

	Key(long x, String y) {
		id = x;
		hostName = y;
	}

	public long getID() {
		return id;
	}

	public String getHostName() {
		return hostName;
	}

	public int compareTo(Key k) {
		if (this.id < k.getID()) {
			return -1;
		} else if (this.id > k.getID()) {
			return 1;
		} else {
			return this.hostName.compareTo(k.getHostName());
		}
	}
}

public class TBQLQueryContextExecutor {
	/*
	 * Each TBQLExecutor object takes a complete TBQLQueryContext object and
	 * execute the query. Results will be filled in the passed TBQLQueryContext object.
	 */

	// TBQLQueryContext object
	TBQLQueryContext tbqlQueryContext;

	// Headers of results of each individual SQL query, i.e., return types
	ArrayList<ArrayList<String>> returnHeaders;
	
	// Headers of filters extracted from event relationship constraints
	ArrayList<ArrayList<String>> filterHeaders;

	// Select prefix string for all contexts
	// Format: <id, hostname, filterHeaders.get(i)>, id is unique identifier for all events for a host
	ArrayList<String> selectPrefixHeaders;

	// SQL results ({<id, hostname> -> {vals}})
	// Note that sqlResultForReturn.get(i) are actual values of returnHeaders.get(i)
	ArrayList<HashMap<Key, ArrayList<String>>> queryResultForReturn;

	// Filter SQL results by event relationships
	// Note that sqlResultsForFilterHeaders.get(i) are actual values of filterHeaders.get(i) 
	ArrayList<HashMap<Key, ArrayList<String>>> queryResultsForFilter;

	// Filtered keys by relation
	ArrayList<ArrayList<Key>> satisfyingKeyGroups; // <SQL1key, SQL2key,
													// SQL3key> that satisfies
													// relation constraints

	
	TBQLExecutorResultHandler handler;
	TBQLExecutorErrorHandler errHandler;
	PostgresDBProperties postgresDbProperty;
	Neo4jDBProperties neo4jDbProperty;

	CopyOnWriteArrayList<BaseConnection> currentConnections = new CopyOnWriteArrayList<>();
	boolean cancelled = false;

	// Constructor
	public TBQLQueryContextExecutor(TBQLQueryContext tbqlQueryContext) {
		this.tbqlQueryContext = tbqlQueryContext;

		returnHeaders = new ArrayList<>();
		queryResultForReturn = new ArrayList<>();
		queryResultsForFilter = new ArrayList<>();
		filterHeaders = new ArrayList<>();
		satisfyingKeyGroups = new ArrayList<>();
		selectPrefixHeaders = new ArrayList<>();
	}

	public void setResultHandler(TBQLExecutorResultHandler handler) {
		this.handler = handler;
	}

	public void setErrorHandler(TBQLExecutorErrorHandler errHandler) {
		this.errHandler = errHandler;
	}
	
	public void setPostgresDbProperty(PostgresDBProperties postgresDbProperty) {
		this.postgresDbProperty = postgresDbProperty;
	}
	
	public void setNeo4jDbProperty(Neo4jDBProperties neo4jDbProperty) {
		this.neo4jDbProperty = neo4jDbProperty;
	}

	// Efficient query execution via relationship-based scheduling
	public void run() {
		// Prepare headers
		prepareQueryInfo();

		// Schedule an efficient execution plan of relationships
		ArrayList<TBQLEventRelationConstraint> efficientEvtRels = scheduleEventRelations();

		// Execute data queries
		executeDataQueries(efficientEvtRels);

		// Print out results
		printTBQLResults();
	}
	
	// Schedule the order of rels to make the execution efficient
	private ArrayList<TBQLEventRelationConstraint> scheduleEventRelations() {
		// Main intuitions: sort rels based on some precedence rules
		// 1) Attribute relationships before temporal relationships
		// 2) Attribute relationships with higher score(evt1)+score(evt2) in the front
		// 3) "=" attribute relationships before other attribute relationships
		// 4) Inside "=" attribute relationship, event with higher score is in the front
	
		ArrayList<TBQLEventRelationConstraint> sortedEvtRels = new ArrayList<>();
		sortedEvtRels.addAll(tbqlQueryContext.eventRelationConstraints);
		
		// Compute complexity score of each event in tbqlQueryContext.contexts
		HashMap<String, Integer> scores = new HashMap<>(); // event ID -> score
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
			scores.put(tbqlEventContext.eventID, tbqlEventContext.complexityScore);
		}
		
		// Insertion sort
		for (int i = 1; i < sortedEvtRels.size(); i++) {
			int j = i;
			while (j > 0) {
				// Decide whether A[j-1] > A[j]
				boolean swap = false;
				TBQLEventRelationConstraint evtRel1 = sortedEvtRels.get(j - 1);
				TBQLEventRelationConstraint evtRel2 = sortedEvtRels.get(j);
				boolean rel1IsTemporal = (evtRel1.getConstraintType().equals("temporalRelationConstraint"));
				boolean rel2IsTemporal = (evtRel2.getConstraintType().equals("temporalRelationConstraint"));
				int score1 = scores.get(evtRel1.getEventIDLeft()) + scores.get(evtRel1.getEventIDRight());
				int score2 = scores.get(evtRel2.getEventIDLeft()) + scores.get(evtRel2.getEventIDRight());
				boolean rel1IsEqual = (evtRel1.getRelation().equals("="));
				boolean rel2IsEqual = (evtRel2.getRelation().equals("="));

				if (rel1IsTemporal && rel2IsTemporal) {
					if (score1 < score2) {
						swap = true;
					}
				} else if (rel1IsTemporal && !rel2IsTemporal) { // rel1 > rel2
					swap = true;
				} else if (!rel1IsTemporal && rel2IsTemporal) { // rel1 < rel2
				} else {
					if (rel1IsEqual && rel2IsEqual) {
						if (score1 < score2) {
							swap = true;
						}
					} else if (rel1IsEqual && !rel2IsEqual) {
					} else if (!rel1IsEqual && rel2IsEqual) {
						swap = true;
					} else {
						if (score1 < score2) {
							swap = true;
						}
					}
				}

				// Swap if necessary
				if (swap) {
					sortedEvtRels.set(j - 1, evtRel2);
					sortedEvtRels.set(j, evtRel1);
				}

				// Update j
				j = j - 1;
			}
		}

		// For all rel that has "=", switch event IDs if necessary
		for (TBQLEventRelationConstraint evtRel : sortedEvtRels) {
			if (evtRel.getRelation().equals("=")) {
				if (scores.get(evtRel.getEventIDLeft()) < scores.get(evtRel.getEventIDRight())) {
					evtRel.swapLeftAndRight();
				}
			}
		}

		// Now, all rels in sortedRels are sorted, and order of event IDs in "=" rels are optimized.
		return sortedEvtRels;
	}

	// Prepare information about data queries
	private void prepareQueryInfo() {
		// Prepare:
		// 1) headers: SQL select
		// 2) metaSelect: key and filter information (key: starttime + agentID)
		// 3) sqlFiltersHeader: filter information (extracted from rels)
		
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
			// Get the header of the sql string. Store in headers ArrayList
			String currHeader = "";
			if (tbqlEventContext.synthesizeSQLQuery) {
				currHeader = tbqlEventContext.sqlQueryContext.getSelect().toString().replaceFirst("select ", "");
			}
			else {
				currHeader = tbqlEventContext.cypherQueryContext.getReturnStr().toString().replaceFirst("return ", "");
			}
			currHeader = currHeader.replace("\n", "");
			returnHeaders.add(new ArrayList<String>(Arrays.asList(currHeader.split(", "))));

			// Prepare metaSelectArray, sqlFiltersHeader
			String metaSelect = tbqlEventContext.eventID + ".id, " + tbqlEventContext.eventID + ".hostname, ";
			ArrayList<String> tmpFilterHeader = new ArrayList<>();

			for (TBQLEventRelationConstraint eventRelationConstraint : tbqlQueryContext.eventRelationConstraints) {
				if (eventRelationConstraint.getEventIDLeft().equals(tbqlEventContext.eventID)) {
					String currFilter = eventRelationConstraint.getEntityIDLeft() + "." + eventRelationConstraint.getEntityAttributeLeft();
					if (!tmpFilterHeader.contains(currFilter)) { // new filter
						tmpFilterHeader.add(currFilter);
						metaSelect += currFilter + ", ";
					}
				} else if (eventRelationConstraint.getEventIDRight().equals(tbqlEventContext.eventID)) {
					String currFilter = eventRelationConstraint.getEntityIDRight() + "." + eventRelationConstraint.getEntityAttributeRight();
					if (!tmpFilterHeader.contains(currFilter)) {
						tmpFilterHeader.add(currFilter);
						metaSelect += currFilter + ", ";
					}
				}
			}

			selectPrefixHeaders.add(metaSelect);
			filterHeaders.add(tmpFilterHeader);

			// Insert empty hashmap in sqlResults
			queryResultForReturn.add(new HashMap<Key, ArrayList<String>>());

			// Insert empty hashamp in sqlFilters
			queryResultsForFilter.add(new HashMap<Key, ArrayList<String>>());
		}
	}
	
	// Main method for query execution
	private void executeDataQueries(ArrayList<TBQLEventRelationConstraint> evtRels) {
		// Input: list of rels of which the order has been optimized
		// Functionality:
		// 1) Fill in sqlResults
		// 2) Fill in sqlFilters
		// 3) Fill in satisfyingKeyGroups
		
		HashMap<String, Integer> eventIDToContextIndex = new HashMap<>(); // event ID -> TBQLContext index in contexts
		for (int i = 0; i < tbqlQueryContext.eventPatternContexts.size(); i++) {
			eventIDToContextIndex.put(tbqlQueryContext.eventPatternContexts.get(i).eventID, i);
		}
		Set<String> eventIDForExecutedContexts = new TreeSet<>(); // set of event IDs of executed contexts
		HashMap<String, ArrayList<ArrayList<Key>>> tmpResults = new HashMap<>(); // 1\t2\t3\t -> list of key tuples
		
		// For each evtRel
		for (TBQLEventRelationConstraint evtRel : evtRels) {
			// Parse two event IDs
			String eventID1 = evtRel.getEventIDLeft();
			String eventID2 = evtRel.getEventIDRight();
			int eventID1ContextIndex = eventIDToContextIndex.get(eventID1);
			int eventID2ContextIndex = eventIDToContextIndex.get(eventID2);

			// Whether the two events have been executed (i.e., results are stored in sqlResults)
			boolean eventID1IsExecuted = eventIDForExecutedContexts.contains(eventID1);
			boolean eventID2IsExecuted = eventIDForExecutedContexts.contains(eventID2);

			// Different cases
			if (!eventID1IsExecuted && !eventID2IsExecuted) {
				// Both eventID1ContextIndex and eventID2ContextIndex are new
				
				// Execute eventID1 and eventID2, and store results
				if (!evtRel.getRelation().equals("=")) {
					// Plan: execute eventID1, eventID2 separately
					getQueryResultsContext(eventID1ContextIndex, "");
					getQueryResultsContext(eventID2ContextIndex, "");
				} else {
					// Plan: execute eventID1, use the results to execute eventID2
					// Execute eventID1
					boolean swap = false;
					if (eventID1.startsWith("evt") && eventID2.startsWith("evt")) {
						String eventnbr1str = eventID1.substring(3);
						String eventnbr2str = eventID2.substring(3);
						try {
							int eventnbr1 = Integer.parseInt(eventnbr1str);
							int eventnbr2 = Integer.parseInt(eventnbr2str);
							if (eventnbr1 > eventnbr2) {
								swap = true;
							}

						} catch (Exception e) {
							// skip switch event id index
						}
					}

					if (swap) {
						// Execute eventID2
						getQueryResultsContext(eventID2ContextIndex, "");

						// Prepare the filter string
						String filter = getContextFilterString(eventID2ContextIndex, evtRel, true, tmpResults);

						// Execute eventID1
						getQueryResultsContext(eventID1ContextIndex, filter);
					} else {
						// Execute eventID1
						getQueryResultsContext(eventID1ContextIndex, "");

						// Prepare the filter string
						String filter = getContextFilterString(eventID1ContextIndex, evtRel, true, tmpResults);

						// Execute eventID2
						getQueryResultsContext(eventID2ContextIndex, filter);
					}
				}

				// Now results of eventID1 and eventID2 are stored
				eventIDForExecutedContexts.add(eventID1);
				eventIDForExecutedContexts.add(eventID2);

				// Begin tuple matching
				ArrayList<ArrayList<Key>> tmpVal = new ArrayList<>();
				for (Key key1 : queryResultsForFilter.get(eventID1ContextIndex).keySet()) {
					for (Key key2 : queryResultsForFilter.get(eventID2ContextIndex).keySet()) {
						boolean match = tupleMatch(key1, eventID1ContextIndex, key2, eventID2ContextIndex, evtRel);

						if (match) { // If matches
							// Update tmpVal
							ArrayList<Key> tmpRow = new ArrayList<>(Arrays.asList(key1, key2));
							tmpVal.add(tmpRow);
						}
					}
				}

				// Update tmpVal to tmpResults
				String tmpIndexString = eventID1ContextIndex + "\t" + eventID2ContextIndex;
				tmpResults.put(tmpIndexString, tmpVal);
			} else if ((!eventID1IsExecuted && eventID2IsExecuted) || (eventID1IsExecuted && !eventID2IsExecuted)) {
				// Either eventID1ContextIndex or eventID2ContextIndex is new

				// Find out which is new
				int eventIDContextIndexNew;
				int eventIDContextIndexOld;
				if (!eventID1IsExecuted) {
					eventIDContextIndexNew = eventID1ContextIndex;
					eventIDContextIndexOld = eventID2ContextIndex;
				} else {
					eventIDContextIndexNew = eventID2ContextIndex;
					eventIDContextIndexOld = eventID1ContextIndex;
				}

				// Execute the TBQLContext that has "new" event context index
				if (!evtRel.getRelation().equals("=")) {
					getQueryResultsContext(eventIDContextIndexNew, "");
				} else { // Plan: use executed results to filter					
					// Prepare the filter string
					String filter = getContextFilterString(eventIDContextIndexOld, evtRel, false, tmpResults);

					// Execute new
					getQueryResultsContext(eventIDContextIndexNew, filter);
				}

				// Now results of eventIDContextIndexNew are stored
				if (eventIDToContextIndex.get(eventID1) == eventIDContextIndexNew) {
					eventIDForExecutedContexts.add(eventID1);
				} else {
					eventIDForExecutedContexts.add(eventID2);
				}
				
				// Begin tuple matching
				String indexStringOld = "";
				for (String indexString : tmpResults.keySet()) {
					if (Arrays.asList(indexString.split("\t")).contains(Integer.toString(eventIDContextIndexOld))) {
						indexStringOld = indexString;
						break;
					}
				}
				if (indexStringOld.equals("")) {
					errHandler.handleOtherError("Something wrong..");
				}
				
				ArrayList<ArrayList<Key>> tmpVal = new ArrayList<>();
				
				for (ArrayList<Key> keysOld : tmpResults.get(indexStringOld)) {
					for (Key keyNew : queryResultsForFilter.get(eventIDContextIndexNew).keySet()) {
						// Find the key in keysOld
						Key keyOld = keysOld.get(Arrays.asList(indexStringOld.split("\t"))
								.indexOf(Integer.toString(eventIDContextIndexOld)));

						// Filter
						Key key1; // key for eventID1ContextIndex
						Key key2;
						if (eventIDContextIndexNew == eventID1ContextIndex) {
							key1 = keyNew;
							key2 = keyOld;

						} else {
							key1 = keyOld;
							key2 = keyNew;
						}
						boolean match = tupleMatch(key1, eventID1ContextIndex, key2, eventID2ContextIndex, evtRel);
						
						if (match) { // If matches
							// Update tmpVal
							ArrayList<Key> tmpRow = new ArrayList<>();
							tmpRow.addAll(keysOld);
							tmpRow.add(keyNew);
							tmpVal.add(tmpRow);
						}
					}
				}
				
				// Update tmpVal to tmpResults
				tmpResults.remove(indexStringOld);
				String tmpIndexString = indexStringOld + "\t" + eventIDContextIndexNew;
				tmpResults.put(tmpIndexString, tmpVal);
			} else {
				// Both eventID1ContextIndex and eventID2ContextIndex are old (i.e., executed)

				ArrayList<ArrayList<Key>> tmpVal = new ArrayList<>();
				String indexString1 = "";
				String indexString2 = "";
				for (String indexString : tmpResults.keySet()) {
					if (Arrays.asList(indexString.split("\t")).contains(Integer.toString(eventID1ContextIndex))) {
						indexString1 = indexString;
						break;
					}
				}
				for (String indexString : tmpResults.keySet()) {
					if (Arrays.asList(indexString.split("\t")).contains(Integer.toString(eventID2ContextIndex))) {
						indexString2 = indexString;
						break;
					}
				}
				if (indexString1.equals("") || indexString2.equals("")) {
					errHandler.handleOtherError("Something wrong..");
				}
				// Notice that it is possible that indexString1 == indexString2

				// Begin tuple matching
				if (!indexString1.equals(indexString2)) { // merge the two with
															// tuple matching
					for (ArrayList<Key> keys1 : tmpResults.get(indexString1)) {
						for (ArrayList<Key> keys2 : tmpResults.get(indexString2)) {
							Key key1 = keys1.get(Arrays.asList(indexString1.split("\t"))
									.indexOf(Integer.toString(eventID1ContextIndex)));
							Key key2 = keys2.get(Arrays.asList(indexString2.split("\t"))
									.indexOf(Integer.toString(eventID2ContextIndex)));
							boolean match = tupleMatch(key1, eventID1ContextIndex, key2, eventID2ContextIndex, evtRel);

							if (match) {
								ArrayList<Key> tmpRow = new ArrayList<>();
								tmpRow.addAll(keys1);
								tmpRow.addAll(keys2);
								tmpVal.add(tmpRow);
							}
						}
					}

					// Update tmpVal to tmpResults
					tmpResults.remove(indexString1);
					tmpResults.remove(indexString2);
					String tmpIndexString = indexString1 + "\t" + indexString2;
					tmpResults.put(tmpIndexString, tmpVal);
				} else { // eventID1, eventID2 belong to the same group.
					for (ArrayList<Key> keys : tmpResults.get(indexString1)) {
						Key key1 = keys.get(Arrays.asList(indexString1.split("\t"))
								.indexOf(Integer.toString(eventID1ContextIndex)));
						Key key2 = keys.get(Arrays.asList(indexString1.split("\t"))
								.indexOf(Integer.toString(eventID2ContextIndex)));
						boolean match = tupleMatch(key1, eventID1ContextIndex, key2, eventID2ContextIndex, evtRel);

						if (match) {
							ArrayList<Key> tmpRow = new ArrayList<>();
							tmpRow.addAll(keys);
							tmpVal.add(tmpRow);
						}
					}

					// Update tmpVal to tmpResults
					tmpResults.remove(indexString1); // remove original key
														// tuples
					String tmpIndexString = indexString1;
					tmpResults.put(tmpIndexString, tmpVal); // add updated key
															// tuples
				}
			}
		}

		// Expand tmpResults to contain missing indices
		Set<Integer> existingIndexSet = new TreeSet<>();
		for (String indexString : tmpResults.keySet()) {
			for (String ind : Arrays.asList(indexString.split("\t"))) {
				existingIndexSet.add(Integer.parseInt(ind));
			}
		}
		if (existingIndexSet.size() < tbqlQueryContext.eventPatternContexts.size()) {
			for (int i = 0; i < tbqlQueryContext.eventPatternContexts.size(); i++) {
				if (!existingIndexSet.contains(i)) {
					// Execute the TBQLContext with index i
					getQueryResultsContext(i, "");

					// Create an entry in tmpResults
					ArrayList<ArrayList<Key>> tmp = new ArrayList<>();
					for (Key key : queryResultForReturn.get(i).keySet()) {
						ArrayList<Key> row = new ArrayList<>(Arrays.asList(key));
						tmp.add(row);
					}

					// Update tmp to tmpResults
					tmpResults.put(Integer.toString(i), tmp);
				}
			}
		}

		// Merge tmpResults
		while (tmpResults.size() > 1) {
			// Selet the first two indexString
			ArrayList<String> indexStringAll = new ArrayList<>();
			indexStringAll.addAll(tmpResults.keySet());
			String indexString1 = indexStringAll.get(0);
			String indexString2 = indexStringAll.get(1);		

			// Prepare newKey
			String indexStringNew = indexString1 + "\t" + indexString2;

			// Prepare newVal
			ArrayList<ArrayList<Key>> newVal = new ArrayList<>();
			for (ArrayList<Key> keys1 : tmpResults.get(indexString1)) {
				for (ArrayList<Key> keys2 : tmpResults.get(indexString2)) {
					ArrayList<Key> tmpRow = new ArrayList<>();
					tmpRow.addAll(keys1);
					tmpRow.addAll(keys2);
					newVal.add(tmpRow);
				}
			}

			// Update tmpResults
			tmpResults.remove(indexString1);
			tmpResults.remove(indexString2);
			tmpResults.put(indexStringNew, newVal);
		}

		// Now tmpResults contain everything, only one <index, val> pair
		ArrayList<String> indexStringAll = new ArrayList<>();
		indexStringAll.addAll(tmpResults.keySet());
		if (indexStringAll.size() != 1) {
			errHandler.handleOtherError("Something wrong...");
		}

		// Reorder indices
		ArrayList<Integer> indexList = new ArrayList<>();
		for (String ind : Arrays.asList(indexStringAll.get(0).split("\t"))) {
			indexList.add(Integer.parseInt(ind));
		}
		ArrayList<Integer> indexPositions = new ArrayList<>();
		for (int i = 0; i < tbqlQueryContext.eventPatternContexts.size(); i++) {
			if (!indexList.contains(i)) {
				errHandler.handleOtherError("Some index is not merged");
			}
			indexPositions.add(indexList.indexOf(i));
		}
		ArrayList<ArrayList<Key>> vals = tmpResults.get(indexStringAll.get(0));
		for (ArrayList<Key> row : vals) {
			// Store into satisfyingKeyGroups following the position
			ArrayList<Key> reorderdRow = new ArrayList<>();
			for (int pos : indexPositions) {
				reorderdRow.add(row.get(pos));
			}
			satisfyingKeyGroups.add(reorderdRow);
		}
	}
	
	private void getQueryResultsContext(int contextIndex, String filter) {
		// Synthesize either a SQL query or a Cypher query for execution
		
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts.get(contextIndex);
		if (tbqlEventContext.synthesizeSQLQuery) {
			getSQLResultsContext(contextIndex, filter);
		}
		else {
			getCypherResultsContext(contextIndex, filter);
		}
	}

	// Get SQL results of a specific TBQLEventContext
	private void getSQLResultsContext(int contextIndex, String filter) {
		// Execute SQL query of the specific TBQLContext
		// Input:
		// (1) contextIndex: index of the TBQLEventContext in contexts
		// (2) isMultithreading: control time window partition
		// (3) filter: xx in (...), will append to the end of "where"
		
		// DB information (dbName is stored in the tbqlContext object)
		String host = postgresDbProperty.getHost();
		String username = postgresDbProperty.getUser();
		String password = postgresDbProperty.getPassword();

		// Make a connection
		PostgresDBConnectionProvider provider = new PostgresDBConnectionProvider(postgresDbProperty);

		// For the TBQLContext object
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts.get(contextIndex);

		// Prepare meta information (i.e., results that are not specified in TBQL return)
		int numMetaFields = filterHeaders.get(contextIndex).size() + 2; // 2 is for id, hostname
		String selectPrefix = selectPrefixHeaders.get(contextIndex);

		// Prepare final SQL clause commands
		String selectCommand = tbqlEventContext.sqlQueryContext.getSelect().toString();
		selectCommand = new StringBuilder(selectCommand).insert(7, selectPrefix).toString();
		String fromCommand = tbqlEventContext.sqlQueryContext.getFrom().toString();
		String whereCommand = tbqlEventContext.sqlQueryContext.getWhere().toString();
		if (!filter.equals("")) {
			whereCommand = whereCommand + " and " + filter + "\n";
		}

		// Storage for SQL results
		ArrayList<ArrayList<String>> res = new ArrayList<>();

		// For each dbName -> timewindow
		try {
			for (String dbName : tbqlEventContext.dbNameToTimeWindowStr.keySet()) {
				synchronized (this) {
					if (!cancelled) {
						// Prepare the final SQL command
						String sqlCommand = selectCommand + fromCommand + whereCommand;
						if (!tbqlEventContext.dbNameToTimeWindowStr.get(dbName).equals("")) { // has timeWindowStr
							sqlCommand += " and " + tbqlEventContext.dbNameToTimeWindowStr.get(dbName);
						}
						
						// Create a connection to this DB
						Connection conn = provider.getConnectionPostgreSQL(host, dbName, username, password);
						if (conn.isWrapperFor(org.postgresql.core.BaseConnection.class)) {
							currentConnections.add((BaseConnection) conn);
						}
					
						// Retrieve results
						// Format: <id, hostname, filters, ...>
						ArrayList<ArrayList<String>> currRes = DBAdapter.executeQuery(conn, sqlCommand);
						
						// Store results in res
						for (ArrayList<String> row : currRes) {
							res.add(row);
						}

						// Close the connection
						conn.close();
						currRes.clear();
						currRes = null;
					}
				}
			}
		} catch (Exception e) {
			errHandler.handleOtherError("Fail to retrieve data from DB.", e);
		}

		// Parse the results for this TBQLContext object
		parseQueryResultsContext(res, numMetaFields, contextIndex);
	}
	
	// Get Cypher results of a specific TBQLEventContext
	private void getCypherResultsContext(int contextIndex, String filter) {
		// Execute Cypher query of the specific TBQLContext
		// Input:
		// (1) contextIndex: index of the TBQLEventContext in contexts
		// (2) filter: xx in (...), will append to the end of "where"
				
		// DB information (dbName is stored in the tbqlContext object)
		String host = neo4jDbProperty.getHost();
		String username = neo4jDbProperty.getUser();
		String password = neo4jDbProperty.getPassword();

		// Make a connection
		Neo4jDBConnectionProvider provider = new Neo4jDBConnectionProvider();

		// For the TBQLContext object
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts.get(contextIndex);

		// Prepare meta information (i.e., results that are not specified in TBQL return)
		int numMetaFields = filterHeaders.get(contextIndex).size() + 2; // 2 is for id, hostname
		String selectPrefix = selectPrefixHeaders.get(contextIndex);

		// Prepare final SQL clause commands
		String returnCommand = tbqlEventContext.cypherQueryContext.getReturnStr().toString();
		returnCommand = new StringBuilder(returnCommand).insert(7, selectPrefix).toString();
		String matchCommand = tbqlEventContext.cypherQueryContext.getMatch().toString();
		String whereCommand = tbqlEventContext.cypherQueryContext.getWhere().toString();
		if (!filter.equals("")) {
			// Change to square brackets
			whereCommand = whereCommand + " and " + DBAdapter.adoptToCypherFilterString(filter) + "\n";
		}
		
		// Storage for Cypher results
		ArrayList<ArrayList<String>> res = new ArrayList<>();

		// For each dbName -> timewindow
		try {
			for (String dbName : tbqlEventContext.dbNameToTimeWindowStr.keySet()) {
				synchronized (this) {
					if (!cancelled) {
						// Prepare the final SQL command
						String cypherCommand = matchCommand + whereCommand;
						if (!tbqlEventContext.dbNameToTimeWindowStr.get(dbName).equals("")) { // has timeWindowStr
							cypherCommand += " and " + tbqlEventContext.dbNameToTimeWindowStr.get(dbName) + "\n";
						}
						cypherCommand += returnCommand;

						// Create a connection to this DB
						Connection conn = provider.getConnectionNeo4j(host, "", username, password);
						if (conn.isWrapperFor(org.postgresql.core.BaseConnection.class)) {
							currentConnections.add((BaseConnection) conn);
						}

						// Retrieve results
						// Format: <id, hostname, filters, ...>
						ArrayList<ArrayList<String>> currRes = DBAdapter.executeQuery(conn, cypherCommand);
						
						// Store results in res
						for (ArrayList<String> row : currRes) {
							res.add(row);
						}

						// Close the connection
						conn.close();
						currRes.clear();
						currRes = null;
					}
				}
			}
		} catch (Exception e) {
			errHandler.handleOtherError("Fail to retrieve data from DB.", e);
		}

		// Parse the results for this TBQLContext object
		parseQueryResultsContext(res, numMetaFields, contextIndex);
	}

	public void cancelQueryExecutions() {
		synchronized (this) {
			cancelled = true;
			for (BaseConnection baseConnection : currentConnections) {
				try {
					baseConnection.cancelQuery();
				} catch (Exception e) {

				}
			}
		}
	}

	// Parse query (SQL, Cypher) results for the context
	private void parseQueryResultsContext(ArrayList<ArrayList<String>> res, int numMetaFields, int contextIndex) {
		// Format of res: id, hostname,..., (meta fields specified by the first numMetaColumns columns)
		// We want to use <id, hostname> as keys, and store meta fields into sqlFilters, and store remaining fields in sqlResults
		
		HashMap<Key, ArrayList<String>> currMap = new HashMap<>();
		HashMap<Key, ArrayList<String>> currFilter = new HashMap<>();
		for (ArrayList<String> row : res) {
			// Prepare the Key
			long id = Long.parseLong(row.get(0));
			String hostName = row.get(1);
			Key key = new Key(id, hostName);

			ArrayList<String> filter = new ArrayList<>();
			for (int i = 2; i < numMetaFields; i++) {
				// Other meta fields extracted from event relations
				filter.add(row.get(i));
			}
			currFilter.put(key, filter);

			ArrayList<String> val = new ArrayList<>();
			for (int i = numMetaFields; i < row.size(); i++) {
				val.add(row.get(i));
			}
			currMap.put(key, val);
		}

		if ((!queryResultForReturn.get(contextIndex).isEmpty()) || (!queryResultsForFilter.get(contextIndex).isEmpty())) {
			errHandler.handleOtherError("Context has been executed before. Cannot parse results to sqlResults and sqlFilters");
		}
		queryResultForReturn.set(contextIndex, currMap);
		queryResultsForFilter.set(contextIndex, currFilter);
	}
	
	// Get filter string
	private String getContextFilterString(int eventIDContextIndex, TBQLEventRelationConstraint evtRel, boolean bothNew,
			HashMap<String, ArrayList<ArrayList<Key>>> tmpResults) {
		// tmpResults is used to retrieve currently available key

		// Sanity check
		if (!evtRel.getRelation().equals("=")) {
			errHandler.handleOtherError("rel is not =: " + evtRel);
		}

		// Parse rel
		StringBuffer res = new StringBuffer();
		String fieldFilter;
		if (tbqlQueryContext.eventPatternContexts.get(eventIDContextIndex).eventID.equals(evtRel.getEventIDLeft())) {
			fieldFilter = evtRel.getEntityIDLeft() + "." + evtRel.getEntityAttributeLeft();
			res.append(evtRel.getEntityIDRight() + "." + evtRel.getEntityAttributeRight() + " in (");
		} 
		else {
			fieldFilter = evtRel.getEntityIDRight() + "." + evtRel.getEntityAttributeRight();
			res.append(evtRel.getEntityIDLeft() + "." + evtRel.getEntityAttributeLeft() + " in (");
		}

		HashSet<String> seenIds = new HashSet<>();
		// If both new (no available keys in tmpResults)
		if (bothNew) {
			// Prepare res
			// A slower version
			for (Key key : queryResultsForFilter.get(eventIDContextIndex).keySet()) {
				String val = queryResultsForFilter.get(eventIDContextIndex).get(key)
						.get(filterHeaders.get(eventIDContextIndex).indexOf(fieldFilter));
				if (seenIds.add(val)) {
					res.append(val + ", ");
				}
			}
		} else { // key of eventIDContextIndex is in tmpResults
			// Parse tmpResults
			String indexString = "";
			for (String str : tmpResults.keySet()) {
				if (Arrays.asList(str.split("\t")).contains(Integer.toString(eventIDContextIndex))) {
					indexString = str;
					break;
				}
			}
			if (indexString.equals("")) {
				errHandler.handleOtherError("tmpResults does not match eventIDContextIndex");
			}

			// A faster version (better to find a test case to test this)
			for (ArrayList<Key> keys : tmpResults.get(indexString)) {
				// Find the key
				Key key = keys
						.get(Arrays.asList(indexString.split("\t")).indexOf(Integer.toString(eventIDContextIndex)));
				String val = queryResultsForFilter.get(eventIDContextIndex).get(key)
						.get(filterHeaders.get(eventIDContextIndex).indexOf(fieldFilter));
				if (seenIds.add(val)) {
					res.append(val + ", ");
				}
			}
		}

		// Add bracket
		String resstr = res.toString();
		if(resstr.endsWith("(")){
			return "";
		}
		resstr = resstr.substring(0, resstr.length() - 2) + ")";
		
		// For Cypher, use square brackets		
		return resstr;
	}

	// Test whether the tuple indexed by keys match the relationship
	private boolean tupleMatch(Key key1, int eventID1ContextIndex, Key key2, int eventID2ContextIndex,
			TBQLEventRelationConstraint evtRel) {

		// Parse rel
		String attr1 = evtRel.getEntityIDLeft() + "." + evtRel.getEntityAttributeLeft();
		String attr2 = evtRel.getEntityIDRight() + "." + evtRel.getEntityAttributeRight();
		String opRel = evtRel.getRelation(); // may be attribute rel or temporal rel

		// Get field results
		String val1 = queryResultsForFilter.get(eventID1ContextIndex).get(key1)
				.get(filterHeaders.get(eventID1ContextIndex).indexOf(attr1));
		String val2 = queryResultsForFilter.get(eventID2ContextIndex).get(key2)
				.get(filterHeaders.get(eventID2ContextIndex).indexOf(attr2));

		// Match
		boolean match = false;
		if (!opRel.contains("\t")) { // attribute rel
			switch (opRel) {
			case "=":
				if (val1.equals(val2)) {
					match = true;
				}
				break;
			case "!=":
				if (!val1.equals(val2)) {
					match = true;
				}
				break;
			case "<":
				if (Double.parseDouble(val1) < Double.parseDouble(val2)) {
					match = true;
				}
				break;
			case ">":
				if (Double.parseDouble(val1) > Double.parseDouble(val2)) {
					match = true;
				}
				break;
			case "<=":
				if (Double.parseDouble(val1) <= Double.parseDouble(val2)) {
					match = true;
				}
				break;
			case ">=":
				if (Double.parseDouble(val1) >= Double.parseDouble(val2)) {
					match = true;
				}
				break;
			default:
				errHandler.handleOtherError("Undefined operation: " + opRel);
			}
		} else { // temporal rel
			String timeOp = opRel.split("\t")[0];
			long smallTimeUTC = Long.parseLong(opRel.split("\t")[1]);
			long largeTimeUTC = Long.parseLong(opRel.split("\t")[2]);
			long starttime1 = new BigDecimal(val1).longValue(); // neo4j returns scientific notation
			long starttime2 = new BigDecimal(val2).longValue();

			// timeOp could be "before", "after", "within"
			switch (timeOp) {
			case "before":
				if (key1.getHostName() == key2.getHostName() && smallTimeUTC == 0 && largeTimeUTC == Long.MAX_VALUE) {
					if (key1.getID() < key2.getID()) {
						match = true;
					}
					break;
				}

				if ((starttime2 - starttime1) > smallTimeUTC && (starttime2 - starttime1) < largeTimeUTC) {
					match = true;
				}
				break;
			case "after":
				if (key1.getHostName() == key2.getHostName() && smallTimeUTC == 0 && largeTimeUTC == Long.MAX_VALUE) {
					if (key1.getID() > key2.getID()) {
						match = true;
					}
					break;
				}

				if ((starttime1 - starttime2) > smallTimeUTC && (starttime1 - starttime2) < largeTimeUTC) {
					match = true;
				}
				break;
			case "within":
				long duration = Math.abs(starttime2 - starttime1);
				if (duration >= smallTimeUTC && duration <= largeTimeUTC) {
					match = true;
				}
				break;
			default:
				errHandler.handleOtherError("Undefined operation: " + opRel);
			}
		}

		return match;
	}
	
	// Print out TBQL query results if no subquery
	private void printTBQLResults() {
		// Considering that there are multiple number of columns in the
		// resulting table, we do not want to store them but rather print them out

		// Indexed by satisfying keys
		// Need to check returnEventIDs to eliminate results that are not to be returned

		// Each column should correspond to returnResults
		// Find the context indices based on contexts and returnEventIDs
		// Find the column indices based on headers, returnEventIDs, returnResults
		ArrayList<Integer> contextIndices = new ArrayList<>();
		ArrayList<Integer> columnIndices = new ArrayList<>();
		for (String eventID : tbqlQueryContext.returnEventIDs) {
			for (int i = 0; i < tbqlQueryContext.eventPatternContexts.size(); i++) {
				if (tbqlQueryContext.eventPatternContexts.get(i).eventID.equals(eventID)) {
					contextIndices.add(i);
					break;
				}
			}
		}
		for (String result : tbqlQueryContext.returnResults) {
			int currContextIndex = contextIndices.get(tbqlQueryContext.returnResults.indexOf(result));
			columnIndices.add(returnHeaders.get(currContextIndex).indexOf(result));
		}

		// Check conditions
		if (tbqlQueryContext.returnCount) { // count
			if (tbqlQueryContext.limitValue != -1) { // limit
				if (!tbqlQueryContext.returnDistinct) {
					handler.handleCount(Math.min(tbqlQueryContext.limitValue, satisfyingKeyGroups.size()));
				} else {
					HashSet<String> outputResults = new HashSet<>();
					for (ArrayList<Key> keys : satisfyingKeyGroups) {
						if (outputResults.size() == tbqlQueryContext.limitValue) {
							break;
						}
						String tmpOutput = "";
						for (int i = 0; i < columnIndices.size(); i++) {
							int currContextIndex = contextIndices.get(i);
							int currColumnIndex = columnIndices.get(i);
							Key currKey = keys.get(currContextIndex);
							String currResult = queryResultForReturn.get(currContextIndex).get(currKey)
									.get(currColumnIndex);
							tmpOutput += currResult + ", ";
						}
						outputResults.add(tmpOutput);
					}
					handler.handleCount(outputResults.size());
				}
				ArrayList<String> headers = new ArrayList<>();
				headers.add("Count");
				handler.handleHeader(headers);
			} else { // no limit
				if (!tbqlQueryContext.returnDistinct) { // distinct
					handler.handleCount(satisfyingKeyGroups.size());
				} else {
					// Find number of distinct rows
					HashSet<String> outputResults = new HashSet<>();
					for (ArrayList<Key> keys : satisfyingKeyGroups) {
						String tmpOutput = "";
						for (int i = 0; i < columnIndices.size(); i++) {
							int currContextIndex = contextIndices.get(i);
							int currColumnIndex = columnIndices.get(i);
							Key currKey = keys.get(currContextIndex);
							String currResult = queryResultForReturn.get(currContextIndex).get(currKey)
									.get(currColumnIndex);
							tmpOutput += currResult + ", ";
						}
						outputResults.add(tmpOutput);
					}
					handler.handleCount(outputResults.size());
				}
				ArrayList<String> headers = new ArrayList<>();
				headers.add("Count");
				handler.handleHeader(headers);
			}
		} else {
			if (tbqlQueryContext.limitValue != -1) { // limit
				if (tbqlQueryContext.returnDistinct) {
					HashSet<String> outputResults = new HashSet<>();
					for (ArrayList<Key> keys : satisfyingKeyGroups) {
						String tmpOutput = "";
						for (int i = 0; i < columnIndices.size(); i++) {
							int currContextIndex = contextIndices.get(i);
							int currColumnIndex = columnIndices.get(i);
							Key currKey = keys.get(currContextIndex);
							String currResult = queryResultForReturn.get(currContextIndex).get(currKey)
									.get(currColumnIndex);
							tmpOutput += currResult + ", ";
						}
						tmpOutput = tmpOutput.substring(0, tmpOutput.length() - 2);
						outputResults.add(tmpOutput);

						// If reaches limit
						if (outputResults.size() == tbqlQueryContext.limitValue) {
							break;
						}
					}

					// Print out
					for (String res : outputResults) {
						handler.handleRow(res);
					}
					ArrayList<String> headers = new ArrayList<>(tbqlQueryContext.returnResults);
					handler.handleHeader(headers);
					// System.out.println(outputResults.size());
				} else {
					// No distinct, we do not need to store the results
					// in outputResults
					int p = 0;
					for (ArrayList<Key> keys : satisfyingKeyGroups) {
						// Check whether reaches limit
						if (p == tbqlQueryContext.limitValue) {
							break;
						}

						String tmpOutput = "";
						for (int i = 0; i < columnIndices.size(); i++) {
							int currContextIndex = contextIndices.get(i);
							int currColumnIndex = columnIndices.get(i);
							Key currKey = keys.get(currContextIndex);
							String currResult = queryResultForReturn.get(currContextIndex).get(currKey)
									.get(currColumnIndex);
							tmpOutput += currResult + ", ";
						}
						tmpOutput = tmpOutput.substring(0, tmpOutput.length() - 2);
						handler.handleRow(tmpOutput);
						p++;
					}
					ArrayList<String> headers = new ArrayList<>(tbqlQueryContext.returnResults);
					handler.handleHeader(headers);
					// System.out.println(p);
				}
			} else { // no limit
				if (tbqlQueryContext.returnDistinct) {
					// Store results in outputResults
					HashSet<String> outputResults = new HashSet<>();
					for (ArrayList<Key> keys : satisfyingKeyGroups) {
						String tmpOutput = "";
						for (int i = 0; i < columnIndices.size(); i++) {
							int currContextIndex = contextIndices.get(i);
							int currColumnIndex = columnIndices.get(i);
							Key currKey = keys.get(currContextIndex);
							String currResult = queryResultForReturn.get(currContextIndex).get(currKey)
									.get(currColumnIndex);
							tmpOutput += currResult + ", ";
						}
						tmpOutput = tmpOutput.substring(0, tmpOutput.length() - 2);
						outputResults.add(tmpOutput);
					}

					// Print out
					for (String res : outputResults) {
						handler.handleRow(res);
					}
					ArrayList<String> headers = new ArrayList<>(tbqlQueryContext.returnResults);
					handler.handleHeader(headers);
					// System.out.println(outputResults.size());
				} else {
					// No distinct, we do not need to store the results in outputResults
					for (ArrayList<Key> keys : satisfyingKeyGroups) {
						// Check whether reaches limit
						String tmpOutput = "";
						for (int i = 0; i < columnIndices.size(); i++) {
							int currContextIndex = contextIndices.get(i);
							int currColumnIndex = columnIndices.get(i);
							Key currKey = keys.get(currContextIndex);
							String currResult = queryResultForReturn.get(currContextIndex).get(currKey)
									.get(currColumnIndex);
							tmpOutput += currResult + ", ";
						}
						tmpOutput = tmpOutput.substring(0, tmpOutput.length() - 2);
						handler.handleRow(tmpOutput);
					}
					ArrayList<String> headers = new ArrayList<>(tbqlQueryContext.returnResults);
					handler.handleHeader(headers);
				}
			}
		}
	}
}
