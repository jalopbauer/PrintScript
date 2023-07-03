package parser.parserState

import token.Token

data class ParserState(val tokens: List<Token> = listOf()) {
    fun tokens(): List<Token> = tokens
    fun addToken(token: Token): ParserState = ParserState(tokens + token)
}
