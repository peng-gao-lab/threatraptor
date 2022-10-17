package query.tbql.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.joda.time.DateTime;
import query.tbql.executor.TBQLUtils.ValType;
import query.tbql.executor.constraint.AttributeConstraint;
import query.tbql.executor.constraint.TBQLEventConstraint;
import query.tbql.executor.constraint.TBQLEventRelationConstraint;
import query.tbql.executor.constraint.TimeWindowConstraint;
import query.tbql.executor.datetime.TBQLDateTime;
import query.tbql.executor.datetime.TBQLDateTimeParser;
import query.tbql.executor.dbadaptor.DBAdapter;
import query.tbql.executor.dbadaptor.Neo4jDBProperties;
import query.tbql.executor.dbadaptor.PostgresDBProperties;
import query.tbql.executor.handler.TBQLExecutorErrorHandler;
import query.tbql.parser.TBQLParser;
import query.tbql.parser.TBQLParserBaseVisitor;

public class TBQLQueryParser extends TBQLParserBaseVisitor<String> {
	// TBQLQueryContext object
	TBQLQueryContext tbqlQueryContext;

	// Suffix used to generate missing IDs
	int idSuffix = 0;
	
	// Identifier for locating attribute expression
	String currIDForAttribute = "";

	// TBQLExecutorErrorHandler
	TBQLExecutorErrorHandler errHandler;

	// Parse datetime
	TBQLDateTimeParser tbqlDateTimeParser;
	
	// DB properties (for getting table names)
	PostgresDBProperties postgresDbProperty;
	Neo4jDBProperties neo4jDbProperty;
	
	// Constructor
	public TBQLQueryParser(TBQLExecutorErrorHandler errHandler, PostgresDBProperties postgresDbProperty, Neo4jDBProperties neo4jDbProperty) {
		this.errHandler = errHandler;
		tbqlDateTimeParser = new TBQLDateTimeParser(this.errHandler);
		this.postgresDbProperty = postgresDbProperty;
		this.neo4jDbProperty = neo4jDbProperty;
	}

