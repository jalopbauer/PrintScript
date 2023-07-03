package app.interpreter

import interpreter.state.PrintScriptInterpreterState
import lexer.lexerState.LexerState
import parser.parserState.ParserState

data class PrintScriptInterpretStates(
    val lexerState: LexerState,
    val parserState: ParserState,
    val printScriptInterpreterState: PrintScriptInterpreterState
)
