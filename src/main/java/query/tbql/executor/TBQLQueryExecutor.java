package query.tbql.executor;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import query.tbql.executor.dbadaptor.Neo4jDBProperties;
import query.tbql.executor.dbadaptor.PostgresDBProperties;
import query.tbql.executor.handler.TBQLExecutorErrorHandler;
import query.tbql.executor.handler.TBQLExecutorResultHandler;
import query.tbql.executor.handler.DescriptiveErrorListener;
import query.tbql.parser.TBQLLexer;
import query.tbql.parser.TBQLParser;

public class TBQLQueryExecutor {
	// Each TBQLQueryExecutor takes a TBQL query and output results.

	// Variables
	TBQLQueryContext tbqlQueryContext;
	TBQLExecutorResultHandler handler;
	TBQLExecutorErrorHandler errHandler;
	PostgresDBProperties postgresDbProperty;
	Neo4jDBProperties neo4jDbProperty;
	private TBQLQueryContextExecutor tbqlQueryContextExecutor;

	// Constructor
	public TBQLQueryExecutor() {}

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
	
	// Visit parse tree
	public void parseExpression(String expression) {
		// Lexer
		TBQLLexer lexer = new TBQLLexer(CharStreams.fromString(expression));
		lexer.removeErrorListeners(); // remove ConsoleErrorListener
		lexer.addErrorListener(new DescriptiveErrorListener(errHandler)); // add our implementation
		
		// Tokens
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// Parser
		TBQLParser parser = new TBQLParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new DescriptiveErrorListener(errHandler));
		parser.setBuildParseTree(true);
		ParseTree stat = parser.stat();

		// Visit tree
		TBQLQueryParser visitor = new TBQLQueryParser(errHandler, postgresDbProperty, neo4jDbProperty);
		visitor.visit(stat);
		tbqlQueryContext = visitor.tbqlQueryContext;
	}

	public void cancelQuery() {
		if (tbqlQueryContextExecutor != null) {
			tbqlQueryContextExecutor.cancelQueryExecutions();
		}
	}

	// Run the parsed query
	public void run() {
		tbqlQueryContextExecutor = new TBQLQueryContextExecutor(tbqlQueryContext);
		tbqlQueryContextExecutor.setResultHandler(handler);
		tbqlQueryContextExecutor.setErrorHandler(errHandler);
		tbqlQueryContextExecutor.setPostgresDbProperty(postgresDbProperty);
		tbqlQueryContextExecutor.setNeo4jDbProperty(neo4jDbProperty);
		tbqlQueryContextExecutor.run();
	}

	// Print out results
	public void printResults() {
		for (String str : tbqlQueryContext.queryTableResults) {
			System.out.println(str);
		}
		if (!tbqlQueryContext.returnCount) {
			System.out.println(tbqlQueryContext.returnResults);
			System.out.println("Number of records: " + tbqlQueryContext.queryTableResults.size());
		} else {
			System.out.println("Count");
		}
	}

	public TBQLQueryContext getTBQLQueryContext() {
		return tbqlQueryContext;
	}
}
