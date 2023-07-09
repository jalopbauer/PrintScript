package parser.parserState

import token.Token

interface ParserState {
    fun tokens(): List<Token>
    fun addToken(token: Token): ParserState
}

data class RegularParserState(val tokens: List<Token> = listOf()) : ParserState {
    override fun tokens(): List<Token> = tokens
    override fun addToken(token: Token): RegularParserState = RegularParserState(tokens + token)
}
