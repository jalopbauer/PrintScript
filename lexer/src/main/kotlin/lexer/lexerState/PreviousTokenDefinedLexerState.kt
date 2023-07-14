package lexer.lexerState

import token.ErrorToken
import token.Token

data class PreviousTokenDefinedLexerState(
    val initialPosition: Int,
    val nextPosition: Int,
    val lineNumber: Int,
    val previousPossibleTokenString: String,
    val previousToken: Token
) : IntermediateLexerState {
    override fun isEmpty(): Boolean =
        previousPossibleTokenString == ""

    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> {
                TokenFoundLexerState(
                    nextPosition,
                    nextPosition,
                    lineNumber,
                    previousToken
                )
            }
            else -> this.copy(
                previousPossibleTokenString = addChar(nextChar, previousPossibleTokenString),
                nextPosition = nextPosition + 1,
                previousToken = nextToken
            )
        }
    override fun initialPosition(): Int = initialPosition

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = previousPossibleTokenString
}
