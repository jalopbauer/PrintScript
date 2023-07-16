package app.formatter

import formatter.Formatter
import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.PreviousTokenDefinedLexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer
import lexer.tokenLexer.SecondVersionPrintScriptLexer
import token.Token

class PrintScriptFormatterI(private val tokenListLexer: NewTokenListLexer, private val formatter: Formatter) : PrintScriptFormatter {

    constructor(version: String, formatter: Formatter) :
        this(
            if (version == "1.1") {
                NewTokenListLexer(SecondVersionPrintScriptLexer())
            } else {
                NewTokenListLexer(FirstVersionPrintScriptLexer())
            },
            formatter
        )
    override fun format(
        nextChar: Char,
        states: PrintScriptFormatterStates
    ): PrintScriptFormatterStates =
        LexerInput(nextChar, states.lexerState)
            .let { input ->
                when (val stateLexerResponse = tokenListLexer.tokenize(input)) {
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
                formatter.format(tokens)
                    ?.let {
                        states.copy(
                            tokens = listOf(),
                            string = states.string + it
                        )
                    }
                    ?: states.copy(tokens = tokens)
            }

    override fun handleLastState(states: PrintScriptFormatterStates): PrintScriptFormatterStates? =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> formatStates(lexerState.previousToken, states)
            else -> null
        }
}
