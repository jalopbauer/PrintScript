package lexer

import lexer.lexerState.LexerState

data class LexerInput<T : LexerState>(val nextChar: Char, val lexerState: T)
