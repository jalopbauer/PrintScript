package state

import InterpreterResponse
import Literal
import VariableInstance
import VariableNameNode

interface PrintScriptInterpreterState : InterpreterState, PrintlnInterpreterState, VariableInterpreterState

data class PrintScriptInterpreterStateI(private val printlnInterpreterState: PrintlnInterpreterState) : PrintScriptInterpreterState {
    override fun println(value: String): InterpreterResponse =
        printlnInterpreterState.println(value).let {
            when (it) {
                is PrintlnInterpreterState -> this.copy(printlnInterpreterState = it)
                else -> it
            }
        }

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse =
        printlnInterpreterState.initializeVariable(variableInstance).let {
            when (it) {
                is PrintlnInterpreterState -> this.copy(printlnInterpreterState = it)
                else -> it
            }
        }

    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterResponse =
        printlnInterpreterState.setLiteralToVariable(key, value).let {
            when (it) {
                is PrintlnInterpreterState -> this.copy(printlnInterpreterState = it)
                else -> it
            }
        }
    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterResponse =
        printlnInterpreterState.setVariableValueToVariable(key, value).let {
            when (it) {
                is PrintlnInterpreterState -> this.copy(printlnInterpreterState = it)
                else -> it
            }
        }

    override fun get(variableName: VariableNameNode): Literal? =
        printlnInterpreterState.get(variableName)

    override fun printList(): String = printlnInterpreterState.printList()
}
