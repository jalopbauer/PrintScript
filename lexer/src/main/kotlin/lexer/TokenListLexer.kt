package lexer

import lexer.printScriptLexer.FirstVersionPrintScriptLexer
import token.ErrorToken
import token.Token

class TokenListLexer : Lexer<LexerInput, LexerState> {
    override fun tokenize(input: LexerInput): LexerState =
        input.lexerState.tokenLexerInput(input.nextChar)
            .let { tokenLexerInput ->
                FirstVersionPrintScriptLexer().tokenize(tokenLexerInput)
                    .let {
                        input.lexerState.handleNextToken(input.nextChar, it)
                    }
            }
}

data class LexerInput(val nextChar: Char, val lexerState: LexerState)

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
data class PreviousTokenDefinedLexerState(
    val initialPosition: Int,
    val nextPosition: Int,
    val lineNumber: Int,
    val previousPossibleTokenString: String,
    val previousToken: Token
) : LexerState {
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

data class NoPreviousTokenDefinedLexerState(
    val initialPosition: Int = 0,
    val nextPosition: Int = 0,
    val lineNumber: Int = 0,
    val previousPossibleTokenString: String = ""
) : LexerState {

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
