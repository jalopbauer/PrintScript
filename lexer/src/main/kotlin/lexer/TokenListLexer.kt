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
            position(),
            lineNumber()
        )
    fun nextPosition(nextChar: Char): Int = if (nextChar == '\n') position() + 1 else position()
    fun addChar(nextChar: Char, string: String) =
        when (nextChar) {
            ' ' -> string
            else -> string + nextChar
        }
    fun position(): Int
    fun lineNumber(): Int
    fun previousPossibleTokenString(): String

    fun tokens(): List<Token>
}
data class PreviousTokenDefinedLexerState(
    val position: Int,
    val lineNumber: Int,
    val previousPossibleTokenString: String,
    val previousToken: Token,
    val tokens: List<Token>
) : LexerState {
    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> {
                RerunLexerState(position, lineNumber, tokens + previousToken)
            }
            else -> this.copy(
                position = nextPosition(nextChar),
                previousPossibleTokenString = addChar(nextChar, previousPossibleTokenString),
                previousToken = nextToken
            )
        }
    override fun position(): Int = position

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = previousPossibleTokenString
    override fun tokens(): List<Token> = tokens
}

data class NoPreviousTokenDefinedLexerState(
    val position: Int = 0,
    val lineNumber: Int = 0,
    val previousPossibleTokenString: String = "",
    val tokens: List<Token> = listOf()
) : LexerState {

    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> this.copy(previousPossibleTokenString = addChar(nextChar, previousPossibleTokenString), position = nextPosition(nextChar))
            else -> {
                PreviousTokenDefinedLexerState(
                    nextPosition(nextChar),
                    lineNumber,
                    addChar(nextChar, previousPossibleTokenString),
                    nextToken,
                    tokens
                )
            }
        }

    override fun position(): Int = position

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = previousPossibleTokenString
    override fun tokens(): List<Token> = tokens
}

data class RerunLexerState(private val position: Int, private val lineNumber: Int, private val tokens: List<Token>) :
    LexerState {
    override fun handleNextToken(nextChar: Char, nextToken: Token): LexerState =
        when (nextToken) {
            is ErrorToken -> NoPreviousTokenDefinedLexerState(nextPosition(nextChar), lineNumber, addChar(nextChar, ""), tokens)
            else -> PreviousTokenDefinedLexerState(nextPosition(nextChar), lineNumber, addChar(nextChar, ""), nextToken, tokens)
        }

    override fun position(): Int = position

    override fun lineNumber(): Int = lineNumber

    override fun previousPossibleTokenString(): String = ""

    override fun tokens(): List<Token> = tokens
}
