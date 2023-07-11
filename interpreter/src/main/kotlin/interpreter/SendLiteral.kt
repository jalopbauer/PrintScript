package interpreter

import interpreter.state.PrintScriptInterpreterState

data class SendLiteral(val state: PrintScriptInterpreterState) : InterpreterResponse
