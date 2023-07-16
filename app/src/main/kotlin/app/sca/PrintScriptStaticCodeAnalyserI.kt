package app.sca

import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.PreviousTokenDefinedLexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer
import lexer.tokenLexer.SecondVersionPrintScriptLexer
import staticcodeanalyser.ErrorResponse
import staticcodeanalyser.PsStaticCodeAnalyser
import token.Token

class PrintScriptStaticCodeAnalyserI(private val tokenListLexer: NewTokenListLexer, private val linter: PsStaticCodeAnalyser) : PrintScriptStaticCodeAnalyser {

    constructor(version: String, linter: PsStaticCodeAnalyser) :
        this(
            if (version == "1.1") {
                NewTokenListLexer(SecondVersionPrintScriptLexer())
            } else {
                NewTokenListLexer(FirstVersionPrintScriptLexer())
            },
            linter
        )
    override fun format(
        nextChar: Char,
        states: PrintScriptStaticCodeAnalyserStates
    ): PrintScriptStaticCodeAnalyserStates =
        LexerInput(nextChar, states.lexerState)
            .let { input ->
                when (val stateLexerResponse = tokenListLexer.tokenize(input)) {
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
                val response = linter.format(tokens)
                when (response) {
                    is ErrorResponse -> states.copy(string = states.string + response.message)
                    else -> states
                }.copy(tokens = response.tokens())
            }

    override fun handleLastState(states: PrintScriptStaticCodeAnalyserStates): PrintScriptStaticCodeAnalyserStates? =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> formatStates(lexerState.previousToken, states)
            else -> null
        }
}
