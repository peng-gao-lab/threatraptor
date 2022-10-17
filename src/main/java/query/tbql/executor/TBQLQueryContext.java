package query.tbql.executor;

import java.util.ArrayList;
import query.tbql.executor.constraint.TBQLEventConstraint;
import query.tbql.executor.constraint.TBQLEventRelationConstraint;

public class TBQLQueryContext {
	// Default attributes
	static String fileAttribute = "name"; // path.path
	static String processletAttribute = "exename";
	static String ipchannelAttribute = "dstip";
	static String eventAttribute = "hostname";
	
	// All BQLEventContext objects
	ArrayList<TBQLEventContext> eventPatternContexts; // Array of TBQLEventContext objects
	
	// Event relationship constraints
	ArrayList<TBQLEventRelationConstraint> eventRelationConstraints;

	// Return
	ArrayList<String> returnEventIDs; // EventIDs in the return (preserve order)
	ArrayList<String> returnResults; // Return results specified by the user
	boolean returnDistinct = false; // return distinct xxx, xxx
	boolean returnCount = false; // return count (distinct) xxx, xxx
	int limitValue = -1; // limit xxx 
	
	// Global constraints
	ArrayList<TBQLEventConstraint> globalConstraints; // <id, eq, val> or <id, op, collection> or <timewindow, eq, timeKey, timeVal, starttime, endtime>
	
	// Subquery
	ArrayList<String> queryTableResults; // table results of this query
	
	// Find the TBQLEventContext object that corresponds to id
	public int findIndexTBQLEventContext(String id) {
		for (int i = 0; i < eventPatternContexts.size(); i++) {
			TBQLEventContext tbqlEventContext = eventPatternContexts.get(i);
			if (tbqlEventContext.idTypeMap.containsKey(id)) {
				return i;
			}
		}
		return -1;
	}
	
	// Getters
	public ArrayList<String> getQueryTableResults(){
		return queryTableResults;
	}
	
	public ArrayList<String> getReturnResults(){
		return returnResults;
	}
	
	public boolean getReturnCount(){
		return returnCount;
	}
	
	public ArrayList<TBQLEventContext> getContexts() {
		return eventPatternContexts;
	}
	
	// Constructor
	public TBQLQueryContext() {
		eventPatternContexts = new ArrayList<TBQLEventContext>();
		eventRelationConstraints = new ArrayList<>();
		queryTableResults = new ArrayList<>();
		returnEventIDs = new ArrayList<>(); // all eventIDs used
		returnResults = new ArrayList<>();
	}
	
	public static String getDefaultAttribute(String type) {
		String defaultAttribute = "";
		switch (type) {
		case "file":
			defaultAttribute = fileAttribute;
			break;
		case "processlet":
			defaultAttribute = processletAttribute;
			break;
		case "ipchannel":
			defaultAttribute = ipchannelAttribute;
			break;
		case "fileevent":
		case "processletevent":
		case "ipchannelevent":
			defaultAttribute = eventAttribute;
			break;
		default:
			throw new RuntimeException("Undefined type: " + type);
		}
		
		return defaultAttribute;
	}
}
