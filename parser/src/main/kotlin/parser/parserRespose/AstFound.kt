package parser.parserRespose

import ast.AbstractSyntaxTree
import token.Token

data class AstFound(val tokens: List<Token>, val abstractSyntaxTree: AbstractSyntaxTree) : ParserResponse
