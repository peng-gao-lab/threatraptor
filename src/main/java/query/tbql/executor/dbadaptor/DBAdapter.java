package query.tbql.executor.dbadaptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import query.tbql.executor.TBQLUtils.ValType;
import query.tbql.executor.constraint.AttributeConstraint;

public class DBAdapter {

	public static String getSQLAttributeConstraintStr(AttributeConstraint attributeConstraint, String eventID) {
		// Compile the BQL-style attribute constraint to SQL-style attribute constraint

		String id = attributeConstraint.getId();
		String op = attributeConstraint.getOp(); // =, >, <, >=, <=, !=, in, not in
		String val = attributeConstraint.getVal(); // int, float, string
		boolean negation = attributeConstraint.getNegation();
		ValType valType = attributeConstraint.getValType();

		if (valType == ValType.NUM) {
			if (op.equals("!=")) {
				op = "<>";
			}
		}
		else if (valType == ValType.STRING) {
			if (op.equals("=")) {
				op = "ilike";
			}
			else if (op.equals("!=")) {
				op = "not ilike";
			}
		}
		else if (valType == ValType.SET) {
			if (op.equals("in")) {
				if (negation) {
					op = "not in";
				}
			}
		}
		
		return eventID + "." + id + " " + op + " " + val;
	}
	
	public static String getCypherAttributeConstraintStr(AttributeConstraint attributeConstraint, String eventID) {
		// Compile the BQL-style attribute constraint to Cypher-style attribute constraint
		
		String id = attributeConstraint.getId();
		String op = attributeConstraint.getOp(); // =, >, <, >=, <=, !=, in, not in
		String val = attributeConstraint.getVal(); // int, float, string
		boolean negation = attributeConstraint.getNegation();
		ValType valType = attributeConstraint.getValType();

		String str = "";
		
		if (valType == ValType.STRING) {
			if (op.equals("=") || op.equals("!=")) {
				op = "=~";
				str = "toLower(" + eventID + "." + id + ") " + op + " " + val;
			}
		}
		else if (valType == ValType.NUM) {
			if (op.equals("!=")) {
				op = "<>";
			}
			str = eventID + "." + id + " " + op + " " + val;
		}
		else if (valType == ValType.SET) {
			if (val.contains("\"") || val.contains("\'")) {
				str = "toLower(" + eventID + "." + id + ") " + op + " " + val;
			}
			else {
				str = eventID + "." + id + " " + op + " " + val;
			}
		}
		
		if (negation) {
			str = "not(" + str + ")";
		}
		
		return str;
	}
	
	public static String adoptToCypherFilterString(String sqlFilterStr) {
		// Change brackets to square brackets
		
		String cypherFilterStr = sqlFilterStr.replace("(", "[");
		cypherFilterStr = cypherFilterStr.replace(")", "]");
		return cypherFilterStr;
	}
	
	public static ArrayList<ArrayList<String>> executeQuery(Connection conn, String query) throws Exception {
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		while (rs.next()) {
			ArrayList<String> row = new ArrayList<String>();
			for (int j = 1; j <= columnCount; j++) {
				row.add(rs.getString(j));
			}
			result.add(row);
		}

		ps.close();
		rs.close();
		return result;
	}
}
