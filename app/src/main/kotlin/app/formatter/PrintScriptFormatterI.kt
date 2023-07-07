package app.formatter

import formatter.Formatter
import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.PreviousTokenDefinedLexerState
import token.Token

class PrintScriptFormatterI(private val formatter: Formatter) : PrintScriptFormatter {
    override fun format(
        nextChar: Char,
        states: PrintScriptFormatterStates
    ): PrintScriptFormatterStates =
        LexerInput(nextChar, states.lexerState)
            .let { input ->
                when (val stateLexerResponse = NewTokenListLexer().tokenize(input)) {
                    is IntermediateLexerStateResponse -> states.copy(lexerState = stateLexerResponse.intermediateLexerState)
                    is TokenFoundLexerStateResponse -> formatStates(stateLexerResponse.token, states.copy(lexerState = stateLexerResponse.intermediateLexerState))
                }
            }

    private fun formatStates(
        nextToken: Token,
        states: PrintScriptFormatterStates
    ): PrintScriptFormatterStates =
        (states.tokens + nextToken)
            .let { tokens ->
                formatter.format(states.tokens)
                    ?.let {
                        states.copy(
                            tokens = listOf(),
                            string = states.string + it
                        )
                    }
                    ?: states.copy(tokens = tokens)
            }

    fun handleLastState(states: PrintScriptFormatterStates): PrintScriptFormatterStates? =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> formatStates(lexerState.previousToken, states)
            else -> null
        }
}
