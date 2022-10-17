package query.tbql.executor.dbadaptor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Neo4jDBProperties {
	private String host;
	private String database;
	private String user;
	private String password;
	private String fileTable;
	private String processTable;
	private String networkTable;
	private String fileEventTable;
	private String processEventTable;
	private String networkEventTable;
	
	public Neo4jDBProperties() {
		loadProperties("cfg/database/neo4j_db.properties");
	}
	
	public Neo4jDBProperties(String propertyFilePath) {
		loadProperties(propertyFilePath);
	}

	public void loadProperties(String configFile) {
		Properties prop = new Properties();
		try {
			// Load a properties file from config file, inside static method
			try {
				prop.load(new FileInputStream(configFile));
			} catch (Exception e) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				InputStream input = classLoader.getResourceAsStream("neo4j_db.properties");
				prop = new Properties();
				prop.load(input);
			}
			
			// Get the property value and print it out
			host = prop.getProperty("host");
			database = prop.getProperty("database");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			fileTable = prop.getProperty("fileTable");
			processTable = prop.getProperty("processTable");
			networkTable = prop.getProperty("networkTable");
			fileEventTable = prop.getProperty("fileEventTable");
			processEventTable = prop.getProperty("processEventTable");
			networkEventTable = prop.getProperty("networkEventTable");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFileTable() {
		return fileTable;
	}

	public void setFileTable(String fileTable) {
		this.fileTable = fileTable;
	}

	public String getProcessTable() {
		return processTable;
	}

	public void setProcessTable(String processTable) {
		this.processTable = processTable;
	}

	public String getNetworkTable() {
		return networkTable;
	}

	public void setNetworkTable(String networkTable) {
		this.networkTable = networkTable;
	}

	public String getFileEventTable() {
		return fileEventTable;
	}

	public void setFileEventTable(String fileEventTable) {
		this.fileEventTable = fileEventTable;
	}

	public String getProcessEventTable() {
		return processEventTable;
	}

	public void setProcessEventTable(String processEventTable) {
		this.processEventTable = processEventTable;
	}

	public String getNetworkEventTable() {
		return networkEventTable;
	}

	public void setNetworkEventTable(String networkEventTable) {
		this.networkEventTable = networkEventTable;
	}
}
