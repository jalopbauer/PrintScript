package state

import InterpreterResponse
import Literal
import VariableInstance
import VariableNameNode

interface PrintScriptInterpreterState : InterpreterState, PrintlnInterpreterState, VariableInterpreterState

class PrintScriptInterpreterStateI(private val printlnInterpreterState: PrintlnInterpreterState) : PrintScriptInterpreterState {
    override fun println(value: String): InterpreterResponse =
        printlnInterpreterState.println(value)

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse =
        printlnInterpreterState.initializeVariable(variableInstance)

    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterResponse =
        printlnInterpreterState.setLiteralToVariable(key, value)
    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterResponse =
        printlnInterpreterState.setVariableValueToVariable(key, value)

    override fun get(variableName: VariableNameNode): Literal? =
        printlnInterpreterState.get(variableName)
}
