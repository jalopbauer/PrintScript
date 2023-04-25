package state

import InterpreterResponse
import Literal
import VariableInstance
import VariableNameNode

interface PrintlnInterpreterState : InterpreterState, VariableInterpreterState {
    fun println(value: String): InterpreterResponse
    fun printList(): String
}

data class PrintlnInterpreterStateI(val printList: List<String> = listOf(), val variableInterpreterState: VariableInterpreterState) : PrintlnInterpreterState {
    override fun println(value: String): InterpreterResponse =
        this.copy(printList = printList + value)

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse =
        variableInterpreterState.initializeVariable(variableInstance)

    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterResponse =
        variableInterpreterState.setLiteralToVariable(key, value)

    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterResponse =
        variableInterpreterState.setVariableValueToVariable(key, value)

    override fun get(variableName: VariableNameNode): Literal? =
        variableInterpreterState.get(variableName)

    override fun printList(): String = printList.last()
}
