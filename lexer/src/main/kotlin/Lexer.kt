package lexer

import token.Token
import token.TokenIdentifier

interface Lexer<T :LexerInput> {

    fun buildTokenList(lexerInput: T): List<Token>

}

class LineLexer(private val tokenIdentifiers: List<TokenIdentifier>): Lexer<Line> {
    override fun buildTokenList(lexerInput: Line): List<Token> {
        return splitIntoTokens(lexerInput).reversed()
    }

    private fun splitIntoTokens(line: Line, positionInLine: Int = 0 ): List<Token> {
        return if (line.value.isEmpty()) listOf()
        else {
            val tokenIdentifier = tokenIdentifiers.first { tknIdentifier -> tknIdentifier.identify(line.value, positionInLine) }
            val finalPositionExclusive = tokenIdentifier.finalPositionExclusive(line.value, positionInLine)
            val tokenValue = line.value.substring(positionInLine, finalPositionExclusive)
            val token = Token(tokenIdentifier.tokenName, tokenValue, line.number, finalPositionExclusive)
            listOf(token) + token
        }
    }}
