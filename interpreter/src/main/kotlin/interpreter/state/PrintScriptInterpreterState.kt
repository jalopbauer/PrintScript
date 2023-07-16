package interpreter.state

import ast.Literal
import ast.VariableInstance
import ast.VariableNameNode
import interpreter.InterpreterResponse

interface PrintScriptInterpreterState : InterpreterState, PrintlnInterpreterState, VariableInterpreterState, ReadInputInterpreterState {
    override fun print(): Pair<List<String>, PrintScriptInterpreterState>
    override fun readInput(): Pair<Literal?, PrintScriptInterpreterState>
    override fun setReadInput(literal: Literal): PrintScriptInterpreterState
    override fun println(value: String): PrintScriptInterpreterState
}

data class PrintScriptInterpreterStateI(
    private val printlnInterpreterState: PrintlnInterpreterState = PrintlnInterpreterStateI(),
    private val inputtedValue: Literal? = null
) :
    PrintScriptInterpreterState {
    override fun println(value: String): PrintScriptInterpreterState =
        printlnInterpreterState.println(value).let {
            this.copy(printlnInterpreterState = it)
        }

    override fun print(): Pair<List<String>, PrintScriptInterpreterStateI> =
        printlnInterpreterState.print().let { (string, state) ->
            Pair(string, this.copy(printlnInterpreterState = state))
        }

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse =
        printlnInterpreterState.initializeVariable(variableInstance).let {
            when (it) {
                is PrintlnInterpreterState -> this.copy(printlnInterpreterState = it)
                else -> it
            }
        }

    override fun initializeConst(variableInstance: VariableInstance): InterpreterResponse =
        printlnInterpreterState.initializeConst(variableInstance).let {
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

    override fun isVariableDefined(key: VariableNameNode): Boolean =
        printlnInterpreterState.isVariableDefined(key)

    override fun readInput(): Pair<Literal?, PrintScriptInterpreterState> =
        Pair(inputtedValue, this.copy(inputtedValue = null))

    override fun setReadInput(literal: Literal): PrintScriptInterpreterState =
        this.copy(inputtedValue = literal)
}
