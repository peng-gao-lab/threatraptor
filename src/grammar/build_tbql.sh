#!/bin/bash

antlr -listener -visitor TBQLLexer.g4 -o ../../src/main/java/query/tbql/parser -package query.tbql.parser
antlr -listener -visitor TBQLParser.g4 -o ../../src/main/java/query/tbql/parser -package query.tbql.parser
