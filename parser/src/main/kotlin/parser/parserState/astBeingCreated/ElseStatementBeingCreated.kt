package parser.parserState.astBeingCreated

import ast.IfStatement
import token.ElseToken
import token.Token

data class ElseStatementBeingCreated(val tokens: List<Token>, val ifStatement: IfStatement) : AstBeingCreated {
    constructor(nextToken: ElseToken, ifStatement: IfStatement) : this(listOf(nextToken), ifStatement)
}
