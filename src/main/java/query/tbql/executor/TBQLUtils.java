package query.tbql.executor;

import query.tbql.executor.constraint.TBQLEventRelationConstraint;

public class TBQLUtils{
	// Utility functions for TBQL parsing and execution
	
	public enum ValType 
	{ 
		NUM, STRING, SET; 
	}
	
	public static boolean isNumeric(String s) {  
	  try {
		  Double.parseDouble(s);  
	  } catch(NumberFormatException nfe) {
		  return false;  
	  }
	  return true;  
	}
	
	public static String trimBeginEndQuotes(String s) {
		String res = "";
		if ((s.startsWith("\"") && s.endsWith("\"")) || s.startsWith("\'") && s.endsWith("\'")) {
			res = s.substring(1, s.length() - 1);
		}
		else {
			throw new RuntimeException(s + " is not a quoted string");
		}
		
		return res;
	}
	
	public static String toSingleQuoteString(String s) {
		String res = trimBeginEndQuotes(s);
		res = "'" + res + "'";
		
		return res;
	}

	// Print out SQL queries in the constructor
	public static void printTBQLQueryContext(TBQLQueryContext tbqlQueryContext) {		
		System.out.println("\nAll parsed single-event SQL queries:\n");
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {			
			System.out.println("############\nTBQLEventContext info:");
			System.out.println("subjectID: " + tbqlEventContext.subjectID);
			System.out.println("opType: " + tbqlEventContext.opType);
			System.out.println("objectID: " + tbqlEventContext.objectID);
			System.out.println("eventID: " + tbqlEventContext.eventID);
			System.out.println("eventType: " + tbqlEventContext.eventType);

			System.out.print("TBQLDateTime info: ");
			if (tbqlEventContext.tbqlDateTime == null) {
				System.out.println("null");
			}
			else {
				System.out.println(tbqlEventContext.tbqlDateTime.toString());
			}
			System.out.println();

			// For each timeWindow
			System.out.println("synthesizeSQLQuery: " + tbqlEventContext.synthesizeSQLQuery);
			System.out.println("pathLengthBoundaryLeft: " + tbqlEventContext.pathLengthBoundaryLeft);
			System.out.println("pathLengthBoundaryRight: " + tbqlEventContext.pathLengthBoundaryRight);
			System.out.println();

			for (String dbName: tbqlEventContext.dbNameToTimeWindowStr.keySet()) {
				System.out.println("For each dbNameToTimeWindowStr:");
				System.out.println("DB: " + dbName);
				System.out.println("timeWindowStr: " + tbqlEventContext.dbNameToTimeWindowStr.get(dbName));
				System.out.println();
				if (tbqlEventContext.synthesizeSQLQuery) {
					System.out.println(tbqlEventContext.getCompleteSQLQuery(dbName));
				}
				else {
					System.out.println(tbqlEventContext.getCompleteCypherQuery(dbName));
				}
			}
			System.out.println("############\n");
		}

		System.out.println("All parsed event relation constraint information:");
		if (tbqlQueryContext.eventRelationConstraints != null) {
			for (TBQLEventRelationConstraint eventRelationConstraint: tbqlQueryContext.eventRelationConstraints) {
				System.out.println(eventRelationConstraint);
			}
		}
		System.out.println();

		System.out.println("EventIDs that are involved in the return results:");
		System.out.println(tbqlQueryContext.returnEventIDs);
		System.out.println(tbqlQueryContext.returnResults);
		System.out.println();

		System.out.println("Return distinct values?: ");
		System.out.println(tbqlQueryContext.returnDistinct);
		System.out.println();

		System.out.println("Return count?:");
		System.out.println(tbqlQueryContext.returnCount);
		System.out.println();

		System.out.println("Top?:");
		System.out.println(tbqlQueryContext.limitValue);
		System.out.println();	
	}

