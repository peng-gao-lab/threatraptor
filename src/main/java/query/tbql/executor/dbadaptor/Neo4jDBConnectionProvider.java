package query.tbql.executor.dbadaptor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;

public class Neo4jDBConnectionProvider {
	private final String dbClassName = "org.neo4j.jdbc.Driver";
	private String CONNECTION;
	private static DataSource ds;

	public Neo4jDBConnectionProvider() {
		try {
			Class.forName(dbClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		CONNECTION = "jdbc:neo4j:bolt://";
	}

	public Connection getConnectionNeo4j(String host, String db,
			String user, String pwd) throws Exception {
		String conn = CONNECTION + host;
		if (db != null && db.length() > 0) {
			conn = CONNECTION + host + "/" + db;
		}

		Properties p = new Properties();
		p.put("user", user);
		p.put("password", pwd);
		return DriverManager.getConnection(conn, p);
	}

	public void close() throws Exception {
		if (ds != null) {
			SharedPoolDataSource tds = (SharedPoolDataSource) ds;
			tds.close();
		}	
	}
}
