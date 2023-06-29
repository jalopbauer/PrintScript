package parser.parserState.astBeingCreated

import token.IfToken
import token.Token

data class IfStatementBeingCreated(val tokens: List<Token>) : AstBeingCreated {
    constructor(nextToken: IfToken) : this(listOf(nextToken))
}
