package lexer

import lexer.lexerState.LexerState

data class LexerInput(val nextChar: Char, val lexerState: LexerState)
