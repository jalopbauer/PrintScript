package parser.parserState

import ast.IfStatement
import token.Token

interface ParserState {
    fun tokens(): List<Token>
    fun addToken(token: Token): ParserState
    fun lastAddedToken(): Token?
}

data class RegularParserState(val tokens: List<Token> = listOf()) : ParserState {
    override fun tokens(): List<Token> = tokens
    override fun addToken(token: Token): RegularParserState = RegularParserState(tokens + token)
    override fun lastAddedToken(): Token? = tokens.lastOrNull()
}

data class IfParserState(val ifStatement: IfStatement, val regularParserState: RegularParserState = RegularParserState()) : ParserState {
    override fun tokens(): List<Token> = regularParserState.tokens
    override fun addToken(token: Token): IfParserState = this.copy(regularParserState = regularParserState.addToken(token))
    override fun lastAddedToken(): Token? = regularParserState.lastAddedToken()
}
