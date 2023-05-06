package state

import InterpreterError
import InterpreterResponse
import Literal
import Type
import VariableAlreadyExistsError
import VariableAndLiteralTypeDoNotMatch
import VariableInstance
import VariableIsNotDefined
import VariableNameNode
import VariablesDontShareType

interface VariableInterpreterState : InterpreterState {

    fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse

    fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterResponse

    fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterResponse

    fun get(variableName: VariableNameNode): Literal?

    fun isVariableDefined(key: VariableNameNode): Boolean
}
data class VariableInterpreterStateI(
    val errors: List<InterpreterError> = listOf(),
    val variableTypeMap: Map<String, Type> = mapOf(),
    val variableLiteralMap: Map<String, Literal> = mapOf()
) : VariableInterpreterState {

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterResponse =
        if (isVariableDefined(variableInstance.variableNameNode)) {
            VariableAlreadyExistsError()
        } else {
            this.copy(variableTypeMap = variableTypeMap + (variableInstance.variableNameNode.value() to variableInstance.type))
        }

    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterResponse =
        getVariableType(key)
            ?.let {
                if (it == value.type()) { add(key, value) } else {
                    VariableAndLiteralTypeDoNotMatch()
                }
            }
            ?: VariableIsNotDefined()

    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterResponse =
        getKeyValueIfVariablesAreSameType(key, value)
            ?.let { setLiteralToVariable(value, it) }
            ?: VariablesDontShareType()

    private fun add(
        key: VariableNameNode,
        value: Literal
    ) = this.copy(variableLiteralMap = variableLiteralMap + (key.value() to value))
    override fun get(variableName: VariableNameNode): Literal? =
        variableLiteralMap[variableName.value()]
    private fun getVariableType(variableName: VariableNameNode): Type? =
        variableTypeMap[variableName.value()]
    private fun getKeyValueIfVariablesAreSameType(key: VariableNameNode, value: VariableNameNode): Literal? =
        getVariableType(key)
            ?.let { keyType ->
                getVariableType(value)
                    ?.let { valueType ->
                        if (keyType == valueType) {
                            get(value)
                        } else {
                            null
                        }
                    }
            }
    override fun isVariableDefined(key: VariableNameNode) = variableTypeMap.containsKey(key.value())
}
