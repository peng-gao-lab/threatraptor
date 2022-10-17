package query.tbql.executor.handler;

import java.util.Map;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.IntervalSet;

public class ExpectedTokenErrorListener extends BaseErrorListener{

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		
		super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
		
		Parser parser = (Parser)recognizer;
		IntervalSet set = parser.getExpectedTokensWithinCurrentRule();
		String setstr = set.toString(parser.getVocabulary()).substring(1);
		setstr = setstr.substring(0, setstr.length() - 1);
		String[] split = setstr.split(",");
		Map<String, Integer> tokenTypeMap = parser.getTokenTypeMap();
		for (String s : split) {
			if (tokenTypeMap.containsKey(s)) {
				System.out.println("found literal " + parser.getVocabulary().getLiteralName(tokenTypeMap.get(s)));
			}
		}
		System.out.println("Tokens " + setstr);
	}
}
