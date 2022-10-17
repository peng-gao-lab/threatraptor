package query.tbql.executor.handler;

public interface TBQLExecutorErrorHandler {	
	public void handleLexerParserError (String errMsg); // errors due to lexing and parsing
	public void handleTimeFormatError (String errMsg); // time window information
	public void handleUndefinedError (String errMsg); // mainly for the default case in switch statement. This function may not be called often if the lexer works correctly.
	public void handleIDError (String errMsg); // e.g., using reserved id for path table; conflicts with the generated ids, etc.
	public void handleOtherError (String errMsg); // other errors, mainly used in TBQLExecutor
	public void handleOtherError (String errMsg, Exception e); // other errors, mainly used in TBQLExecutor
}
