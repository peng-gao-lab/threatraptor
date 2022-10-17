import java.nio.file.Files;
import java.nio.file.Paths;
import query.tbql.executor.TBQLQueryExecutor;
import query.tbql.executor.dbadaptor.Neo4jDBProperties;
import query.tbql.executor.dbadaptor.PostgresDBProperties;
import query.tbql.executor.handler.ConsoleErrorHandler;
import query.tbql.executor.handler.ConsoleResultHandler;
import sograph.SecurityOperationGraph;
import st2g.ST2G;
import web.WebApplication;

public class Main {
	private static void runPipeline() throws Exception {
		// Install st2g from https://github.com/security-kg/st2g before continue

		// NL-text to attack graph
		ST2G st2g = ST2G.getInstance();
		String inputPath = "input/example/attack_description.txt";
		String dotFilepath = st2g.process(inputPath);

		// Attack graph to TBQL query
		SecurityOperationGraph sog = new SecurityOperationGraph(dotFilepath);
		String tbql = sog.generateTBQL();
		System.out.println();
		System.out.println("Input path:" + inputPath);
		System.out.println("Generated TBQL: \n");
		System.out.println(tbql);

		// TBQL query execution
		long startTime = System.currentTimeMillis();
		PostgresDBProperties postgresDbProperty = new PostgresDBProperties();
		Neo4jDBProperties neo4jDbProperty = new Neo4jDBProperties();
		TBQLQueryExecutor tbqlQueryExecutor = new TBQLQueryExecutor();
		tbqlQueryExecutor.setResultHandler(new ConsoleResultHandler()); // print query results to console
		tbqlQueryExecutor.setErrorHandler(new ConsoleErrorHandler());
		tbqlQueryExecutor.setPostgresDbProperty(postgresDbProperty);
		tbqlQueryExecutor.setNeo4jDbProperty(neo4jDbProperty);
		tbqlQueryExecutor.parseExpression(tbql);
		tbqlQueryExecutor.run();

		long endTime = System.currentTimeMillis();
		String elapsedTime = (endTime - startTime) / 1000.0 + "s";
		System.out.println("TBQL Query execution time (total): " + elapsedTime);
	}

	private static void runTBQL() throws Exception {
		// Following is a typical pipeline for executing a TBQL query
		String query = new String(Files.readAllBytes(Paths.get("input/example/tbql_sql.tbql")));
		System.out.println(query);
		System.out.println("=====================================TBQL Execution=====================================");

		// Record the starttime
		long startTime = System.currentTimeMillis();

		// DB properties
		PostgresDBProperties postgresDbProperty = new PostgresDBProperties(); // default: cfg/database/postgres_db.properties
		Neo4jDBProperties neo4jDbProperty = new Neo4jDBProperties(); // default: cfg/database/neo4j_db.properties

		// Parse the expression
		TBQLQueryExecutor tbqlQueryExecutor = new TBQLQueryExecutor();
		tbqlQueryExecutor.setResultHandler(new ConsoleResultHandler());
		tbqlQueryExecutor.setErrorHandler(new ConsoleErrorHandler());
		tbqlQueryExecutor.setPostgresDbProperty(postgresDbProperty);
		tbqlQueryExecutor.setNeo4jDbProperty(neo4jDbProperty);
		tbqlQueryExecutor.parseExpression(query);

		// Run TBQLQueryExecutor
		tbqlQueryExecutor.run();

		// Record the endtime
		long endTime = System.currentTimeMillis();
		String elapsedTime = (endTime - startTime) / 1000.0 + "s";
		System.out.println("Query execution time (total): " + elapsedTime);
	}

	private static void runWeb() {
		WebApplication.init();
	}

	public static void main(String[] args) throws Exception {
		runTBQL();
		// runPipeline();
		// runWeb();
	}
}
