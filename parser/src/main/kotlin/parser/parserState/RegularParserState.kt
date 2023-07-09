package parser.parserState

import token.Token

data class RegularParserState(val tokens: List<Token> = listOf()) : ParserState {
    override fun tokens(): List<Token> = tokens
    override fun addToken(token: Token): RegularParserState = RegularParserState(tokens + token)
    override fun firstToken(): Token? = tokens.firstOrNull()
}
