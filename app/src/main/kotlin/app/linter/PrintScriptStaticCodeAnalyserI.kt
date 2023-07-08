package app.linter

import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.PreviousTokenDefinedLexerState
import staticcodeanalyser.StaticCodeAnalyserString
import token.Token

class PrintScriptStaticCodeAnalyserI(private val linter: StaticCodeAnalyserString) : PrintScriptStaticCodeAnalyser {
    override fun format(
        nextChar: Char,
        states: PrintScriptStaticCodeAnalyserStates
    ): PrintScriptStaticCodeAnalyserStates =
        LexerInput(nextChar, states.lexerState)
            .let { input ->
                when (val stateLexerResponse = NewTokenListLexer().tokenize(input)) {
                    is IntermediateLexerStateResponse -> states.copy(lexerState = stateLexerResponse.intermediateLexerState)
                    is TokenFoundLexerStateResponse -> formatStates(stateLexerResponse.token, states.copy(lexerState = stateLexerResponse.intermediateLexerState))
                }
            }

    private fun formatStates(
        nextToken: Token,
        states: PrintScriptStaticCodeAnalyserStates
    ): PrintScriptStaticCodeAnalyserStates =
        (states.tokens + nextToken)
            .let { tokens ->
                linter.format(tokens)
                    ?.let {
                        states.copy(
                            tokens = listOf(),
                            string = states.string + it
                        )
                    }
                    ?: states.copy(tokens = tokens)
            }

    fun handleLastState(states: PrintScriptStaticCodeAnalyserStates): PrintScriptStaticCodeAnalyserStates? =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> formatStates(lexerState.previousToken, states)
            else -> null
        }
}
