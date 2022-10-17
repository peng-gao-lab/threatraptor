package query.tbql.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class SQLExecutorThread extends Thread {

	private String sql;
	private Connection conn;
	LinkedBlockingQueue<ArrayList<String>> res;

	public SQLExecutorThread(String sql, Connection conn, LinkedBlockingQueue<ArrayList<String>> res) {
		super();
		this.sql = sql;
		this.conn = conn;
		this.res = res;
	}

	@Override
	public void run() {
		ArrayList<ArrayList<String>> sqlret;
		try {
			sqlret = getResultAsString(conn, sql);
			synchronized (sqlret) {
				res.addAll(sqlret);
			}
		} catch (Exception e) {
			System.out.println("Error in executing SQL: " + e);
			System.out.println("=============\n" + sql+ "\n=================");

			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<String>> getResultAsString(Connection conn, String sql) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

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
