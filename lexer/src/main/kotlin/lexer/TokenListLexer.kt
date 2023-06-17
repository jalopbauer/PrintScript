package lexer

import lexer.printScriptLexer.FirstVersionPrintScriptLexer
import token.ErrorToken
import token.Token

class TokenListLexer : Lexer<LexerInput, LexerState> {
    override fun tokenize(input: LexerInput): LexerState = input.lexerState.tokenLexerInput(input.nextChar)
        .let { tokenLexerInput ->
            FirstVersionPrintScriptLexer().tokenize(tokenLexerInput)
                .let {
                    input.lexerState.handleNextToken(input.nextChar, it)
                }
        }.let {
            when (it) {
                is RerunLexerState -> this.tokenize(input.copy(lexerState = it))
                else -> it
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
            ' ' -> string
            else -> string + nextChar
        }
    fun initialPosition(): Int
    fun lineNumber(): Int
    fun previousPossibleTokenString(): String

    fun tokens(): List<Token>
}
data class PreviousTokenDefinedLexerState(
    val initialPosition: Int,
    val nextPosition: Int,
    val lineNumber: Int,
    val previousPossibleTokenString: String,
    val previousToken: Token,
    val tokens: List<Token>
) : LexerState {
    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> {
                RerunLexerState(
                    nextPosition,
                    nextPosition,
                    lineNumber,
                    tokens + previousToken
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
    override fun tokens(): List<Token> = tokens
}

data class NoPreviousTokenDefinedLexerState(
    val initialPosition: Int = 0,
    val nextPosition: Int = 0,
    val lineNumber: Int = 0,
    val previousPossibleTokenString: String = "",
    val tokens: List<Token> = listOf()
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
                    nextToken,
                    tokens
                )
            }
        }

    override fun initialPosition(): Int = initialPosition

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = previousPossibleTokenString
    override fun tokens(): List<Token> = tokens
}

data class RerunLexerState(
    private val initialPosition: Int,
    val nextPosition: Int,
    private val lineNumber: Int,
    private val tokens: List<Token>
) :
    LexerState {
    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> NoPreviousTokenDefinedLexerState(
                if (nextChar == ' ') initialPosition + 1 else initialPosition,
                nextPosition + 1,
                lineNumber,
                addChar(nextChar, ""),
                tokens
            )
            else -> PreviousTokenDefinedLexerState(
                initialPosition,
                nextPosition + 1,
                lineNumber,
                addChar(nextChar, ""),
                nextToken,
                tokens
            )
        }

    override fun initialPosition(): Int = initialPosition

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = ""

    override fun tokens(): List<Token> = tokens
}
