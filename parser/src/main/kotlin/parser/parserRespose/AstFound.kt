package parser.parserRespose

import ast.AbstractSyntaxTree
import parser.parserState.ParserState

data class AstFound(val parserState: ParserState, val abstractSyntaxTree: AbstractSyntaxTree) : ParserResponse
