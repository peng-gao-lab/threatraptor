// Generated from TBQLParser.g4 by ANTLR 4.7.1
package query.tbql.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TBQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ENTITYTYPE=1, OPTYPE=2, COMMA=3, COLON=4, DOT=5, DASH=6, TILDE=7, WITH=8, 
		LOGICALOR=9, LOGICALAND=10, EQUAL=11, UNEQUAL=12, NOT=13, NOTSTR=14, GT=15, 
		LT=16, GEQ=17, LEQ=18, LEFTBRACKET=19, RIGHTBRACKET=20, LEFTSQUAREBRACKET=21, 
		RIGHTSQUAREBRACKET=22, LEFTDOUBLEBRACKET=23, RIGHTDOUBLEBRACKET=24, LEFTARROW=25, 
		RIGHTARROW=26, RIGHTTILDEARROW=27, IN=28, AS=29, AT=30, FROM=31, TO=32, 
		LAST=33, AND=34, OR=35, BEFORE=36, AFTER=37, WITHIN=38, TIMEUNIT=39, DISTINCT=40, 
		COUNT=41, RETURN=42, LIMIT=43, LIST=44, SORTBY=45, ASC=46, DESC=47, INT=48, 
		FLOAT=49, STRING=50, ID=51, NEWLINE=52, WS=53, COMMENT=54, LINE_COMMENT=55;
	public static final int
		RULE_stat = 0, RULE_globalConstraint = 1, RULE_timeWindowConstraint = 2, 
		RULE_multieventQuery = 3, RULE_eventPattern = 4, RULE_entity = 5, RULE_operation = 6, 
		RULE_pathOperation = 7, RULE_event = 8, RULE_attributeExpression = 9, 
		RULE_attributeConstraint = 10, RULE_collection = 11, RULE_collectionElement = 12, 
		RULE_timeWindow = 13, RULE_withClause = 14, RULE_eventRelation = 15, RULE_returnClause = 16, 
		RULE_result = 17, RULE_limit = 18;
	public static final String[] ruleNames = {
		"stat", "globalConstraint", "timeWindowConstraint", "multieventQuery", 
		"eventPattern", "entity", "operation", "pathOperation", "event", "attributeExpression", 
		"attributeConstraint", "collection", "collectionElement", "timeWindow", 
		"withClause", "eventRelation", "returnClause", "result", "limit"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, "','", "':'", "'.'", "'-'", "'~'", "'with'", "'||'", 
		"'&&'", "'='", "'!='", "'!'", "'not'", "'>'", "'<'", "'>='", "'<='", "'('", 
		"')'", "'['", "']'", "'<<'", "'>>'", "'<-'", "'->'", "'~>'", "'in'", "'as'", 
		"'at'", "'from'", "'to'", "'last'", "'and'", "'or'", "'before'", "'after'", 
		"'within'", null, "'distinct'", "'count'", "'return'", "'top'", "'list'", 
		"'sort by'", "'asc'", "'desc'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ENTITYTYPE", "OPTYPE", "COMMA", "COLON", "DOT", "DASH", "TILDE", 
		"WITH", "LOGICALOR", "LOGICALAND", "EQUAL", "UNEQUAL", "NOT", "NOTSTR", 
		"GT", "LT", "GEQ", "LEQ", "LEFTBRACKET", "RIGHTBRACKET", "LEFTSQUAREBRACKET", 
		"RIGHTSQUAREBRACKET", "LEFTDOUBLEBRACKET", "RIGHTDOUBLEBRACKET", "LEFTARROW", 
		"RIGHTARROW", "RIGHTTILDEARROW", "IN", "AS", "AT", "FROM", "TO", "LAST", 
		"AND", "OR", "BEFORE", "AFTER", "WITHIN", "TIMEUNIT", "DISTINCT", "COUNT", 
		"RETURN", "LIMIT", "LIST", "SORTBY", "ASC", "DESC", "INT", "FLOAT", "STRING", 
		"ID", "NEWLINE", "WS", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TBQLParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TBQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StatContext extends ParserRuleContext {
		public GlobalConstraintContext globalConstraint;
		public List<GlobalConstraintContext> glbcons = new ArrayList<GlobalConstraintContext>();
		public MultieventQueryContext multieventQuery() {
			return getRuleContext(MultieventQueryContext.class,0);
		}
		public List<GlobalConstraintContext> globalConstraint() {
			return getRuleContexts(GlobalConstraintContext.class);
		}
		public GlobalConstraintContext globalConstraint(int i) {
			return getRuleContext(GlobalConstraintContext.class,i);
		}
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_stat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AT) | (1L << FROM) | (1L << LAST) | (1L << BEFORE) | (1L << AFTER) | (1L << ID))) != 0)) {
				{
				{
				setState(38);
				((StatContext)_localctx).globalConstraint = globalConstraint();
				((StatContext)_localctx).glbcons.add(((StatContext)_localctx).globalConstraint);
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44);
			multieventQuery();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalConstraintContext extends ParserRuleContext {
		public GlobalConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalConstraint; }
	 
		public GlobalConstraintContext() { }
		public void copyFrom(GlobalConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AttributeGlobalConstraintContext extends GlobalConstraintContext {
		public Token key;
		public Token not;
		public Token op;
		public Token val;
		public CollectionContext coll;
		public TerminalNode ID() { return getToken(TBQLParser.ID, 0); }
		public TerminalNode EQUAL() { return getToken(TBQLParser.EQUAL, 0); }
		public TerminalNode UNEQUAL() { return getToken(TBQLParser.UNEQUAL, 0); }
		public TerminalNode GT() { return getToken(TBQLParser.GT, 0); }
		public TerminalNode LT() { return getToken(TBQLParser.LT, 0); }
		public TerminalNode GEQ() { return getToken(TBQLParser.GEQ, 0); }
		public TerminalNode LEQ() { return getToken(TBQLParser.LEQ, 0); }
		public TerminalNode IN() { return getToken(TBQLParser.IN, 0); }
		public CollectionContext collection() {
			return getRuleContext(CollectionContext.class,0);
		}
		public TerminalNode NOTSTR() { return getToken(TBQLParser.NOTSTR, 0); }
		public TerminalNode INT() { return getToken(TBQLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(TBQLParser.FLOAT, 0); }
		public TerminalNode STRING() { return getToken(TBQLParser.STRING, 0); }
		public AttributeGlobalConstraintContext(GlobalConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAttributeGlobalConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAttributeGlobalConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAttributeGlobalConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TimeWindowGlobalConstraintContext extends GlobalConstraintContext {
		public TimeWindowConstraintContext tcons;
		public TimeWindowConstraintContext timeWindowConstraint() {
			return getRuleContext(TimeWindowConstraintContext.class,0);
		}
		public TimeWindowGlobalConstraintContext(GlobalConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterTimeWindowGlobalConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitTimeWindowGlobalConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitTimeWindowGlobalConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalConstraintContext globalConstraint() throws RecognitionException {
		GlobalConstraintContext _localctx = new GlobalConstraintContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_globalConstraint);
		int _la;
		try {
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new AttributeGlobalConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(46);
				((AttributeGlobalConstraintContext)_localctx).key = match(ID);
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOTSTR) {
					{
					setState(47);
					((AttributeGlobalConstraintContext)_localctx).not = match(NOTSTR);
					}
				}

				setState(50);
				((AttributeGlobalConstraintContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << UNEQUAL) | (1L << GT) | (1L << LT) | (1L << GEQ) | (1L << LEQ) | (1L << IN))) != 0)) ) {
					((AttributeGlobalConstraintContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(53);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INT:
				case FLOAT:
				case STRING:
					{
					setState(51);
					((AttributeGlobalConstraintContext)_localctx).val = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) ) {
						((AttributeGlobalConstraintContext)_localctx).val = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case LEFTBRACKET:
					{
					setState(52);
					((AttributeGlobalConstraintContext)_localctx).coll = collection();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case AT:
			case FROM:
			case LAST:
			case BEFORE:
			case AFTER:
				_localctx = new TimeWindowGlobalConstraintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				((TimeWindowGlobalConstraintContext)_localctx).tcons = timeWindowConstraint();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimeWindowConstraintContext extends ParserRuleContext {
		public TimeWindowConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeWindowConstraint; }
	 
		public TimeWindowConstraintContext() { }
		public void copyFrom(TimeWindowConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FromToTimeConstraintContext extends TimeWindowConstraintContext {
		public Token t1;
		public Token t2;
		public TerminalNode FROM() { return getToken(TBQLParser.FROM, 0); }
		public TerminalNode TO() { return getToken(TBQLParser.TO, 0); }
		public List<TerminalNode> STRING() { return getTokens(TBQLParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(TBQLParser.STRING, i);
		}
		public FromToTimeConstraintContext(TimeWindowConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterFromToTimeConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitFromToTimeConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitFromToTimeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BeforeTimeConstraintContext extends TimeWindowConstraintContext {
		public Token t;
		public TerminalNode BEFORE() { return getToken(TBQLParser.BEFORE, 0); }
		public TerminalNode STRING() { return getToken(TBQLParser.STRING, 0); }
		public BeforeTimeConstraintContext(TimeWindowConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterBeforeTimeConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitBeforeTimeConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitBeforeTimeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LastTimeConstraintContext extends TimeWindowConstraintContext {
		public Token amount;
		public Token unit;
		public TerminalNode LAST() { return getToken(TBQLParser.LAST, 0); }
		public TerminalNode TIMEUNIT() { return getToken(TBQLParser.TIMEUNIT, 0); }
		public TerminalNode INT() { return getToken(TBQLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(TBQLParser.FLOAT, 0); }
		public LastTimeConstraintContext(TimeWindowConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterLastTimeConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitLastTimeConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitLastTimeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtTimeConstraintContext extends TimeWindowConstraintContext {
		public Token t;
		public TerminalNode AT() { return getToken(TBQLParser.AT, 0); }
		public TerminalNode STRING() { return getToken(TBQLParser.STRING, 0); }
		public AtTimeConstraintContext(TimeWindowConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAtTimeConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAtTimeConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAtTimeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AfterTimeConstraintContext extends TimeWindowConstraintContext {
		public Token t;
		public TerminalNode AFTER() { return getToken(TBQLParser.AFTER, 0); }
		public TerminalNode STRING() { return getToken(TBQLParser.STRING, 0); }
		public AfterTimeConstraintContext(TimeWindowConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAfterTimeConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAfterTimeConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAfterTimeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeWindowConstraintContext timeWindowConstraint() throws RecognitionException {
		TimeWindowConstraintContext _localctx = new TimeWindowConstraintContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_timeWindowConstraint);
		int _la;
		try {
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				_localctx = new AtTimeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				match(AT);
				setState(59);
				((AtTimeConstraintContext)_localctx).t = match(STRING);
				}
				break;
			case FROM:
				_localctx = new FromToTimeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(60);
				match(FROM);
				setState(61);
				((FromToTimeConstraintContext)_localctx).t1 = match(STRING);
				setState(62);
				match(TO);
				setState(63);
				((FromToTimeConstraintContext)_localctx).t2 = match(STRING);
				}
				break;
			case BEFORE:
				_localctx = new BeforeTimeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(64);
				match(BEFORE);
				setState(65);
				((BeforeTimeConstraintContext)_localctx).t = match(STRING);
				}
				break;
			case AFTER:
				_localctx = new AfterTimeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(66);
				match(AFTER);
				setState(67);
				((AfterTimeConstraintContext)_localctx).t = match(STRING);
				}
				break;
			case LAST:
				_localctx = new LastTimeConstraintContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(68);
				match(LAST);
				setState(69);
				((LastTimeConstraintContext)_localctx).amount = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==INT || _la==FLOAT) ) {
					((LastTimeConstraintContext)_localctx).amount = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(70);
				((LastTimeConstraintContext)_localctx).unit = match(TIMEUNIT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultieventQueryContext extends ParserRuleContext {
		public EventPatternContext eventPattern;
		public List<EventPatternContext> defs = new ArrayList<EventPatternContext>();
		public ReturnClauseContext returnClause() {
			return getRuleContext(ReturnClauseContext.class,0);
		}
		public List<EventPatternContext> eventPattern() {
			return getRuleContexts(EventPatternContext.class);
		}
		public EventPatternContext eventPattern(int i) {
			return getRuleContext(EventPatternContext.class,i);
		}
		public WithClauseContext withClause() {
			return getRuleContext(WithClauseContext.class,0);
		}
		public LimitContext limit() {
			return getRuleContext(LimitContext.class,0);
		}
		public MultieventQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multieventQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterMultieventQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitMultieventQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitMultieventQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultieventQueryContext multieventQuery() throws RecognitionException {
		MultieventQueryContext _localctx = new MultieventQueryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_multieventQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(73);
			((MultieventQueryContext)_localctx).eventPattern = eventPattern();
			((MultieventQueryContext)_localctx).defs.add(((MultieventQueryContext)_localctx).eventPattern);
			}
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ENTITYTYPE) {
				{
				{
				setState(74);
				((MultieventQueryContext)_localctx).eventPattern = eventPattern();
				((MultieventQueryContext)_localctx).defs.add(((MultieventQueryContext)_localctx).eventPattern);
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(80);
				withClause();
				}
			}

			setState(83);
			returnClause();
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(84);
				limit();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventPatternContext extends ParserRuleContext {
		public EventPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventPattern; }
	 
		public EventPatternContext() { }
		public void copyFrom(EventPatternContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarPathPatternContext extends EventPatternContext {
		public EntityContext sub;
		public PathOperationContext op;
		public EntityContext obj;
		public EventContext evt;
		public List<EntityContext> entity() {
			return getRuleContexts(EntityContext.class);
		}
		public EntityContext entity(int i) {
			return getRuleContext(EntityContext.class,i);
		}
		public PathOperationContext pathOperation() {
			return getRuleContext(PathOperationContext.class,0);
		}
		public EventContext event() {
			return getRuleContext(EventContext.class,0);
		}
		public VarPathPatternContext(EventPatternContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterVarPathPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitVarPathPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitVarPathPattern(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NormalEventPatternContext extends EventPatternContext {
		public EntityContext sub;
		public OperationContext op;
		public EntityContext obj;
		public EventContext evt;
		public TimeWindowContext time;
		public List<EntityContext> entity() {
			return getRuleContexts(EntityContext.class);
		}
		public EntityContext entity(int i) {
			return getRuleContext(EntityContext.class,i);
		}
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public EventContext event() {
			return getRuleContext(EventContext.class,0);
		}
		public TimeWindowContext timeWindow() {
			return getRuleContext(TimeWindowContext.class,0);
		}
		public NormalEventPatternContext(EventPatternContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterNormalEventPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitNormalEventPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitNormalEventPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventPatternContext eventPattern() throws RecognitionException {
		EventPatternContext _localctx = new EventPatternContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_eventPattern);
		int _la;
		try {
			setState(99);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new NormalEventPatternContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				((NormalEventPatternContext)_localctx).sub = entity();
				setState(88);
				((NormalEventPatternContext)_localctx).op = operation(0);
				setState(89);
				((NormalEventPatternContext)_localctx).obj = entity();
				setState(90);
				((NormalEventPatternContext)_localctx).evt = event();
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LEFTBRACKET) {
					{
					setState(91);
					((NormalEventPatternContext)_localctx).time = timeWindow();
					}
				}

				}
				break;
			case 2:
				_localctx = new VarPathPatternContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				((VarPathPatternContext)_localctx).sub = entity();
				setState(95);
				((VarPathPatternContext)_localctx).op = pathOperation();
				setState(96);
				((VarPathPatternContext)_localctx).obj = entity();
				setState(97);
				((VarPathPatternContext)_localctx).evt = event();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntityContext extends ParserRuleContext {
		public Token type;
		public Token id;
		public AttributeExpressionContext attr;
		public TerminalNode ENTITYTYPE() { return getToken(TBQLParser.ENTITYTYPE, 0); }
		public TerminalNode ID() { return getToken(TBQLParser.ID, 0); }
		public TerminalNode LEFTSQUAREBRACKET() { return getToken(TBQLParser.LEFTSQUAREBRACKET, 0); }
		public TerminalNode RIGHTSQUAREBRACKET() { return getToken(TBQLParser.RIGHTSQUAREBRACKET, 0); }
		public AttributeExpressionContext attributeExpression() {
			return getRuleContext(AttributeExpressionContext.class,0);
		}
		public EntityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterEntity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitEntity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitEntity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntityContext entity() throws RecognitionException {
		EntityContext _localctx = new EntityContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_entity);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			((EntityContext)_localctx).type = match(ENTITYTYPE);
			setState(102);
			((EntityContext)_localctx).id = match(ID);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFTSQUAREBRACKET) {
				{
				setState(103);
				match(LEFTSQUAREBRACKET);
				setState(104);
				((EntityContext)_localctx).attr = attributeExpression(0);
				setState(105);
				match(RIGHTSQUAREBRACKET);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationContext extends ParserRuleContext {
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
	 
		public OperationContext() { }
		public void copyFrom(OperationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OpOperationContext extends OperationContext {
		public OperationContext left;
		public Token op;
		public OperationContext right;
		public List<OperationContext> operation() {
			return getRuleContexts(OperationContext.class);
		}
		public OperationContext operation(int i) {
			return getRuleContext(OperationContext.class,i);
		}
		public TerminalNode LOGICALAND() { return getToken(TBQLParser.LOGICALAND, 0); }
		public TerminalNode LOGICALOR() { return getToken(TBQLParser.LOGICALOR, 0); }
		public OpOperationContext(OperationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterOpOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitOpOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitOpOperation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomOperationContext extends OperationContext {
		public Token atom;
		public TerminalNode OPTYPE() { return getToken(TBQLParser.OPTYPE, 0); }
		public AtomOperationContext(OperationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAtomOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAtomOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAtomOperation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenOperationContext extends OperationContext {
		public TerminalNode LEFTBRACKET() { return getToken(TBQLParser.LEFTBRACKET, 0); }
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public TerminalNode RIGHTBRACKET() { return getToken(TBQLParser.RIGHTBRACKET, 0); }
		public ParenOperationContext(OperationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterParenOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitParenOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitParenOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		return operation(0);
	}

	private OperationContext operation(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OperationContext _localctx = new OperationContext(_ctx, _parentState);
		OperationContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_operation, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPTYPE:
				{
				_localctx = new AtomOperationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(110);
				((AtomOperationContext)_localctx).atom = match(OPTYPE);
				}
				break;
			case LEFTBRACKET:
				{
				_localctx = new ParenOperationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(111);
				match(LEFTBRACKET);
				setState(112);
				operation(0);
				setState(113);
				match(RIGHTBRACKET);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(122);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OpOperationContext(new OperationContext(_parentctx, _parentState));
					((OpOperationContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_operation);
					setState(117);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(118);
					((OpOperationContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==LOGICALOR || _la==LOGICALAND) ) {
						((OpOperationContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(119);
					((OpOperationContext)_localctx).right = operation(4);
					}
					} 
				}
				setState(124);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PathOperationContext extends ParserRuleContext {
		public Token arrow;
		public Token left;
		public Token right;
		public TerminalNode RIGHTTILDEARROW() { return getToken(TBQLParser.RIGHTTILDEARROW, 0); }
		public TerminalNode RIGHTARROW() { return getToken(TBQLParser.RIGHTARROW, 0); }
		public TerminalNode LEFTBRACKET() { return getToken(TBQLParser.LEFTBRACKET, 0); }
		public TerminalNode RIGHTBRACKET() { return getToken(TBQLParser.RIGHTBRACKET, 0); }
		public TerminalNode LEFTSQUAREBRACKET() { return getToken(TBQLParser.LEFTSQUAREBRACKET, 0); }
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public TerminalNode RIGHTSQUAREBRACKET() { return getToken(TBQLParser.RIGHTSQUAREBRACKET, 0); }
		public TerminalNode TILDE() { return getToken(TBQLParser.TILDE, 0); }
		public List<TerminalNode> INT() { return getTokens(TBQLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(TBQLParser.INT, i);
		}
		public PathOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterPathOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitPathOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitPathOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathOperationContext pathOperation() throws RecognitionException {
		PathOperationContext _localctx = new PathOperationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_pathOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			((PathOperationContext)_localctx).arrow = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RIGHTARROW || _la==RIGHTTILDEARROW) ) {
				((PathOperationContext)_localctx).arrow = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFTBRACKET) {
				{
				setState(126);
				match(LEFTBRACKET);
				setState(128);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(127);
					((PathOperationContext)_localctx).left = match(INT);
					}
					break;
				}
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TILDE) {
					{
					setState(130);
					match(TILDE);
					}
				}

				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INT) {
					{
					setState(133);
					((PathOperationContext)_localctx).right = match(INT);
					}
				}

				setState(136);
				match(RIGHTBRACKET);
				}
			}

			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFTSQUAREBRACKET) {
				{
				setState(139);
				match(LEFTSQUAREBRACKET);
				setState(140);
				operation(0);
				setState(141);
				match(RIGHTSQUAREBRACKET);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventContext extends ParserRuleContext {
		public Token id;
		public AttributeExpressionContext attr;
		public TerminalNode AS() { return getToken(TBQLParser.AS, 0); }
		public TerminalNode ID() { return getToken(TBQLParser.ID, 0); }
		public TerminalNode LEFTSQUAREBRACKET() { return getToken(TBQLParser.LEFTSQUAREBRACKET, 0); }
		public TerminalNode RIGHTSQUAREBRACKET() { return getToken(TBQLParser.RIGHTSQUAREBRACKET, 0); }
		public AttributeExpressionContext attributeExpression() {
			return getRuleContext(AttributeExpressionContext.class,0);
		}
		public EventContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_event; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterEvent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitEvent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitEvent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventContext event() throws RecognitionException {
		EventContext _localctx = new EventContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_event);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			match(AS);
			setState(146);
			((EventContext)_localctx).id = match(ID);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFTSQUAREBRACKET) {
				{
				setState(147);
				match(LEFTSQUAREBRACKET);
				setState(148);
				((EventContext)_localctx).attr = attributeExpression(0);
				setState(149);
				match(RIGHTSQUAREBRACKET);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeExpressionContext extends ParserRuleContext {
		public AttributeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeExpression; }
	 
		public AttributeExpressionContext() { }
		public void copyFrom(AttributeExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AtomAttributeExprContext extends AttributeExpressionContext {
		public AttributeConstraintContext atom;
		public AttributeConstraintContext attributeConstraint() {
			return getRuleContext(AttributeConstraintContext.class,0);
		}
		public AtomAttributeExprContext(AttributeExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAtomAttributeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAtomAttributeExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAtomAttributeExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OpAttributeExprContext extends AttributeExpressionContext {
		public AttributeExpressionContext left;
		public Token op;
		public AttributeExpressionContext right;
		public List<AttributeExpressionContext> attributeExpression() {
			return getRuleContexts(AttributeExpressionContext.class);
		}
		public AttributeExpressionContext attributeExpression(int i) {
			return getRuleContext(AttributeExpressionContext.class,i);
		}
		public TerminalNode LOGICALAND() { return getToken(TBQLParser.LOGICALAND, 0); }
		public TerminalNode LOGICALOR() { return getToken(TBQLParser.LOGICALOR, 0); }
		public OpAttributeExprContext(AttributeExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterOpAttributeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitOpAttributeExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitOpAttributeExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenAttributeExprContext extends AttributeExpressionContext {
		public TerminalNode LEFTBRACKET() { return getToken(TBQLParser.LEFTBRACKET, 0); }
		public AttributeExpressionContext attributeExpression() {
			return getRuleContext(AttributeExpressionContext.class,0);
		}
		public TerminalNode RIGHTBRACKET() { return getToken(TBQLParser.RIGHTBRACKET, 0); }
		public ParenAttributeExprContext(AttributeExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterParenAttributeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitParenAttributeExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitParenAttributeExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeExpressionContext attributeExpression() throws RecognitionException {
		return attributeExpression(0);
	}

	private AttributeExpressionContext attributeExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AttributeExpressionContext _localctx = new AttributeExpressionContext(_ctx, _parentState);
		AttributeExpressionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_attributeExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
			case INT:
			case FLOAT:
			case STRING:
			case ID:
				{
				_localctx = new AtomAttributeExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(154);
				((AtomAttributeExprContext)_localctx).atom = attributeConstraint();
				}
				break;
			case LEFTBRACKET:
				{
				_localctx = new ParenAttributeExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(155);
				match(LEFTBRACKET);
				setState(156);
				attributeExpression(0);
				setState(157);
				match(RIGHTBRACKET);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(166);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OpAttributeExprContext(new AttributeExpressionContext(_parentctx, _parentState));
					((OpAttributeExprContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_attributeExpression);
					setState(161);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(162);
					((OpAttributeExprContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==LOGICALOR || _la==LOGICALAND) ) {
						((OpAttributeExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(163);
					((OpAttributeExprContext)_localctx).right = attributeExpression(4);
					}
					} 
				}
				setState(168);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AttributeConstraintContext extends ParserRuleContext {
		public AttributeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeConstraint; }
	 
		public AttributeConstraintContext() { }
		public void copyFrom(AttributeConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AttributeConstraintCollectionContext extends AttributeConstraintContext {
		public Token id;
		public Token not;
		public CollectionContext col;
		public TerminalNode IN() { return getToken(TBQLParser.IN, 0); }
		public TerminalNode ID() { return getToken(TBQLParser.ID, 0); }
		public CollectionContext collection() {
			return getRuleContext(CollectionContext.class,0);
		}
		public TerminalNode NOTSTR() { return getToken(TBQLParser.NOTSTR, 0); }
		public AttributeConstraintCollectionContext(AttributeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAttributeConstraintCollection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAttributeConstraintCollection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAttributeConstraintCollection(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AttributeConstraintFullContext extends AttributeConstraintContext {
		public Token id;
		public Token op;
		public Token not;
		public Token val;
		public TerminalNode INT() { return getToken(TBQLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(TBQLParser.FLOAT, 0); }
		public TerminalNode STRING() { return getToken(TBQLParser.STRING, 0); }
		public TerminalNode ID() { return getToken(TBQLParser.ID, 0); }
		public TerminalNode NOT() { return getToken(TBQLParser.NOT, 0); }
		public TerminalNode EQUAL() { return getToken(TBQLParser.EQUAL, 0); }
		public TerminalNode UNEQUAL() { return getToken(TBQLParser.UNEQUAL, 0); }
		public TerminalNode GT() { return getToken(TBQLParser.GT, 0); }
		public TerminalNode LT() { return getToken(TBQLParser.LT, 0); }
		public TerminalNode GEQ() { return getToken(TBQLParser.GEQ, 0); }
		public TerminalNode LEQ() { return getToken(TBQLParser.LEQ, 0); }
		public AttributeConstraintFullContext(AttributeConstraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAttributeConstraintFull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAttributeConstraintFull(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAttributeConstraintFull(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeConstraintContext attributeConstraint() throws RecognitionException {
		AttributeConstraintContext _localctx = new AttributeConstraintContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_attributeConstraint);
		int _la;
		try {
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new AttributeConstraintFullContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(169);
					((AttributeConstraintFullContext)_localctx).id = match(ID);
					setState(170);
					((AttributeConstraintFullContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << UNEQUAL) | (1L << GT) | (1L << LT) | (1L << GEQ) | (1L << LEQ))) != 0)) ) {
						((AttributeConstraintFullContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(173);
					((AttributeConstraintFullContext)_localctx).not = match(NOT);
					}
				}

				setState(176);
				((AttributeConstraintFullContext)_localctx).val = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) ) {
					((AttributeConstraintFullContext)_localctx).val = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 2:
				_localctx = new AttributeConstraintCollectionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				((AttributeConstraintCollectionContext)_localctx).id = match(ID);
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOTSTR) {
					{
					setState(178);
					((AttributeConstraintCollectionContext)_localctx).not = match(NOTSTR);
					}
				}

				setState(181);
				match(IN);
				setState(182);
				((AttributeConstraintCollectionContext)_localctx).col = collection();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionContext extends ParserRuleContext {
		public CollectionElementContext collectionElement;
		public List<CollectionElementContext> collelems = new ArrayList<CollectionElementContext>();
		public TerminalNode LEFTBRACKET() { return getToken(TBQLParser.LEFTBRACKET, 0); }
		public TerminalNode RIGHTBRACKET() { return getToken(TBQLParser.RIGHTBRACKET, 0); }
		public List<CollectionElementContext> collectionElement() {
			return getRuleContexts(CollectionElementContext.class);
		}
		public CollectionElementContext collectionElement(int i) {
			return getRuleContext(CollectionElementContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TBQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TBQLParser.COMMA, i);
		}
		public CollectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterCollection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitCollection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitCollection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionContext collection() throws RecognitionException {
		CollectionContext _localctx = new CollectionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_collection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(LEFTBRACKET);
			setState(186);
			((CollectionContext)_localctx).collectionElement = collectionElement();
			((CollectionContext)_localctx).collelems.add(((CollectionContext)_localctx).collectionElement);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(187);
				match(COMMA);
				setState(188);
				((CollectionContext)_localctx).collectionElement = collectionElement();
				((CollectionContext)_localctx).collelems.add(((CollectionContext)_localctx).collectionElement);
				}
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(194);
			match(RIGHTBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionElementContext extends ParserRuleContext {
		public Token val;
		public Token func;
		public Token ID;
		public List<Token> args = new ArrayList<Token>();
		public Token INT;
		public Token FLOAT;
		public Token STRING;
		public Token _tset584;
		public Token _tset599;
		public TerminalNode LEFTBRACKET() { return getToken(TBQLParser.LEFTBRACKET, 0); }
		public TerminalNode RIGHTBRACKET() { return getToken(TBQLParser.RIGHTBRACKET, 0); }
		public List<TerminalNode> INT() { return getTokens(TBQLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(TBQLParser.INT, i);
		}
		public List<TerminalNode> FLOAT() { return getTokens(TBQLParser.FLOAT); }
		public TerminalNode FLOAT(int i) {
			return getToken(TBQLParser.FLOAT, i);
		}
		public List<TerminalNode> STRING() { return getTokens(TBQLParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(TBQLParser.STRING, i);
		}
		public List<TerminalNode> ID() { return getTokens(TBQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(TBQLParser.ID, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TBQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TBQLParser.COMMA, i);
		}
		public CollectionElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collectionElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterCollectionElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitCollectionElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitCollectionElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionElementContext collectionElement() throws RecognitionException {
		CollectionElementContext _localctx = new CollectionElementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_collectionElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				{
				setState(196);
				((CollectionElementContext)_localctx).val = match(INT);
				}
				break;
			case FLOAT:
				{
				setState(197);
				((CollectionElementContext)_localctx).val = match(FLOAT);
				}
				break;
			case STRING:
				{
				setState(198);
				((CollectionElementContext)_localctx).val = match(STRING);
				}
				break;
			case ID:
				{
				setState(199);
				((CollectionElementContext)_localctx).func = match(ID);
				setState(200);
				match(LEFTBRACKET);
				setState(209);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(201);
					((CollectionElementContext)_localctx)._tset584 = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) ) {
						((CollectionElementContext)_localctx)._tset584 = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					((CollectionElementContext)_localctx).args.add(((CollectionElementContext)_localctx)._tset584);
					setState(206);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(202);
						match(COMMA);
						setState(203);
						((CollectionElementContext)_localctx)._tset599 = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << ID))) != 0)) ) {
							((CollectionElementContext)_localctx)._tset599 = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						((CollectionElementContext)_localctx).args.add(((CollectionElementContext)_localctx)._tset599);
						}
						}
						setState(208);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(211);
				match(RIGHTBRACKET);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimeWindowContext extends ParserRuleContext {
		public TimeWindowConstraintContext tcons;
		public TerminalNode LEFTBRACKET() { return getToken(TBQLParser.LEFTBRACKET, 0); }
		public TerminalNode RIGHTBRACKET() { return getToken(TBQLParser.RIGHTBRACKET, 0); }
		public TimeWindowConstraintContext timeWindowConstraint() {
			return getRuleContext(TimeWindowConstraintContext.class,0);
		}
		public TimeWindowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeWindow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterTimeWindow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitTimeWindow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitTimeWindow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeWindowContext timeWindow() throws RecognitionException {
		TimeWindowContext _localctx = new TimeWindowContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_timeWindow);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			match(LEFTBRACKET);
			setState(215);
			((TimeWindowContext)_localctx).tcons = timeWindowConstraint();
			setState(216);
			match(RIGHTBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithClauseContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(TBQLParser.WITH, 0); }
		public List<EventRelationContext> eventRelation() {
			return getRuleContexts(EventRelationContext.class);
		}
		public EventRelationContext eventRelation(int i) {
			return getRuleContext(EventRelationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TBQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TBQLParser.COMMA, i);
		}
		public WithClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterWithClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitWithClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitWithClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithClauseContext withClause() throws RecognitionException {
		WithClauseContext _localctx = new WithClauseContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_withClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(WITH);
			{
			setState(219);
			eventRelation();
			}
			setState(224);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(220);
				match(COMMA);
				setState(221);
				eventRelation();
				}
				}
				setState(226);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventRelationContext extends ParserRuleContext {
		public EventRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventRelation; }
	 
		public EventRelationContext() { }
		public void copyFrom(EventRelationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TemporalRelationContext extends EventRelationContext {
		public Token evt1;
		public Token type;
		public Token num1;
		public Token num2;
		public Token unit;
		public Token evt2;
		public List<TerminalNode> ID() { return getTokens(TBQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(TBQLParser.ID, i);
		}
		public TerminalNode BEFORE() { return getToken(TBQLParser.BEFORE, 0); }
		public TerminalNode AFTER() { return getToken(TBQLParser.AFTER, 0); }
		public TerminalNode WITHIN() { return getToken(TBQLParser.WITHIN, 0); }
		public TerminalNode LEFTSQUAREBRACKET() { return getToken(TBQLParser.LEFTSQUAREBRACKET, 0); }
		public TerminalNode DASH() { return getToken(TBQLParser.DASH, 0); }
		public TerminalNode RIGHTSQUAREBRACKET() { return getToken(TBQLParser.RIGHTSQUAREBRACKET, 0); }
		public TerminalNode TIMEUNIT() { return getToken(TBQLParser.TIMEUNIT, 0); }
		public List<TerminalNode> INT() { return getTokens(TBQLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(TBQLParser.INT, i);
		}
		public List<TerminalNode> FLOAT() { return getTokens(TBQLParser.FLOAT); }
		public TerminalNode FLOAT(int i) {
			return getToken(TBQLParser.FLOAT, i);
		}
		public TemporalRelationContext(EventRelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterTemporalRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitTemporalRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitTemporalRelation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AttributeRelationContext extends EventRelationContext {
		public Token id1;
		public Token attr1;
		public Token op;
		public Token id2;
		public Token attr2;
		public List<TerminalNode> DOT() { return getTokens(TBQLParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(TBQLParser.DOT, i);
		}
		public List<TerminalNode> ID() { return getTokens(TBQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(TBQLParser.ID, i);
		}
		public TerminalNode EQUAL() { return getToken(TBQLParser.EQUAL, 0); }
		public TerminalNode UNEQUAL() { return getToken(TBQLParser.UNEQUAL, 0); }
		public TerminalNode GT() { return getToken(TBQLParser.GT, 0); }
		public TerminalNode LT() { return getToken(TBQLParser.LT, 0); }
		public TerminalNode GEQ() { return getToken(TBQLParser.GEQ, 0); }
		public TerminalNode LEQ() { return getToken(TBQLParser.LEQ, 0); }
		public AttributeRelationContext(EventRelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterAttributeRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitAttributeRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitAttributeRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventRelationContext eventRelation() throws RecognitionException {
		EventRelationContext _localctx = new EventRelationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_eventRelation);
		int _la;
		try {
			setState(245);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				_localctx = new AttributeRelationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(227);
				((AttributeRelationContext)_localctx).id1 = match(ID);
				setState(228);
				match(DOT);
				setState(229);
				((AttributeRelationContext)_localctx).attr1 = match(ID);
				setState(230);
				((AttributeRelationContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << UNEQUAL) | (1L << GT) | (1L << LT) | (1L << GEQ) | (1L << LEQ))) != 0)) ) {
					((AttributeRelationContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(231);
				((AttributeRelationContext)_localctx).id2 = match(ID);
				setState(232);
				match(DOT);
				setState(233);
				((AttributeRelationContext)_localctx).attr2 = match(ID);
				}
				break;
			case 2:
				_localctx = new TemporalRelationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(234);
				((TemporalRelationContext)_localctx).evt1 = match(ID);
				setState(235);
				((TemporalRelationContext)_localctx).type = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BEFORE) | (1L << AFTER) | (1L << WITHIN))) != 0)) ) {
					((TemporalRelationContext)_localctx).type = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LEFTSQUAREBRACKET) {
					{
					setState(236);
					match(LEFTSQUAREBRACKET);
					{
					setState(237);
					((TemporalRelationContext)_localctx).num1 = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==INT || _la==FLOAT) ) {
						((TemporalRelationContext)_localctx).num1 = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					setState(238);
					match(DASH);
					{
					setState(239);
					((TemporalRelationContext)_localctx).num2 = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==INT || _la==FLOAT) ) {
						((TemporalRelationContext)_localctx).num2 = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					setState(240);
					((TemporalRelationContext)_localctx).unit = match(TIMEUNIT);
					setState(241);
					match(RIGHTSQUAREBRACKET);
					}
				}

				setState(244);
				((TemporalRelationContext)_localctx).evt2 = match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnClauseContext extends ParserRuleContext {
		public Token count;
		public Token distinct;
		public ResultContext result;
		public List<ResultContext> res = new ArrayList<ResultContext>();
		public TerminalNode RETURN() { return getToken(TBQLParser.RETURN, 0); }
		public List<ResultContext> result() {
			return getRuleContexts(ResultContext.class);
		}
		public ResultContext result(int i) {
			return getRuleContext(ResultContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TBQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TBQLParser.COMMA, i);
		}
		public TerminalNode COUNT() { return getToken(TBQLParser.COUNT, 0); }
		public TerminalNode DISTINCT() { return getToken(TBQLParser.DISTINCT, 0); }
		public ReturnClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterReturnClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitReturnClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitReturnClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnClauseContext returnClause() throws RecognitionException {
		ReturnClauseContext _localctx = new ReturnClauseContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_returnClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(RETURN);
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COUNT) {
				{
				setState(248);
				((ReturnClauseContext)_localctx).count = match(COUNT);
				}
			}

			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DISTINCT) {
				{
				setState(251);
				((ReturnClauseContext)_localctx).distinct = match(DISTINCT);
				}
			}

			{
			setState(254);
			((ReturnClauseContext)_localctx).result = result();
			((ReturnClauseContext)_localctx).res.add(((ReturnClauseContext)_localctx).result);
			}
			setState(259);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(255);
				match(COMMA);
				setState(256);
				((ReturnClauseContext)_localctx).result = result();
				((ReturnClauseContext)_localctx).res.add(((ReturnClauseContext)_localctx).result);
				}
				}
				setState(261);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultContext extends ParserRuleContext {
		public Token id;
		public Token attr;
		public List<TerminalNode> ID() { return getTokens(TBQLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(TBQLParser.ID, i);
		}
		public TerminalNode DOT() { return getToken(TBQLParser.DOT, 0); }
		public ResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_result; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitResult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitResult(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultContext result() throws RecognitionException {
		ResultContext _localctx = new ResultContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_result);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			((ResultContext)_localctx).id = match(ID);
			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOT) {
				{
				setState(263);
				match(DOT);
				setState(264);
				((ResultContext)_localctx).attr = match(ID);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LimitContext extends ParserRuleContext {
		public Token val;
		public TerminalNode LIMIT() { return getToken(TBQLParser.LIMIT, 0); }
		public TerminalNode INT() { return getToken(TBQLParser.INT, 0); }
		public LimitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).enterLimit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TBQLParserListener ) ((TBQLParserListener)listener).exitLimit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TBQLParserVisitor ) return ((TBQLParserVisitor<? extends T>)visitor).visitLimit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitContext limit() throws RecognitionException {
		LimitContext _localctx = new LimitContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_limit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(LIMIT);
			setState(268);
			((LimitContext)_localctx).val = match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return operation_sempred((OperationContext)_localctx, predIndex);
		case 9:
			return attributeExpression_sempred((AttributeExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean operation_sempred(OperationContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean attributeExpression_sempred(AttributeExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\39\u0111\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\7\2*\n\2\f\2\16\2-\13\2\3\2\3\2\3\3\3\3\5\3\63"+
		"\n\3\3\3\3\3\3\3\5\38\n\3\3\3\5\3;\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\5\4J\n\4\3\5\3\5\7\5N\n\5\f\5\16\5Q\13\5\3\5\5\5"+
		"T\n\5\3\5\3\5\5\5X\n\5\3\6\3\6\3\6\3\6\3\6\5\6_\n\6\3\6\3\6\3\6\3\6\3"+
		"\6\5\6f\n\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7n\n\7\3\b\3\b\3\b\3\b\3\b\3\b\5"+
		"\bv\n\b\3\b\3\b\3\b\7\b{\n\b\f\b\16\b~\13\b\3\t\3\t\3\t\5\t\u0083\n\t"+
		"\3\t\5\t\u0086\n\t\3\t\5\t\u0089\n\t\3\t\5\t\u008c\n\t\3\t\3\t\3\t\3\t"+
		"\5\t\u0092\n\t\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u009a\n\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\5\13\u00a2\n\13\3\13\3\13\3\13\7\13\u00a7\n\13\f\13\16\13"+
		"\u00aa\13\13\3\f\3\f\5\f\u00ae\n\f\3\f\5\f\u00b1\n\f\3\f\3\f\3\f\5\f\u00b6"+
		"\n\f\3\f\3\f\5\f\u00ba\n\f\3\r\3\r\3\r\3\r\7\r\u00c0\n\r\f\r\16\r\u00c3"+
		"\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00cf\n\16"+
		"\f\16\16\16\u00d2\13\16\5\16\u00d4\n\16\3\16\5\16\u00d7\n\16\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\7\20\u00e1\n\20\f\20\16\20\u00e4\13\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\5\21\u00f5\n\21\3\21\5\21\u00f8\n\21\3\22\3\22\5\22\u00fc\n\22\3"+
		"\22\5\22\u00ff\n\22\3\22\3\22\3\22\7\22\u0104\n\22\f\22\16\22\u0107\13"+
		"\22\3\23\3\23\3\23\5\23\u010c\n\23\3\24\3\24\3\24\3\24\2\4\16\24\25\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&\2\n\5\2\r\16\21\24\36\36\3"+
		"\2\62\64\3\2\62\63\3\2\13\f\3\2\34\35\4\2\r\16\21\24\3\2\62\65\3\2&(\2"+
		"\u0126\2+\3\2\2\2\4:\3\2\2\2\6I\3\2\2\2\bK\3\2\2\2\ne\3\2\2\2\fg\3\2\2"+
		"\2\16u\3\2\2\2\20\177\3\2\2\2\22\u0093\3\2\2\2\24\u00a1\3\2\2\2\26\u00b9"+
		"\3\2\2\2\30\u00bb\3\2\2\2\32\u00d6\3\2\2\2\34\u00d8\3\2\2\2\36\u00dc\3"+
		"\2\2\2 \u00f7\3\2\2\2\"\u00f9\3\2\2\2$\u0108\3\2\2\2&\u010d\3\2\2\2(*"+
		"\5\4\3\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,.\3\2\2\2-+\3\2\2\2."+
		"/\5\b\5\2/\3\3\2\2\2\60\62\7\65\2\2\61\63\7\20\2\2\62\61\3\2\2\2\62\63"+
		"\3\2\2\2\63\64\3\2\2\2\64\67\t\2\2\2\658\t\3\2\2\668\5\30\r\2\67\65\3"+
		"\2\2\2\67\66\3\2\2\28;\3\2\2\29;\5\6\4\2:\60\3\2\2\2:9\3\2\2\2;\5\3\2"+
		"\2\2<=\7 \2\2=J\7\64\2\2>?\7!\2\2?@\7\64\2\2@A\7\"\2\2AJ\7\64\2\2BC\7"+
		"&\2\2CJ\7\64\2\2DE\7\'\2\2EJ\7\64\2\2FG\7#\2\2GH\t\4\2\2HJ\7)\2\2I<\3"+
		"\2\2\2I>\3\2\2\2IB\3\2\2\2ID\3\2\2\2IF\3\2\2\2J\7\3\2\2\2KO\5\n\6\2LN"+
		"\5\n\6\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PS\3\2\2\2QO\3\2\2\2R"+
		"T\5\36\20\2SR\3\2\2\2ST\3\2\2\2TU\3\2\2\2UW\5\"\22\2VX\5&\24\2WV\3\2\2"+
		"\2WX\3\2\2\2X\t\3\2\2\2YZ\5\f\7\2Z[\5\16\b\2[\\\5\f\7\2\\^\5\22\n\2]_"+
		"\5\34\17\2^]\3\2\2\2^_\3\2\2\2_f\3\2\2\2`a\5\f\7\2ab\5\20\t\2bc\5\f\7"+
		"\2cd\5\22\n\2df\3\2\2\2eY\3\2\2\2e`\3\2\2\2f\13\3\2\2\2gh\7\3\2\2hm\7"+
		"\65\2\2ij\7\27\2\2jk\5\24\13\2kl\7\30\2\2ln\3\2\2\2mi\3\2\2\2mn\3\2\2"+
		"\2n\r\3\2\2\2op\b\b\1\2pv\7\4\2\2qr\7\25\2\2rs\5\16\b\2st\7\26\2\2tv\3"+
		"\2\2\2uo\3\2\2\2uq\3\2\2\2v|\3\2\2\2wx\f\5\2\2xy\t\5\2\2y{\5\16\b\6zw"+
		"\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\17\3\2\2\2~|\3\2\2\2\177\u008b"+
		"\t\6\2\2\u0080\u0082\7\25\2\2\u0081\u0083\7\62\2\2\u0082\u0081\3\2\2\2"+
		"\u0082\u0083\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0086\7\t\2\2\u0085\u0084"+
		"\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0088\3\2\2\2\u0087\u0089\7\62\2\2"+
		"\u0088\u0087\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008c"+
		"\7\26\2\2\u008b\u0080\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u0091\3\2\2\2"+
		"\u008d\u008e\7\27\2\2\u008e\u008f\5\16\b\2\u008f\u0090\7\30\2\2\u0090"+
		"\u0092\3\2\2\2\u0091\u008d\3\2\2\2\u0091\u0092\3\2\2\2\u0092\21\3\2\2"+
		"\2\u0093\u0094\7\37\2\2\u0094\u0099\7\65\2\2\u0095\u0096\7\27\2\2\u0096"+
		"\u0097\5\24\13\2\u0097\u0098\7\30\2\2\u0098\u009a\3\2\2\2\u0099\u0095"+
		"\3\2\2\2\u0099\u009a\3\2\2\2\u009a\23\3\2\2\2\u009b\u009c\b\13\1\2\u009c"+
		"\u00a2\5\26\f\2\u009d\u009e\7\25\2\2\u009e\u009f\5\24\13\2\u009f\u00a0"+
		"\7\26\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009b\3\2\2\2\u00a1\u009d\3\2\2\2"+
		"\u00a2\u00a8\3\2\2\2\u00a3\u00a4\f\5\2\2\u00a4\u00a5\t\5\2\2\u00a5\u00a7"+
		"\5\24\13\6\u00a6\u00a3\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2"+
		"\u00a8\u00a9\3\2\2\2\u00a9\25\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ac"+
		"\7\65\2\2\u00ac\u00ae\t\7\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2"+
		"\u00ae\u00b0\3\2\2\2\u00af\u00b1\7\17\2\2\u00b0\u00af\3\2\2\2\u00b0\u00b1"+
		"\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00ba\t\3\2\2\u00b3\u00b5\7\65\2\2"+
		"\u00b4\u00b6\7\20\2\2\u00b5\u00b4\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7"+
		"\3\2\2\2\u00b7\u00b8\7\36\2\2\u00b8\u00ba\5\30\r\2\u00b9\u00ad\3\2\2\2"+
		"\u00b9\u00b3\3\2\2\2\u00ba\27\3\2\2\2\u00bb\u00bc\7\25\2\2\u00bc\u00c1"+
		"\5\32\16\2\u00bd\u00be\7\5\2\2\u00be\u00c0\5\32\16\2\u00bf\u00bd\3\2\2"+
		"\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4"+
		"\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c5\7\26\2\2\u00c5\31\3\2\2\2\u00c6"+
		"\u00d7\7\62\2\2\u00c7\u00d7\7\63\2\2\u00c8\u00d7\7\64\2\2\u00c9\u00ca"+
		"\7\65\2\2\u00ca\u00d3\7\25\2\2\u00cb\u00d0\t\b\2\2\u00cc\u00cd\7\5\2\2"+
		"\u00cd\u00cf\t\b\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce"+
		"\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3"+
		"\u00cb\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d7\7\26"+
		"\2\2\u00d6\u00c6\3\2\2\2\u00d6\u00c7\3\2\2\2\u00d6\u00c8\3\2\2\2\u00d6"+
		"\u00c9\3\2\2\2\u00d7\33\3\2\2\2\u00d8\u00d9\7\25\2\2\u00d9\u00da\5\6\4"+
		"\2\u00da\u00db\7\26\2\2\u00db\35\3\2\2\2\u00dc\u00dd\7\n\2\2\u00dd\u00e2"+
		"\5 \21\2\u00de\u00df\7\5\2\2\u00df\u00e1\5 \21\2\u00e0\u00de\3\2\2\2\u00e1"+
		"\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\37\3\2\2"+
		"\2\u00e4\u00e2\3\2\2\2\u00e5\u00e6\7\65\2\2\u00e6\u00e7\7\7\2\2\u00e7"+
		"\u00e8\7\65\2\2\u00e8\u00e9\t\7\2\2\u00e9\u00ea\7\65\2\2\u00ea\u00eb\7"+
		"\7\2\2\u00eb\u00f8\7\65\2\2\u00ec\u00ed\7\65\2\2\u00ed\u00f4\t\t\2\2\u00ee"+
		"\u00ef\7\27\2\2\u00ef\u00f0\t\4\2\2\u00f0\u00f1\7\b\2\2\u00f1\u00f2\t"+
		"\4\2\2\u00f2\u00f3\7)\2\2\u00f3\u00f5\7\30\2\2\u00f4\u00ee\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f8\7\65\2\2\u00f7\u00e5\3"+
		"\2\2\2\u00f7\u00ec\3\2\2\2\u00f8!\3\2\2\2\u00f9\u00fb\7,\2\2\u00fa\u00fc"+
		"\7+\2\2\u00fb\u00fa\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2\2\2\u00fd"+
		"\u00ff\7*\2\2\u00fe\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\3\2"+
		"\2\2\u0100\u0105\5$\23\2\u0101\u0102\7\5\2\2\u0102\u0104\5$\23\2\u0103"+
		"\u0101\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2"+
		"\2\2\u0106#\3\2\2\2\u0107\u0105\3\2\2\2\u0108\u010b\7\65\2\2\u0109\u010a"+
		"\7\7\2\2\u010a\u010c\7\65\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2"+
		"\u010c%\3\2\2\2\u010d\u010e\7-\2\2\u010e\u010f\7\62\2\2\u010f\'\3\2\2"+
		"\2&+\62\67:IOSW^emu|\u0082\u0085\u0088\u008b\u0091\u0099\u00a1\u00a8\u00ad"+
		"\u00b0\u00b5\u00b9\u00c1\u00d0\u00d3\u00d6\u00e2\u00f4\u00f7\u00fb\u00fe"+
		"\u0105\u010b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}