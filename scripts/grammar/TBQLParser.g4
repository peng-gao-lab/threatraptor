parser grammar TBQLParser;

@head{
	package query.tbql;
}	

options{
	tokenVocab = TBQLLexer;
}

stat
:
	(glbcons+=globalConstraint)* multieventQuery
;

globalConstraint
:
	key=ID (not=NOTSTR)? op=(EQUAL|UNEQUAL|GT|LT|GEQ|LEQ|IN) (val=(INT|FLOAT|STRING) | coll=collection)						# AttributeGlobalConstraint // agentid = 1; agentid in (1, 2, 3)
	| tcons=timeWindowConstraint																							# TimeWindowGlobalConstraint
	| key=ID op=EQUAL func=ID LEFTBRACKET (args+=(ID|INT|FLOAT|STRING) (COMMA args+=(ID|INT|FLOAT|STRING))*)? RIGHTBRACKET	# FunctionCallGlobalConstraint
;

timeWindowConstraint
:
	AT t=STRING										# AtTimeConstraint
	| FROM t1=STRING TO t2=STRING					# FromToTimeConstraint
	| BEFORE t=STRING								# BeforeTimeConstraint
	| AFTER t=STRING								# AfterTimeConstraint
	| LAST amount=(INT | FLOAT) unit=TIMEUNIT		# LastTimeConstraint
;

multieventQuery
:
	(defs+=eventPattern) (defs+=eventPattern)* withClause? returnClause limit?
;

eventPattern
:
	sub=entity op=operation obj=entity evt=event (time=timeWindow)?		# NormalEventPattern
	| sub=entity op=pathOperation obj=entity evt=event					# VarPathPattern
;

entity
:
	type=ENTITYTYPE id=ID (LEFTSQUAREBRACKET attr=attributeExpression RIGHTSQUAREBRACKET)?
;

operation
:
	left=operation op=(LOGICALAND | LOGICALOR) right=operation	# OpOperation
	| atom=OPTYPE												# AtomOperation
	| LEFTBRACKET operation	RIGHTBRACKET						# ParenOperation
;

pathOperation
:
	arrow=(RIGHTTILDEARROW | RIGHTARROW) (LEFTBRACKET left=INT? TILDE? right=INT? RIGHTBRACKET)? (LEFTSQUAREBRACKET operation RIGHTSQUAREBRACKET)?
;

event
:
	AS id=ID (LEFTSQUAREBRACKET attr=attributeExpression RIGHTSQUAREBRACKET)?
;

attributeExpression
:
	left=attributeExpression op=(LOGICALAND|LOGICALOR) right=attributeExpression		# OpAttributeExpr
	| atom=attributeConstraint															# AtomAttributeExpr
	| LEFTBRACKET attributeExpression RIGHTBRACKET										# ParenAttributeExpr
;

attributeConstraint
:
	(id=ID op=(EQUAL|UNEQUAL|GT|LT|GEQ|LEQ))? (not=NOT)? val=(INT|FLOAT|STRING)			# AttributeConstraintFull			// name = "XXX", agentid = 1234567, '%chrome%'
	| id=ID (not=NOTSTR)? IN col=collection												# AttributeConstraintCollection 	// id in (1, 2, 3)
;

collection
:
	LEFTBRACKET collelems+=collectionElement(COMMA collelems+=collectionElement)* RIGHTBRACKET
;

collectionElement
:
	(val=INT|val=FLOAT|val=STRING|func=ID LEFTBRACKET (args+=(ID|INT|FLOAT|STRING) (COMMA args+=(ID|INT|FLOAT|STRING))*)? RIGHTBRACKET)
;

timeWindow
:
	LEFTBRACKET tcons=timeWindowConstraint RIGHTBRACKET
;

withClause
:
	WITH (eventRelation)(COMMA eventRelation)*
;

eventRelation
:
	id1=ID DOT attr1=ID op=(EQUAL|UNEQUAL|GT|LT|GEQ|LEQ) id2=ID DOT attr2=ID 																		# AttributeRelation	// p1.exe_name = p2.exe_name, evt1.agentid = evt2.agentid
	| evt1=ID type=(BEFORE|AFTER|WITHIN) (LEFTSQUAREBRACKET (num1=(INT|FLOAT)) DASH (num2=(INT|FLOAT)) unit=TIMEUNIT RIGHTSQUAREBRACKET)? evt2=ID	# TemporalRelation	// evt1 before evt2
;

returnClause
:
	RETURN count=COUNT? distinct=DISTINCT? (res+=result)(COMMA res+=result)*
;

result
: 
	id=ID (DOT attr=ID)? // p.pid, evt1.agentid
;

limit
:
	LIMIT val=INT
;
