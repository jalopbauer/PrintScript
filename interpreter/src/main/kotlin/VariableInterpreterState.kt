interface VariableInterpreterState : InterpreterState {

    fun initializeVariable(variableInstance: VariableInstance): InterpreterStateResponse

    fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterStateResponse

    fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterStateResponse
}
data class VariableInterpreterStateI(
    val errors: List<InterpreterError> = listOf(),
    val variableTypeMap: Map<String, Type> = mapOf(),
    val variableLiteralMap: Map<String, Literal> = mapOf()
) : VariableInterpreterState {

    override fun initializeVariable(variableInstance: VariableInstance): InterpreterStateResponse =
        if (isVariableDefined(variableInstance.variableNameNode)) {
            VariableAlreadyExistsError()
        } else {
            this.copy(variableTypeMap = variableTypeMap + (variableInstance.variableNameNode.value() to variableInstance.type))
        }

    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): InterpreterStateResponse =
        getVariableType(key)
            ?.let {
                if (it == value.type()) { add(key, value) } else { VariableAndLiteralTypeDoNotMatch() }
            }
            ?: VariableIsNotDefined()
    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): InterpreterStateResponse =
        getKeyValueIfVariablesAreSameType(key, value)
            ?.let { setLiteralToVariable(value, it) }
            ?: VariableIsNotDefined()

    private fun add(
        key: VariableNameNode,
        value: Literal
    ) = this.copy(variableLiteralMap = variableLiteralMap + (key.value() to value))
    private fun get(variableName: VariableNameNode): Literal? =
        variableLiteralMap[variableName.value()]
    private fun getVariableType(variableName: VariableNameNode): Type? =
        variableTypeMap[variableName.value()]
    private fun getKeyValueIfVariablesAreSameType(key: VariableNameNode, value: VariableNameNode): Literal? =
        getVariableType(key)
            ?.let { keyType ->
                getVariableType(value)
                    ?.let { valueType ->
                        if (keyType == valueType) {
                            get(key)
                        } else {
                            null
                        }
                    }
            }
    private fun isVariableDefined(key: VariableNameNode) = variableTypeMap.containsKey(key.value())
}
