interface VariableInterpreterState {
    fun addError(error: Error): VariableInterpreterState

    fun initializeVariable(variableInstance: VariableInstance): VariableInterpreterState

    fun setLiteralToVariable(key: VariableNameNode, value: Literal): VariableInterpreterState

    fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): VariableInterpreterState
}
data class StringIntDoubleVariableInterpreterState(
    val errors: List<Error> = listOf(),
    val variableTypeMap: Map<String, Type> = mapOf(),
    val variableLiteralMap: Map<String, Literal> = mapOf()
) : VariableInterpreterState {
    override fun addError(error: Error): StringIntDoubleVariableInterpreterState =
        this.copy(errors = errors + error)

    override fun initializeVariable(variableInstance: VariableInstance): StringIntDoubleVariableInterpreterState =
        if (isVariableDefined(variableInstance.variableNameNode)) {
            this.addError(VariableAlreadyExistsError())
        } else {
            this.copy(variableTypeMap = variableTypeMap + (variableInstance.variableNameNode.value() to variableInstance.type))
        }

    override fun setLiteralToVariable(key: VariableNameNode, value: Literal): StringIntDoubleVariableInterpreterState =
        getVariableType(key)
            ?.let {
                when (value) {
                    is IntNumberLiteral,
                    is DoubleNumberLiteral,
                    is StringLiteral -> add(key, value)
                    else -> this.addError(NotAcceptableAssignationValueError())
                }
            }
            ?: this.addError(VariableIsNotDefined())
    override fun setVariableValueToVariable(key: VariableNameNode, value: VariableNameNode): StringIntDoubleVariableInterpreterState =
        getKeyValueIfVariablesAreSameType(key, value)
            ?.let { setLiteralToVariable(value, it) }
            ?: this.addError(VariableIsNotDefined())

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
