package lexer.lexerState

import token.ErrorToken
import token.Token

data class TokenFoundLexerState(
    private val initialPosition: Int,
    val nextPosition: Int,
    private val lineNumber: Int,
    val token: Token
) :
    LexerState {
    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> NoPreviousTokenDefinedLexerState(
                when (nextChar) {
                    ' ' -> initialPosition + 1
                    '\n' -> 0
                    else -> initialPosition
                },
                when (nextChar) {
                    '\n' -> 0
                    else -> nextPosition + 1
                },
                when (nextChar) {
                    '\n' -> lineNumber + 1
                    else -> lineNumber
                },
                addChar(nextChar, "")
            )
            else -> PreviousTokenDefinedLexerState(
                initialPosition,
                nextPosition + 1,
                lineNumber,
                addChar(nextChar, ""),
                nextToken
            )
        }

    override fun initialPosition(): Int = initialPosition

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = ""
}
