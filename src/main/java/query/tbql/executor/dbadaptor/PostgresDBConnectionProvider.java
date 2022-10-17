package query.tbql.executor.dbadaptor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.postgresql.ds.PGConnectionPoolDataSource;

public class PostgresDBConnectionProvider {
	private final String dbClassName = "org.postgresql.Driver";
	private String CONNECTION;
	private static DataSource ds;
	private PostgresDBProperties dbProperty;

	public PostgresDBConnectionProvider(PostgresDBProperties dbProperty) {
		try {
			Class.forName(dbClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		CONNECTION = "jdbc:postgresql://";
		
		this.dbProperty = dbProperty;
	}

	public Connection getRootConnection() throws Exception {
		Properties p = new Properties();
		p.put("user", dbProperty.getUser());
		p.put("password", dbProperty.getPassword());
		String connstr = "jdbc:postgresql://" + dbProperty.getHost()
				+ "/";
		return DriverManager.getConnection(connstr, p);
	}

	public void init(String host, String dbName, String username, String password) throws Exception {
		CONNECTION = "jdbc:postgresql://" + host + "/" + dbName;
		if (ds != null) {
			try {
				((SharedPoolDataSource) ds).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		PGConnectionPoolDataSource source = new PGConnectionPoolDataSource();
		source.setUrl(CONNECTION);
		source.setUser(username);
		source.setPassword(password);

		SharedPoolDataSource tds = new SharedPoolDataSource();
		tds.setConnectionPoolDataSource(source);
		tds.setMaxTotal(100);
		tds.setDefaultMaxWaitMillis(10000);
		tds.setDefaultMinIdle(10);
		tds.setDefaultAutoCommit(true);
		ds = tds;
	}

	public Connection getConnectionPostgreSQL(String host, String db,
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
