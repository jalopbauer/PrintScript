package app.linter

import lexer.lexerState.LexerState
import token.Token

data class PrintScriptStaticCodeAnalyserStates(
    val lexerState: LexerState,
    val tokens: List<Token>,
    val string: String
)
