package query.tbql.executor.handler;

import org.antlr.v4.runtime.*;

public class DescriptiveErrorListener extends BaseErrorListener {    
    TBQLExecutorErrorHandler errHandler;
    
    public DescriptiveErrorListener(TBQLExecutorErrorHandler errHandler) {
    	this.errHandler = errHandler;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) 
    {
    	String errMsg = "line " + line + ":" + charPositionInLine + " at " +offendingSymbol + ": " + msg;
    	errHandler.handleLexerParserError(errMsg);
    }
}