package parser.parserState

import ast.IfStatement
import token.Token

data class IfParserState(val ifStatement: IfStatement, val regularParserState: RegularParserState = RegularParserState()) : ParserState {
    override fun tokens(): List<Token> = regularParserState.tokens
    override fun addToken(token: Token): IfParserState = this.copy(regularParserState = regularParserState.addToken(token))
    override fun lastAddedToken(): Token? = regularParserState.lastAddedToken()
}
