package interpreter.state

import ast.Literal
import ast.VariableInstance
import ast.VariableNameNode
import interpreter.InterpreterResponse

interface PrintlnInterpreterState : InterpreterState, VariableInterpreterState {
    fun println(value: String): PrintlnInterpreterState
    fun print(): Pair<List<String>, PrintlnInterpreterState>
}

data class PrintlnInterpreterStateI(val printList: List<String> = listOf(), val variableInterpreterState: VariableInterpreterState = VariableInterpreterStateI()) :
    PrintlnInterpreterState {
    override fun println(value: String): PrintlnInterpreterState =
        this.copy(printList = printList + value)

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse =
        variableInterpreterState.initializeVariable(variableInstance).let {
            when (it) {
                is VariableInterpreterState -> this.copy(variableInterpreterState = it)
                else -> it
            }
        }

    override fun initializeConst(variableInstance: VariableInstance): InterpreterResponse =
        variableInterpreterState.initializeConst(variableInstance).let {
            when (it) {
                is VariableInterpreterState -> this.copy(variableInterpreterState = it)
                else -> it
            }
        }
    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterResponse =
        variableInterpreterState.setLiteralToVariable(key, value).let {
            when (it) {
                is VariableInterpreterState -> this.copy(variableInterpreterState = it)
                else -> it
            }
        }

    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterResponse =
        variableInterpreterState.setVariableValueToVariable(key, value).let {
            when (it) {
                is VariableInterpreterState -> this.copy(variableInterpreterState = it)
                else -> it
            }
        }

    override fun get(variableName: VariableNameNode): Literal? =
        variableInterpreterState.get(variableName)

    override fun isVariableDefined(key: VariableNameNode): Boolean =
        variableInterpreterState.isVariableDefined(key)

    override fun print(): Pair<List<String>, PrintlnInterpreterState> =
        Pair(printList, this.copy(printList = listOf()))
}
