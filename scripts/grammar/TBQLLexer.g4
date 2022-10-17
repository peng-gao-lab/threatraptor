lexer grammar TBQLLexer;

@head{
	package query.tbql;
}

// Type of system entities
ENTITYTYPE
:
	FILE
	| PROC
	| IP
;

fragment
FILE
:
	'file'
;

fragment
PROC
:
	'proc'
;

fragment
IP
:
	'ip'
;

// Type of operations between two entities
OPTYPE:
	'open'
	| 'close'
	| 'read'
	| 'write'
	| 'execute'
	| 'start'
	| 'end'
	| 'rename'
	| 'connect'
	| 'unlink'
	| 'modify'
	| 'create'
;

// String literals
COMMA: ',';

COLON: ':';

DOT: '.';

DASH: '-';

TILDE: '~';

WITH: 'with';

LOGICALOR: '||';

LOGICALAND: '&&';

EQUAL: '=';

UNEQUAL: '!=';

NOT: '!';

NOTSTR: 'not';

GT: '>';

LT: '<';

GEQ: '>=';

LEQ: '<=';

LEFTBRACKET: '(';

RIGHTBRACKET: ')';

LEFTSQUAREBRACKET: '[';

RIGHTSQUAREBRACKET: ']';

LEFTDOUBLEBRACKET: '<<';

RIGHTDOUBLEBRACKET: '>>';

LEFTARROW: '<-';

RIGHTARROW: '->';

RIGHTTILDEARROW: '~>';

IN: 'in';

AS: 'as';

AT: 'at';

FROM: 'from';

TO: 'to';

LAST: 'last';

AND: 'and';

OR: 'or';

BEFORE: 'before';

AFTER: 'after';

WITHIN: 'within';

TIMEUNIT
:
	'second'('s')?
	| 'minute'('s')?
	| 'hour'('s')?
	| 'day'('s')?
	| 'month'('s')?
	| 'year'('s')?
;

DISTINCT: 'distinct';

COUNT: 'count';

RETURN: 'return';

LIMIT: 'top';

LIST: 'list';

SORTBY: 'sort by';

ASC: 'asc';

DESC: 'desc';

// Values
INT
:
	POSINT | NEGINT
;

FLOAT
:
	POSFLOAT | NEGFLOAT
;

fragment
POSINT
:
	DIGIT+
;

fragment
POSFLOAT
:
	DIGIT+ '.' DIGIT* // match 1. 39. 3.14159 etc...

	| '.' DIGIT+ // match .1 .14159
;

fragment
NEGINT
:
	('-')DIGIT+
;

fragment
NEGFLOAT
:
	('-')DIGIT+ '.' DIGIT* // match 1. 39. 3.14159 etc...

	| ('-')'.' DIGIT+ // match .1 .14159
;

STRING
:
	'"' DoubleStringCharacter* '"'
	| '\'' SingleStringCharacter* '\''
;

fragment
DoubleStringCharacter
:
	~["\r\n]
;

fragment
SingleStringCharacter
:
	~['\r\n]
;

fragment
DIGIT
:
	[0-9]
;

ID
:
	ID_LETTER
	(
		ID_LETTER
		| DIGIT
	)*
;

fragment
ID_LETTER
:
	[a-zA-Z_]
;

// Whitespace and comments
NEWLINE
:
	[\r\n]+ -> skip
;

WS
:
	[ \t\r\n\u000C]+ -> skip
;

COMMENT
:
	'/*' .*? '*/' -> skip
;

LINE_COMMENT
:
	'//' .*? '\r'? '\n' -> skip
;
