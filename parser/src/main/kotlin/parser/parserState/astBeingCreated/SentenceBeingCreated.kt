package parser.parserState.astBeingCreated

import token.Token

class SentenceBeingCreated(val tokens: List<Token>) : AstBeingCreated {
    constructor(nextToken: Token) : this(listOf(nextToken))
}
