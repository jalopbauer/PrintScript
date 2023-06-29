package lexer.lexerState

import lexer.tokenLexer.TokenLexerInput
import token.Token

sealed interface LexerState {

    fun handleNextToken(nextChar: Char, nextToken: Token): LexerState

    fun tokenLexerInput(nextChar: Char): TokenLexerInput =
        TokenLexerInput(
            this.previousPossibleTokenString() + nextChar,
            initialPosition(),
            lineNumber()
        )
    fun addChar(nextChar: Char, string: String) =
        when (nextChar) {
            ' ', '\n' -> string
            else -> string + nextChar
        }
    fun initialPosition(): Int
    fun lineNumber(): Int
    fun previousPossibleTokenString(): String
}
