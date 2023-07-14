package lexer.lexerState

import token.ErrorToken
import token.Token

data class NoPreviousTokenDefinedLexerState(
    val initialPosition: Int = 0,
    val nextPosition: Int = 0,
    val lineNumber: Int = 0,
    val previousPossibleTokenString: String = ""
) : IntermediateLexerState {
    override fun isEmpty(): Boolean =
        previousPossibleTokenString == ""

    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> this.copy(
                nextPosition = nextPosition + 1,
                previousPossibleTokenString = addChar(nextChar, previousPossibleTokenString),
                initialPosition = initialPosition
            )
            else -> {
                PreviousTokenDefinedLexerState(
                    initialPosition,
                    nextPosition + 1,
                    lineNumber,
                    addChar(nextChar, previousPossibleTokenString),
                    nextToken
                )
            }
        }

    override fun initialPosition(): Int = initialPosition

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = previousPossibleTokenString
}
