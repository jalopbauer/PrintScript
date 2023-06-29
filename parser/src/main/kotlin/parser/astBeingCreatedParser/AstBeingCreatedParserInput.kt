package parser.astBeingCreatedParser

import ast.AbstractSyntaxTree
import token.Token

data class AstBeingCreatedParserInput(val nextToken: Token, val abstractSyntaxTree: AbstractSyntaxTree?)