	// Visit parse tree
	@Override
	public String visitStat(TBQLParser.StatContext ctx) {
		tbqlQueryContext = new TBQLQueryContext();

		// Visit the multievent query body
		visit(ctx.multieventQuery());

		// Visit globalConstraint
		if (ctx.glbcons.size() != 0) {
			tbqlQueryContext.globalConstraints = new ArrayList<>();
			for (ParseTree globalcon : ctx.glbcons) {
				visit(globalcon);
			}
		}

		// Update parsed global constraints to all TBQLQueryContext
		if (tbqlQueryContext.globalConstraints != null && tbqlQueryContext.globalConstraints.size() > 0) {
			for (TBQLEventConstraint globalConstraint : tbqlQueryContext.globalConstraints) {
				// Determine whether the constraint is an AttributeConstraint or a TimeWindowConstraint
				if (globalConstraint.getConstraintType().equals("timeWindow")) {
					// For each TBQLContext object
					for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
						// Store timewindow information
						TBQLDateTime tbqlDateTime = ((TimeWindowConstraint) globalConstraint).getTbqlDateTime();
						tbqlEventContext.tbqlDateTime = tbqlDateTime;
						tbqlEventContext.dbNameToTimeWindowStr.putAll(getDBNameToTimeWindowStr(tbqlEventContext, tbqlDateTime));

						// Update complexity score
						tbqlEventContext.complexityScore++;
					}
				} else {
					for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
						if (tbqlEventContext.synthesizeSQLQuery) {
							String sqlAttributeConstraintStr = DBAdapter.getSQLAttributeConstraintStr((AttributeConstraint) globalConstraint, tbqlEventContext.eventID);
							updateSQLWhere(tbqlEventContext, sqlAttributeConstraintStr);
						}
						else {
							String cypherAttributeConstraintStr = DBAdapter.getCypherAttributeConstraintStr((AttributeConstraint) globalConstraint, tbqlEventContext.eventID);
							updateCypherWhere(tbqlEventContext, cypherAttributeConstraintStr);
						}

						// Update complexity score
						tbqlEventContext.complexityScore++;
					}
				}
			}
		}

		// If any TBQLContext object has no TBQLDateTime, we put null
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
			if (tbqlEventContext.tbqlDateTime == null) {
				tbqlEventContext.dbNameToTimeWindowStr.putAll(getDBNameToTimeWindowStr(tbqlEventContext, null));
			}
		}

		// Append newline to SQL from, where
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
			if (tbqlEventContext.synthesizeSQLQuery) {
				appendSQLNewlineFromWhere(tbqlEventContext);
			}
			else {
				appendCypherNewlineMatchWhere(tbqlEventContext);
			}
		}

		return "";
	}

	private void updateSQLWhere(TBQLEventContext tbqlEventContext, String s) {
		if (!tbqlEventContext.sqlQueryContext.getWhere().toString().equals("where ")) {
			tbqlEventContext.sqlQueryContext.getWhere().append("\n and ");
		}
		tbqlEventContext.sqlQueryContext.getWhere().append("(" + s + ")");
	}
	
	private void updateCypherWhere(TBQLEventContext tbqlEventContext, String s) {
		if (!tbqlEventContext.cypherQueryContext.getWhere().toString().equals("where ")) {
			tbqlEventContext.cypherQueryContext.getWhere().append("\n and ");
		}
		tbqlEventContext.cypherQueryContext.getWhere().append("(" + s + ")");
	}
	
	private void updateSQLSelect(TBQLEventContext tbqlEventContext, String s) {
		tbqlEventContext.sqlQueryContext.getSelect().append(s);
	}
	
	private void updateCypherReturnStr(TBQLEventContext tbqlEventContext, String s) {
		tbqlEventContext.cypherQueryContext.getReturnStr().append(s);
	}
	
	private void updateSQLFrom(TBQLEventContext tbqlEventContext) {
		switch (tbqlEventContext.eventType) {
		case "fileevent":
			// from processlet p, path f, fileevent evt (We do not use file f
			// due to the data issue.)
			tbqlEventContext.sqlQueryContext.getFrom().append(postgresDbProperty.getProcessTable() + " " + tbqlEventContext.subjectID + ", " + postgresDbProperty.getFileTable() + " " + tbqlEventContext.objectID
					+ ", " + postgresDbProperty.getFileEventTable() + " " + tbqlEventContext.eventID);
			break;
		case "processletevent":
			// from processlet p1, processlet p2, processletevent evt
			tbqlEventContext.sqlQueryContext.getFrom().append(postgresDbProperty.getProcessTable() + " " + tbqlEventContext.subjectID + ", " + postgresDbProperty.getProcessTable() + " " + tbqlEventContext.objectID
					+ ", " + postgresDbProperty.getProcessEventTable() + " " + tbqlEventContext.eventID);
			break;
		case "ipchannelevent":
			// from processlet p, ipchannel i, ipchannelevent evt
			tbqlEventContext.sqlQueryContext.getFrom().append(postgresDbProperty.getProcessTable() + " " + tbqlEventContext.subjectID + ", " + postgresDbProperty.getNetworkTable() + " " + tbqlEventContext.objectID
					+ ", " + postgresDbProperty.getNetworkEventTable() + " " + tbqlEventContext.eventID);
			break;
		default:
			errHandler.handleUndefinedError("Undefined event type: " + tbqlEventContext.eventType);
		}
	}
	
	private void updateCypherMatch(TBQLEventContext tbqlEventContext) {
		String startNode = "(" + tbqlEventContext.subjectID + ":" + neo4jDbProperty.getProcessTable() + ")";
		
		String endNode = "";
		String edge = "";
		switch (tbqlEventContext.eventType) {
		case "fileevent":
			endNode = "(" + tbqlEventContext.objectID + ":" + neo4jDbProperty.getFileTable() + ")";
			edge = "[" + tbqlEventContext.eventID + ":" + neo4jDbProperty.getFileEventTable() + "]";
			break;
		case "processletevent":
			endNode = "(" + tbqlEventContext.objectID + ":" + neo4jDbProperty.getProcessTable() + ")";
			edge = "[" + tbqlEventContext.eventID + ":" + neo4jDbProperty.getProcessEventTable() + "]";
			break;
		case "ipchannelevent":
			endNode = "(" + tbqlEventContext.objectID + ":" + neo4jDbProperty.getNetworkTable() + ")";
			edge = "[" + tbqlEventContext.eventID + ":" + neo4jDbProperty.getNetworkEventTable() + "]";
			break;
		default:
			errHandler.handleUndefinedError("Undefined event type: " + tbqlEventContext.eventType);
		}
		
		if (tbqlEventContext.pathLengthBoundaryLeft == 0 && tbqlEventContext.pathLengthBoundaryRight == 0) {
			// Length-1 path, i.e., single-event
			edge = "-" + edge + "->";
		}
		else {
			String vPathPart = "*";
			if (tbqlEventContext.pathLengthBoundaryLeft == tbqlEventContext.pathLengthBoundaryRight) {
				vPathPart += Integer.toString(tbqlEventContext.pathLengthBoundaryLeft - 1);
			}
			else {
				if (tbqlEventContext.pathLengthBoundaryRight == Integer.MAX_VALUE) {
					vPathPart += Integer.toString(tbqlEventContext.pathLengthBoundaryLeft - 1) + "..";
				}
				else {
					vPathPart += (tbqlEventContext.pathLengthBoundaryLeft - 1) + ".." + (tbqlEventContext.pathLengthBoundaryRight - 1);
				}
			}
			
			edge = "-[" + vPathPart + "]->(x)-" + edge + "->";
		}
		
		tbqlEventContext.cypherQueryContext.getMatch().append(startNode + edge + endNode);
	}
	
	private void appendSQLNewlineFromWhere(TBQLEventContext tbqlEventContext) {
		tbqlEventContext.sqlQueryContext.getFrom().append("\n");
		tbqlEventContext.sqlQueryContext.getWhere().append("\n");
	}
	
	private void appendCypherNewlineMatchWhere(TBQLEventContext tbqlEventContext) {
		tbqlEventContext.cypherQueryContext.getMatch().append("\n");
		tbqlEventContext.cypherQueryContext.getWhere().append("\n");
	}
	
	private String sqlToCypherRegex(String s) {
		// s is single quoted string
		s = s.replaceAll("%", ".*");
//		s = s.charAt(0) + "(?i)" + s.substring(1);
		return s;
	}

	// Global constraints
	@Override
	public String visitAttributeGlobalConstraint(TBQLParser.AttributeGlobalConstraintContext ctx) {
		String id = ctx.key.getText();
		String op = ctx.op.getText();
		String val = "";
		boolean negation = false;
		ValType valType = null;;
		
		if (ctx.op.getType() == TBQLParser.UNEQUAL) {
			negation = true;
		}
		
		if (ctx.val != null) {
			val = ctx.val.getText();
			
			if (ctx.val.getType() == TBQLParser.INT || ctx.val.getType() == TBQLParser.FLOAT) {
				valType = ValType.NUM;
			}
			else { // convert to single quote string
				if (ctx.op.getType() != TBQLParser.EQUAL && ctx.op.getType() != TBQLParser.UNEQUAL) {
					errHandler.handleIDError("optype undefined for string value " + op);
				}
				
				valType = ValType.STRING;
				val = TBQLUtils.toSingleQuoteString(val);
			}
		}
		else if (ctx.coll != null && ctx.op.getType() == TBQLParser.IN) {
			valType = ValType.SET;
			val = visit(ctx.coll);
			
			if (ctx.not != null) {
				negation = true;
			}
		}
		else {
			errHandler.handleUndefinedError("Undefined value for global constraints");
		}
		
		// Parse results to globalConstraints of each TBQLQueryContext object in queryContexts
		TBQLEventConstraint tbqlEventConstraint = new AttributeConstraint(id, op, val, negation, valType); // TBQL-style AttributeConstraint
		tbqlQueryContext.globalConstraints.add(tbqlEventConstraint);
		
		return "";
	}
	
	@Override
	public String visitTimeWindowGlobalConstraint(TBQLParser.TimeWindowGlobalConstraintContext ctx) {
		TBQLDateTime tbqlDateTime = TBQLDateTime.fromJsonString(visit(ctx.tcons));
		
		// Pass to globalConstraints
		TBQLEventConstraint tbqlEventConstraint = new TimeWindowConstraint(tbqlDateTime);		
		tbqlQueryContext.globalConstraints.add(tbqlEventConstraint);
		
		return "";
	}

	@Override
	public String visitAtTimeConstraint(TBQLParser.AtTimeConstraintContext ctx) {
		String dateTime = TBQLUtils.trimBeginEndQuotes(ctx.t.getText());
		TBQLDateTime tbqlDateTime = tbqlDateTimeParser.parseAtTime(dateTime);
	
		return tbqlDateTime.toJsonString();
	}
	
	@Override
	public String visitFromToTimeConstraint(TBQLParser.FromToTimeConstraintContext ctx) {
		String dateTime1 = TBQLUtils.trimBeginEndQuotes(ctx.t1.getText());
		String dateTime2 = TBQLUtils.trimBeginEndQuotes(ctx.t2.getText());
		TBQLDateTime tbqlDateTime = tbqlDateTimeParser.parseFromToTime(dateTime1, dateTime2);
		
		return tbqlDateTime.toJsonString();
	}

	@Override
	public String visitLastTimeConstraint(TBQLParser.LastTimeConstraintContext ctx) {
		float amount = Float.parseFloat(ctx.amount.getText());
		String unit = tbqlDateTimeParser.normalizeTimeUnit(ctx.unit.getText());
		TBQLDateTime tbqlDateTime = tbqlDateTimeParser.parseLastTime(amount, unit);

		return tbqlDateTime.toJsonString();
	}

	@Override
	public String visitBeforeTimeConstraint(TBQLParser.BeforeTimeConstraintContext ctx) {
		String dateTime = TBQLUtils.trimBeginEndQuotes(ctx.t.getText());
		TBQLDateTime tbqlDateTime = tbqlDateTimeParser.parseBeforeTime(dateTime);

		return tbqlDateTime.toJsonString();
	}
	
	@Override
	public String visitAfterTimeConstraint(TBQLParser.AfterTimeConstraintContext ctx) {
		String dateTime = TBQLUtils.trimBeginEndQuotes(ctx.t.getText());
		TBQLDateTime tbqlDateTime = tbqlDateTimeParser.parseAfterTime(dateTime);
		
		return tbqlDateTime.toJsonString();
	}

	// Edge
	@Override
	public String visitMultieventQuery(TBQLParser.MultieventQueryContext ctx) {		
		// defs
		for (ParseTree def : ctx.defs) {
			visit(def);
		}

		// withClause
		if (ctx.withClause() != null) {
			visit(ctx.withClause());
		}

		// returnClause
		visit(ctx.returnClause());
		
		// limit
		if (ctx.limit() != null) {
			tbqlQueryContext.limitValue = Integer.parseInt(visit(ctx.limit()));
		}

		return "";
	}

	// Definitions
	@Override
	public String visitNormalEventPattern(TBQLParser.NormalEventPatternContext ctx) {
		TBQLEventContext tbqlEventContext = new TBQLEventContext(); 
		tbqlQueryContext.eventPatternContexts.add(tbqlEventContext);

		// Visit subject, object
		tbqlEventContext.subjectID = visit(ctx.sub);
		tbqlEventContext.objectID = visit(ctx.obj);

		// Visit event
		visit(ctx.evt);

		// Visit operation
		tbqlEventContext.opType = visit(ctx.op);
		updateSQLWhere(tbqlEventContext, tbqlEventContext.opType);
		
		// Update Cypher "from" clause
		updateSQLFrom(tbqlEventContext);

		// Visit timewindow
		if (ctx.time != null) {
			visit(ctx.time);
		}

		return "";
	}
	
	@Override
	public String visitVarPathPattern(TBQLParser.VarPathPatternContext ctx) {
		TBQLEventContext tbqlEventContext = new TBQLEventContext(); 
		tbqlQueryContext.eventPatternContexts.add(tbqlEventContext);
		
		// Variable length path, need to synthesize Cypher query
		tbqlEventContext.synthesizeSQLQuery = false;
		tbqlEventContext.isVarPath = true;

		// Visit subject, object
		tbqlEventContext.subjectID = visit(ctx.sub);
		tbqlEventContext.objectID = visit(ctx.obj);

		// Visit event
		visit(ctx.evt);

		// Visit operation
		tbqlEventContext.opType = visit(ctx.op);
		if (!tbqlEventContext.opType.equals("none")) {
			updateCypherWhere(tbqlEventContext, tbqlEventContext.opType);
		}
		
		// Update Cypher match clause
		updateCypherMatch(tbqlEventContext);

		return "";
	}
	

	@Override
	public String visitEntity(TBQLParser.EntityContext ctx) {
		// Get the corresponding TBQLContext object
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts
				.get(tbqlQueryContext.eventPatternContexts.size() - 1);

		// Entity id
		String id = ctx.id.getText();
		if (id.length() >= 2 && id.substring(0, 2).equals("__")) {
			errHandler.handleIDError("ID cannot start with '__'.");
		}

		int TBQLEventContextIndex = -1;
		if (tbqlQueryContext.eventPatternContexts.size() >= 2) { // ignore the current TBQLEventContext object
			for (int i = 0; i < tbqlQueryContext.eventPatternContexts.size() - 1; i++) {
				TBQLEventContext currTBQLContext = tbqlQueryContext.eventPatternContexts.get(i);
				if (currTBQLContext.subjectID.equals(id) || currTBQLContext.objectID.equals(id)
						|| currTBQLContext.eventID.equals(id)) {
					TBQLEventContextIndex = i;
					break;
				}
			}
		}

		if (TBQLEventContextIndex > -1) {
			// Generate a new id
			String newID = "__gen_" + idSuffix + "_dup";
			idSuffix++;

			// Add id.id = newID.id to the attribute relationship constraints.
			TBQLEventRelationConstraint eventRelationConstraint = new TBQLEventRelationConstraint("attributeRelationConstraint",
					"tmpEventID__" + tbqlQueryContext.eventPatternContexts.indexOf(tbqlEventContext), newID, "id", 
					tbqlQueryContext.eventPatternContexts.get(TBQLEventContextIndex).eventID, id, "id", "=");
			
			tbqlQueryContext.eventRelationConstraints.add(eventRelationConstraint);

			// Update id to newID
			id = newID;
		}
	
		// Entity type
		String type = "";
		switch (ctx.type.getText()) {
		case "file":
			type = "file";
			break;
		case "proc":
			type = "processlet";
			break;
		case "ip":
			type = "ipchannel";
			break;
		default:
			errHandler.handleUndefinedError("Undefined subject type: " + ctx.type.getText());
		}

		tbqlEventContext.idTypeMap.put(id, type);

		// Update currIDForAttribute
		currIDForAttribute = id;

		// Visit attributeExpression
		if (ctx.attributeExpression() != null) {
			// [('%pgao%' || ('%word%', id = 3)), pid = 5]
			String attributeExpression = visit(ctx.attributeExpression());
			
			// Update SQL "where"
			if (tbqlEventContext.synthesizeSQLQuery) {
				updateSQLWhere(tbqlEventContext, attributeExpression);
			}
			else {
				updateCypherWhere(tbqlEventContext, attributeExpression);
			}
			
			// Update complexity score
			tbqlEventContext.complexityScore += computeComplexityScoreOfAttributeExpr(attributeExpression);
		}
		return id;
	}

	@Override
	public String visitEvent(TBQLParser.EventContext ctx) {
		// Get the corresponding TBQLEventContext object
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts
				.get(tbqlQueryContext.eventPatternContexts.size() - 1);

		// Store eventID
		tbqlEventContext.eventID = ctx.id.getText();
		if (tbqlEventContext.eventID.length() >= 2 && tbqlEventContext.eventID.substring(0, 2).equals("__")) {
			errHandler.handleIDError("ID cannot start with '__'.");
		}
		
		// We do not allow the same eventID used before
		int TBQLContextIndex = -1;
		if (tbqlQueryContext.eventPatternContexts.size() >= 2) {
			for (int i = 0; i < tbqlQueryContext.eventPatternContexts.size() - 1; i++) {
				TBQLEventContext currTBQLContext = tbqlQueryContext.eventPatternContexts.get(i);
				if (currTBQLContext.subjectID.equals(tbqlEventContext.eventID)
						|| currTBQLContext.objectID.equals(tbqlEventContext.eventID)
						|| currTBQLContext.eventID.equals(tbqlEventContext.eventID)) {
					TBQLContextIndex = i;
					break;
				}
			}
		}

		if (TBQLContextIndex > -1) {
			errHandler.handleIDError("The same eventID has been used before");
		}

		// Replace any temporary eventIDs in queryContexts.get(currBqlQueryContextIndex).attributeRelations
		for (TBQLEventRelationConstraint eventRelationConstraint : tbqlQueryContext.eventRelationConstraints) {
			if (eventRelationConstraint.getConstraintType().equals("attributeRelationConstraint")) {
				if (eventRelationConstraint.getEventIDLeft()
						.equals("tmpEventID__" + tbqlQueryContext.eventPatternContexts.indexOf(tbqlEventContext))) {
					eventRelationConstraint.setEventIDLeft(tbqlEventContext.eventID);
				}
			}
		}
		
		// Infer eventType
		tbqlEventContext.eventType = tbqlEventContext.idTypeMap.get(tbqlEventContext.objectID) + "event";
		tbqlEventContext.idTypeMap.put(tbqlEventContext.eventID, tbqlEventContext.eventType);

		// Join entity tables with event table
		if (tbqlEventContext.synthesizeSQLQuery) {
			String tableJoinStr = tbqlEventContext.eventID + ".srcid = " + tbqlEventContext.subjectID + ".id" + " and " + tbqlEventContext.eventID + ".dstid = " + tbqlEventContext.objectID + ".id";
			updateSQLWhere(tbqlEventContext, tableJoinStr);
		}

		// Update currIDForAttribute
		currIDForAttribute = tbqlEventContext.eventID;

		// Visit attributeExpression
		if (ctx.attributeExpression() != null) {
			// e.g., [('%chrome%' || ('%word%', id = 3)), pid = 5]
			String attributeExpression = visit(ctx.attributeExpression());

			// Update SQL "where"
			if (tbqlEventContext.synthesizeSQLQuery) {
				updateSQLWhere(tbqlEventContext, attributeExpression);
			}
			else {
				updateCypherWhere(tbqlEventContext, attributeExpression);
			}

			// Update complexity score
			tbqlEventContext.complexityScore += computeComplexityScoreOfAttributeExpr(attributeExpression);
		}

		return "";
	}
	
	@Override
	public String visitPathOperation(TBQLParser.PathOperationContext ctx) {
		// Variable length path syntax
		
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts
				.get(tbqlQueryContext.eventPatternContexts.size() - 1);
		
		if (ctx.arrow.getType() == TBQLParser.RIGHTARROW) {
			// Just the lengh-1 path, or single-event
		}
		else {
			// Compile to variable length path
			
			if (ctx.left == null && ctx.TILDE() == null && ctx.right == null) {
				tbqlEventContext.pathLengthBoundaryLeft = 1;
				tbqlEventContext.pathLengthBoundaryRight = Integer.MAX_VALUE;
			}
			else {
				if (ctx.TILDE() == null) {
					tbqlEventContext.pathLengthBoundaryLeft = Integer.parseInt(ctx.left.getText());
					tbqlEventContext.pathLengthBoundaryRight = Integer.parseInt(ctx.left.getText());
				}
				else {
					int tmpLeft = 1;
					int tmpRight = Integer.MAX_VALUE;
					
					if (ctx.left != null) {
						tmpLeft = Integer.parseInt(ctx.left.getText());
						if (tmpLeft < 1) {
							errHandler.handleOtherError("VPath length undefined: " + tmpLeft);
						}
						tbqlEventContext.pathLengthBoundaryLeft = tmpLeft;
					}
					else {
						tbqlEventContext.pathLengthBoundaryLeft = 1;
					}
					
					if (ctx.right != null) {
						tmpRight = Integer.parseInt(ctx.right.getText());
						if (tmpRight < 1) {
							errHandler.handleOtherError("VPath length undefined: " + tmpRight);
						}
						tbqlEventContext.pathLengthBoundaryRight = tmpRight;
					}
					else {
						tbqlEventContext.pathLengthBoundaryRight = Integer.MAX_VALUE;
					}
					
					if (tmpLeft > tmpRight) {
						errHandler.handleOtherError("VPath length not allowed: " + tmpLeft + " " + tmpRight);
					}
				}
			}
		}

		if (ctx.operation() != null) {
			return visit(ctx.operation());
		}
		else {
			return "none";
		}
	}

	@Override
	public String visitOpOperation(TBQLParser.OpOperationContext ctx) {
		String result = "";
		switch (ctx.op.getType()) {
		case TBQLParser.LOGICALAND:
			result = visit(ctx.left) + " and " + visit(ctx.right);
			break;
		case TBQLParser.LOGICALOR:
			result = visit(ctx.left) + " or " + visit(ctx.right);
			break;
		default:
			errHandler.handleUndefinedError("Undefined logical operation: " + ctx.op.getText());
		}

		return result;
	}

	@Override
	public String visitAtomOperation(TBQLParser.AtomOperationContext ctx) {
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts
				.get(tbqlQueryContext.eventPatternContexts.size() - 1);

		// Parse ctx.atom
		String result = "";
		switch(tbqlEventContext.eventType) {
		case "fileevent":
			if (ctx.atom.getText().equals("read")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'read\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'readv\'" + ")";
			}
			else if (ctx.atom.getText().equals("write")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'write\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'writev\'" + ")";
			}
			else if (ctx.atom.getText().equals("execute")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'execve\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'execute\'" + ")";
			}
			else if (ctx.atom.getText().equals("rename") ) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'rename\'" + ")";
			}
			else if (ctx.atom.getText().equals("unlink") ) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'unlink\'" + ")";
			}
			else if (ctx.atom.getText().equals("open") ) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'open\'" + ")";
			}
			else if (ctx.atom.getText().equals("modify") ) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'modify_file_attributes\'" + ")";
			}
			else if (ctx.atom.getText().equals("create") ) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'create_object\'" + ")";
			}
			else {
				errHandler.handleUndefinedError("Undefined event op type: " + ctx.atom.getText());
			}
			break;
		case "processletevent":
			if (ctx.atom.getText().equals("start")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'execve\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'execute\'"
						+ " or " + tbqlEventContext.eventID + ".optype = \'clone\'"  
						+ ")";
			}
			else {
				errHandler.handleUndefinedError("Undefined event op type: " + ctx.atom.getText());
			}
			break;
		case "ipchannelevent":
			if (ctx.atom.getText().equals("read")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'read\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'readv\'"
						+ " or " + tbqlEventContext.eventID + ".optype = \'recvmsg\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'recvfrom\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'read_socket_params\'" 
						+ ")";
			}
			else if (ctx.atom.getText().equals("write")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'write\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'writev\'"
						+ " or " + tbqlEventContext.eventID + ".optype = \'sendto\'" 
						+ " or " + tbqlEventContext.eventID + ".optype = \'sendmsg\'"
						+ " or " + tbqlEventContext.eventID + ".optype = \'write_socket_params\'"
						+ ")";
			}
			else if (ctx.atom.getText().equals("connect")) {
				result = "(" + tbqlEventContext.eventID + ".optype = \'connect\'" + ")";
			}
			else {
				errHandler.handleUndefinedError("Undefined event op type: " + ctx.atom.getText());
			}
			break;
		default:
			errHandler.handleUndefinedError("Undefined event type: " + tbqlEventContext.eventType);
		}

		return result;
	}

	@Override
	public String visitParenOperation(TBQLParser.ParenOperationContext ctx) {
		return "(" + visit(ctx.operation()) + ")";
	}

	// Attributes
	private int computeComplexityScoreOfAttributeExpr(String attributeExpression) {
		String plainAttributeExpression = attributeExpression.replace("(", "").replace(")", "");
		int score = 0;
		for (String str : plainAttributeExpression.split("or")) {
			score += str.split("and").length;
		}
		return score;
	}

	@Override
	public String visitOpAttributeExpr(TBQLParser.OpAttributeExprContext ctx) {
		String exprLeft = visit(ctx.left);
		String exprRight = visit(ctx.right);
		String op = "";
		switch (ctx.op.getType()) {
		case TBQLParser.LOGICALAND:
			op = " and ";
			break;
		case TBQLParser.LOGICALOR:
			op = " or ";
			break;
		default:
			errHandler.handleUndefinedError("Undefined logical operation: " + ctx.op.getText());
		}
		return exprLeft + op + exprRight;
	}

	@Override
	public String visitAtomAttributeExpr(TBQLParser.AtomAttributeExprContext ctx) {
		return visit(ctx.atom);
	}

	@Override
	public String visitParenAttributeExpr(TBQLParser.ParenAttributeExprContext ctx) {
		return "(" + visit(ctx.attributeExpression()) + ")";
	}

	@Override
	public String visitAttributeConstraintFull(TBQLParser.AttributeConstraintFullContext ctx) {
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts
				.get(tbqlQueryContext.eventPatternContexts.size() - 1);

		// Get the id that the attribute belongs to
		String id = currIDForAttribute;
		
		// Get attribute name
		boolean useDefaultAttribute = (ctx.id == null);
		boolean hasNotOp = (ctx.not != null);
		if (!useDefaultAttribute && hasNotOp) {
			errHandler.handleUndefinedError("Unsupported attribute format.");
		}
		
		String attributeName = "";
		if (!useDefaultAttribute) {
			attributeName = ctx.id.getText();
		}
		else { // default attribute
			String type = tbqlEventContext.idTypeMap.get(id);
			attributeName = TBQLQueryContext.getDefaultAttribute(type);
		}
		
		// Get attribute value
		String value = ctx.val.getText();

		// Prepare the return expression
		String returnExpr = "";
		if (ctx.val.getType() == TBQLParser.INT || ctx.val.getType() == TBQLParser.FLOAT) {
			if (!useDefaultAttribute) {
				switch (ctx.op.getType()) {
				case TBQLParser.EQUAL:
				case TBQLParser.GT:
				case TBQLParser.LT:
				case TBQLParser.GEQ:
				case TBQLParser.LEQ:
					returnExpr = id + "." + attributeName + " " + ctx.op.getText() + " " + value;
					break;
				case TBQLParser.UNEQUAL:
					returnExpr = id + "." + attributeName + " <> " + value;
					break;
				default:
					errHandler.handleUndefinedError("Undefined assignment operation: " + ctx.op.getText());
				}
			}
			else {
				if (!hasNotOp) {
					returnExpr = id + "." + attributeName + " = " + value;
				}
				else {
					returnExpr = id + "." + attributeName + " <> " + value;
				}
			}
		} 
		else { // must be STRING, otherwise error will be caught by parser
			value = TBQLUtils.toSingleQuoteString(value);
			
			if ((!useDefaultAttribute && ctx.op.getType() == TBQLParser.EQUAL) || (useDefaultAttribute && !hasNotOp)) { // ilike
				// If is ipchannelevent and attribute is srcip/dstip	
				if (attributeName.equals("srcip") || attributeName.equals("dstip")) {
					returnExpr = id + "." + attributeName + " = " + value; // cidr
				} else {					
					if (tbqlEventContext.synthesizeSQLQuery) {
						returnExpr = id + "." + attributeName + " ilike " + value;
					}
					else {
						value = sqlToCypherRegex(value);
						returnExpr = "toLower(" + id + "." + attributeName + ") =~ " + value;
					}
				}
			} 
			else if ((!useDefaultAttribute && ctx.op.getType() == TBQLParser.UNEQUAL) || (useDefaultAttribute && hasNotOp)) { // not ilike
				if (attributeName.equals("srcip") || attributeName.equals("dstip")) {
					returnExpr = id + "." + attributeName + " <> " + value;
				} else {
					if (tbqlEventContext.synthesizeSQLQuery) {
						returnExpr = id + "." + attributeName + " not ilike " + value;
					}
					else {
						value = sqlToCypherRegex(value);
						returnExpr = "not(" + "toLower(" + id + "." + attributeName + ") =~ " + value + ")";
					}
				}
			} 
			else {
				errHandler.handleUndefinedError("Undefined assignment operation for string: " + ctx.op.getText());
			}
		}

		return returnExpr;
	}

	@Override
	public String visitAttributeConstraintCollection(TBQLParser.AttributeConstraintCollectionContext ctx) {
		// Get the id that the attribute belongs to
		String id = currIDForAttribute;

		// Get the attribute and value
		String attribute = ctx.id.getText();
		String value = visit(ctx.col);

		// Prepare return expression
		String returnExpr = "";
		if (ctx.not == null) {
			returnExpr = id + "." + attribute + " in " + value;
		} else {
			returnExpr = id + "." + attribute + " not in " + value;
		}

		return returnExpr;
	}

	@Override
	public String visitCollection(TBQLParser.CollectionContext ctx) {
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts
				.get(tbqlQueryContext.eventPatternContexts.size() - 1);
		
		String result = "";
		for (ParseTree collelem : ctx.collelems) {
			result += visit(collelem) + ", ";
		}
		result = result.substring(0, result.length() - 2);
		
		if (tbqlEventContext.synthesizeSQLQuery) {
			return "(" + result + ")";
		}
		else {
			return "[" + result + "]";
		}
	}

	@Override
	public String visitCollectionElement(TBQLParser.CollectionElementContext ctx) {
		String value = ctx.getText();

		// Double quotes to single quotes for strings
		if (ctx.val.getType() == TBQLParser.STRING) {
			value = TBQLUtils.toSingleQuoteString(value);
		}

		return value;
	}

	// timewindow
	@Override
	public String visitTimeWindow(TBQLParser.TimeWindowContext ctx) {
		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts.get(tbqlQueryContext.eventPatternContexts.size() - 1);
		
		TBQLDateTime tbqlDateTime = TBQLDateTime.fromJsonString(visit(ctx.tcons));
		tbqlEventContext.tbqlDateTime = tbqlDateTime;
		tbqlEventContext.dbNameToTimeWindowStr.putAll(getDBNameToTimeWindowStr(tbqlEventContext, tbqlDateTime));
		
		// Update complexity score
		tbqlEventContext.complexityScore++;
		
		return "";
	}
	
	private Map<String, String> getDBNameToTimeWindowStr(TBQLEventContext tbqlEventContext, TBQLDateTime tbqlDateTime) {
		// Give a TBQLEventContext and a TBQLDateTime, generate the dbNameToTimeWindowStr map for the TBQLEventContext
		Map<String, String> dbNameToTimeWindowStr = new HashMap<>();
		
		String dbName;
		if (tbqlEventContext.synthesizeSQLQuery) {
			dbName = postgresDbProperty.getDatabase();
		}
		else {
			dbName = neo4jDbProperty.getDatabase();
		}
		
		String timeWindowConstraintStr;
		if (tbqlDateTime == null) {
			timeWindowConstraintStr = "";
		}
		else {
			DateTime startDateTime = tbqlDateTimeParser.millisecondsToDateTime(tbqlDateTime.getStartDateTimeUTC());
			DateTime endDateTime = tbqlDateTimeParser.millisecondsToDateTime(tbqlDateTime.getEndDateTimeUTC());
			timeWindowConstraintStr = getTimeWindowConstraintStr(tbqlEventContext, new TBQLDateTime("fromto", startDateTime, endDateTime));
		}
		
		dbNameToTimeWindowStr.put(dbName, timeWindowConstraintStr);
		return dbNameToTimeWindowStr;
	}
	
	private String getTimeWindowConstraintStr(TBQLEventContext tbqlEventContext, TBQLDateTime tbqlDateTime) {
		String timeWindowConstraintStr = "";
		if (tbqlEventContext.eventType.equals("processletevent")) {
			timeWindowConstraintStr = "(" + tbqlEventContext.eventID
					+ ".starttime >= " + tbqlDateTime.getStartDateTimeUTC() + " and "
					+ tbqlEventContext.eventID
					+ ".starttime <= " + tbqlDateTime.getEndDateTimeUTC() + ")";
		}
		else {
			timeWindowConstraintStr = "(" + tbqlEventContext.eventID
					+ ".starttime >= " + tbqlDateTime.getStartDateTimeUTC() + " and "
					+ tbqlEventContext.eventID
					+ ".endtime <= " + tbqlDateTime.getEndDateTimeUTC() + ")";
		}
		
		return timeWindowConstraintStr;
	}
	
	private String getEventIDForEntityID(TBQLEventContext tbqlEventContext, String id) {
		String eventID = "";
		if (tbqlEventContext.subjectID.equals(id) || tbqlEventContext.objectID.equals(id) || tbqlEventContext.eventID.equals(id)) {
			eventID = tbqlEventContext.eventID;
		}
		return eventID;
	}

	// Relations
	@Override
	public String visitAttributeRelation(TBQLParser.AttributeRelationContext ctx) {
		// TBQL example: p1.exename = p2.exename, f1.name = f2.name, evt1.agentid = evt2.agentid
		// Unified format: [evt1, entity1, attribute1, evt2, entity2, attribute2, rel]

		// Obtain fields
		String id1 = ctx.id1.getText(); // subject, object, or eventID
		String attribute1 = ctx.attr1.getText();
		String id2 = ctx.id2.getText();
		String attribute2 = ctx.attr2.getText();

		// Input validation
		String eventID1 = "";
		String eventID2 = "";
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
			eventID1 = getEventIDForEntityID(tbqlEventContext, id1);
			if (!eventID1.equals("")) {
				break;
			}
		}
		for (TBQLEventContext tbqlEventContext : tbqlQueryContext.eventPatternContexts) {
			eventID2 = getEventIDForEntityID(tbqlEventContext, id2);
			if (!eventID2.equals("")) {
				break;
			}
		}

		if (eventID1.equals("")) {
			errHandler.handleIDError(id1 + " is undefined.");
		}
		if (eventID2.equals("")) {
			errHandler.handleIDError(id2 + " is undefined.");
		}
		
		String relation = ctx.op.getText();

		// Store parsed results
		TBQLEventRelationConstraint eventRelationConstraint = new TBQLEventRelationConstraint("attributeRelationConstraint", eventID1, 
				id1, attribute1, eventID2, id2, attribute2, relation);
		tbqlQueryContext.eventRelationConstraints.add(eventRelationConstraint);

		return "";
	}

	@Override
	public String visitTemporalRelation(TBQLParser.TemporalRelationContext ctx) {
		// TBQL example: evt1 before[1-3 min] evt2
		// Unified format: [evt1, evt1, starttime, evt2, evt2, starttime, "before\t0\txxx"]
		
		String evtID1 = ctx.evt1.getText();
		String evtID2 = ctx.evt2.getText();

		// Visit the temporal relation node
		String relStr = ctx.type.getText();
		
		if (ctx.num1 != null) { // evt1 before[2-3 minutes] evt2
			// Find unit
			String unit = tbqlDateTimeParser.normalizeTimeUnit(ctx.unit.getText());

			// Find numMilliseconds
			float amountSmall = Float.parseFloat(ctx.num1.getText());
			float amountLarge = Float.parseFloat(ctx.num2.getText());

			// Return if either is less than 0; or amountSmall > amountLarge
			if (amountSmall < 0 || amountLarge < 0 || amountSmall > amountLarge) {
				System.out.println(amountSmall + " and " + amountLarge + " should be >= 0, and " + amountSmall + " < "
						+ amountLarge);
				return "";
			}

			long numMillisecondsSmall = tbqlDateTimeParser.durationInMilliseconds(amountSmall, unit);
			long numMillisecondsLarge = tbqlDateTimeParser.durationInMilliseconds(amountLarge, unit);

			// Append to res
			relStr = relStr + "\t" + Long.toString(numMillisecondsSmall) + "\t" + Long.toString(numMillisecondsLarge);
		} else { // evt1 before evt2
			long numMillisecondsSmall = 0;
			long numMillisecondsLarge = Long.MAX_VALUE;
			relStr = relStr + "\t" + Long.toString(numMillisecondsSmall) + "\t" + Long.toString(numMillisecondsLarge);
		}

		// Update
		TBQLEventRelationConstraint eventRelationConstraint = new TBQLEventRelationConstraint("temporalRelationConstraint",
				evtID1, evtID1, "starttime", evtID2, evtID2, "starttime", relStr);
		tbqlQueryContext.eventRelationConstraints.add(eventRelationConstraint);
				
		return "";
	}

	// Results
	@Override
	public String visitReturnClause(TBQLParser.ReturnClauseContext ctx) {
		for (ParseTree re : ctx.res) {
			visit(re);
		}

		// Prepare SQL "select"
		for (TBQLEventContext tbqlContext : tbqlQueryContext.eventPatternContexts) {
			if (tbqlContext.synthesizeSQLQuery) {
				if (tbqlQueryContext.returnEventIDs.contains(tbqlContext.eventID)) {
					tbqlContext.sqlQueryContext.getSelect().setLength(tbqlContext.sqlQueryContext.getSelect().length() - 2);
				} else {
					// Append eventID.id to select
					tbqlContext.sqlQueryContext.getSelect().append(tbqlContext.eventID + ".id");
				}
				tbqlContext.sqlQueryContext.getSelect().append("\n");
			}
			else {
				if (tbqlQueryContext.returnEventIDs.contains(tbqlContext.eventID)) {
					tbqlContext.cypherQueryContext.getReturnStr().setLength(tbqlContext.cypherQueryContext.getReturnStr().length() - 2);
				} else {
					// Append eventID.id to select
					tbqlContext.cypherQueryContext.getReturnStr().append(tbqlContext.eventID + ".id");
				}
				tbqlContext.cypherQueryContext.getReturnStr().append("\n");
			}
		}

		// Whether to return distinct value
		if (ctx.distinct != null) {
			tbqlQueryContext.returnDistinct = true;
		}

		// Whether to return count
		if (ctx.count != null) {
			tbqlQueryContext.returnCount = true;
		}

		return "";		
	}

	@Override
	public String visitResult(TBQLParser.ResultContext ctx) {
		String id = ctx.id.getText();

		// Find the TBQLContext object that corresponds to id
		int indexTBQLEventContext = tbqlQueryContext.findIndexTBQLEventContext(id);
		if (indexTBQLEventContext == -1) {
			errHandler.handleIDError(id + " is not defined.");
		}

		TBQLEventContext tbqlEventContext = tbqlQueryContext.eventPatternContexts.get(indexTBQLEventContext);

		// Store eventID in returnEventIDs
		tbqlQueryContext.returnEventIDs.add(tbqlEventContext.eventID);

		// Obtain the attribute
		String attributeName = "";
		if (ctx.attr != null) {
			attributeName = ctx.attr.getText();
		} else {
			String type = tbqlEventContext.idTypeMap.get(id);
			attributeName = TBQLQueryContext.getDefaultAttribute(type);
		}

		// Store return string in returnResults;
		tbqlQueryContext.returnResults.add(id + "." + attributeName);

		// Append SQL "select"
		if (tbqlEventContext.synthesizeSQLQuery) {
			updateSQLSelect(tbqlEventContext, id + "." + attributeName + ", ");
		}
		else {
			updateCypherReturnStr(tbqlEventContext, id + "." + attributeName + ", ");
		}

		return "";
	}

	// limit
	@Override
	public String visitLimit(TBQLParser.LimitContext ctx) {
		return ctx.val.getText();
	}
}
