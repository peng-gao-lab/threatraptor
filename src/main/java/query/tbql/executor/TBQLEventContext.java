package query.tbql.executor;

import java.util.HashMap;
import java.util.Map;
import query.tbql.executor.datetime.TBQLDateTime;
import query.tbql.executor.dbadaptor.CypherQueryContext;
import query.tbql.executor.dbadaptor.SQLQueryContext;

public class TBQLEventContext extends TBQLContext {
	// Stores all parsed information about an event pattern in TBQL
	
	// Context for storing SQL info
	SQLQueryContext sqlQueryContext = new SQLQueryContext();
	
	// Context for storing Cypher info
	CypherQueryContext cypherQueryContext = new CypherQueryContext();
	
	// Whether SQL or Cypher
	boolean synthesizeSQLQuery = true;
	
	// Subject, operation, object
	String subjectID;
	String objectID;
	String eventID; // evt, evt1, evt2
	String eventType;
	String opType; // subjectID opType objectID
	Map<String, String> idTypeMap = new HashMap<String, String>(); // id -> type

	// Time window info
	TBQLDateTime tbqlDateTime;
		
	// dbName -> timeWindowInWhere (only contains one key-value pair)
	HashMap<String, String> dbNameToTimeWindowStr = new HashMap<String, String>();

	
	// Complexity score of the event (depends on the number of attributes)
	int complexityScore;
	
	// Variable length path
	boolean isVarPath = false;
	int pathLengthBoundaryLeft;
	int pathLengthBoundaryRight;
	
	// Get the SQL query corresponding to this BQLContext
	public String getCompleteSQLQuery(String dbName) {
		String result = sqlQueryContext.getSQLQuery();
		if (!dbNameToTimeWindowStr.get(dbName).equals("")) {
			result += " and " + dbNameToTimeWindowStr.get(dbName);
		}
		return result;
	}
	
	// Get the Cypher query corresponding to this BQLContext
	public String getCompleteCypherQuery(String dbName) {
		String result = cypherQueryContext.getMatch().toString() + cypherQueryContext.getWhere();
		if (!dbNameToTimeWindowStr.get(dbName).equals("")) {
			result += " and " + dbNameToTimeWindowStr.get(dbName) + "\n";
		}
		result += cypherQueryContext.getReturnStr();
		return result;
	}	
}
