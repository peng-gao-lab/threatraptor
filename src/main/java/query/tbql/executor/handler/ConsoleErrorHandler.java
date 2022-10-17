package query.tbql.executor.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ConsoleErrorHandler implements TBQLExecutorErrorHandler {

	@Override
	public void handleLexerParserError(String errMsg) {
		System.err.println(errMsg);
		throw new RuntimeException(errMsg);
	}

	@Override
	public void handleTimeFormatError(String errMsg) {
		System.err.println(errMsg);
		throw new RuntimeException(errMsg);
	}

	@Override
	public void handleUndefinedError(String errMsg) {
		System.err.println(errMsg);
		throw new RuntimeException(errMsg);
	}

	@Override
	public void handleIDError(String errMsg) {
		System.err.println(errMsg);
		throw new RuntimeException(errMsg);
	}

	@Override
	public void handleOtherError(String errMsg) {
		System.err.println(errMsg);
		throw new RuntimeException(errMsg);
	}
	
	@Override
	public void handleOtherError(String errMsg, Exception e) {
		System.err.println(errMsg);
		System.err.println(ExceptionUtils.getStackTrace(e));
	}
}
