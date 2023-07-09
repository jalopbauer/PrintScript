package parser.parserState

import token.Token

interface ParserState {
    fun tokens(): List<Token>
    fun addToken(token: Token): ParserState
    fun lastAddedToken(): Token?
}
