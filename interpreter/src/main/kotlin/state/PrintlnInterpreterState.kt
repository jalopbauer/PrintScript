package state

import InterpreterResponse
import VariableNameNode

interface PrintlnInterpreterState : InterpreterState, VariableInterpreterState {
    fun println(value: String): InterpreterResponse
    fun println(value: VariableNameNode): InterpreterResponse
}
