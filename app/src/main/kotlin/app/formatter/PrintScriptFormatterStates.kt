package app.formatter

import lexer.lexerState.LexerState
import token.Token

data class PrintScriptFormatterStates(
    val lexerState: LexerState,
    val tokens: List<Token>,
    val string: String
)