	// Print out the large translated SQL query
	public static String synthesizeGiantSQLForTBQLQueryContext(TBQLQueryContext tbqlQueryContext) {
		// Check
		boolean passCheck = true;
		for (TBQLEventContext tbqlEventContext: tbqlQueryContext.eventPatternContexts) {
			if (!tbqlEventContext.synthesizeSQLQuery) {
				passCheck = false;
				break;
			}
		}
		if (!passCheck) {
			System.out.println("Cannot generate a giant SQL query. Exit.");
			return "";
		}
		
		// Select
		String selectAll ="select ";
		if (tbqlQueryContext.returnDistinct){
			selectAll += "distinct ";
		}
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			selectAll += tbqlContext.sqlQueryContext.getSelect().substring(7, tbqlContext.sqlQueryContext.getSelect().length() - 1) + ", ";
		}
		selectAll = selectAll.substring(0, selectAll.length() - 2);
		
		// From
		String fromAll = "from ";
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			fromAll += tbqlContext.sqlQueryContext.getFrom().substring(5, tbqlContext.sqlQueryContext.getFrom().length() - 1) + ", ";
		}
		fromAll = fromAll.substring(0, fromAll.length() - 2);
		
		// Where
		String whereAll = "where ";
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			if (tbqlQueryContext.eventPatternContexts.indexOf(tbqlContext) == 0) {
				whereAll += "/* " + tbqlContext.eventID + " */";
			}
			else {
				whereAll += "\n/* " + tbqlContext.eventID + " */\n";
				whereAll += " and ";
			}
		
			whereAll += tbqlContext.sqlQueryContext.getWhere().substring(6, tbqlContext.sqlQueryContext.getWhere().length() - 1);

			for (String dbName: tbqlContext.dbNameToTimeWindowStr.keySet()) {
				if (!tbqlContext.dbNameToTimeWindowStr.get(dbName).equals("")) {
					whereAll += "\n and " + tbqlContext.dbNameToTimeWindowStr.get(dbName);
				}
			}
		}
		
		String sql = selectAll + "\n" + fromAll + "\n" + whereAll;
		return sql;
	}
	
	public static String synthesizeGiantCypherForTBQLQueryContext(TBQLQueryContext tbqlQueryContext) {
		// Check
		boolean passCheck = true;
		for (TBQLEventContext tbqlEventContext: tbqlQueryContext.eventPatternContexts) {
			if (tbqlEventContext.synthesizeSQLQuery) {
				passCheck = false;
				break;
			}
		}
		if (!passCheck) {
			System.out.println("Cannot generate a giant Cypher query. Exit.");
			return "";
		}
		
		// Match
		String matchAll = "match ";
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			matchAll += tbqlContext.cypherQueryContext.getMatch().substring(6, tbqlContext.cypherQueryContext.getMatch().length() - 1) + ", ";
		}
		matchAll = matchAll.substring(0, matchAll.length() - 2);
		
		// Return
		String returnAll = "return ";
		if (tbqlQueryContext.returnDistinct) {
			returnAll += "distinct";
		}
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			returnAll += tbqlContext.cypherQueryContext.getReturnStr().substring(6, tbqlContext.cypherQueryContext.getReturnStr().length() - 1) + ",";
		}
		returnAll = returnAll.substring(0, returnAll.length() - 1);
		
		// Where
		String whereAll = "where ";
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			if (tbqlQueryContext.eventPatternContexts.indexOf(tbqlContext) == 0) {
				whereAll = "// " + tbqlContext.eventID + "\n" + whereAll;
			}
			else {
				whereAll += "\n// " + tbqlContext.eventID + "\n";
				whereAll += " and ";
			}
		
			whereAll += tbqlContext.cypherQueryContext.getWhere().substring(6, tbqlContext.cypherQueryContext.getWhere().length() - 1);

			for (String dbName: tbqlContext.dbNameToTimeWindowStr.keySet()) {
				if (!tbqlContext.dbNameToTimeWindowStr.get(dbName).equals("")) {
					whereAll += "\n and " + tbqlContext.dbNameToTimeWindowStr.get(dbName);
				}
			}
		}
		
		String cypher = matchAll + "\n" + whereAll + "\n" + returnAll;		
		return cypher;
	}
}
