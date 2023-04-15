interface PrintScriptInterpreterState {
    fun addError(error: Error): PrintScriptInterpreterState
    fun initializeVariable(key: VariableNameNode, value: Type): PrintScriptInterpreterState

    fun println(value: PrintlnAstParameter): PrintScriptInterpreterState

    fun setValueToVariable(key: VariableNameNode, value: AssignationParameterNode<*>): PrintScriptInterpreterState
    fun get(operation: VariableNameNode): Literal?
}

data class StatefullPrintScriptInterpreterState(
    val errors: List<Error> = listOf(),
    val printList: List<String> = listOf(),
    val variableTypeMap: Map<String, Type> = mapOf(),
    val variableIntegerMap: Map<String, Int> = mapOf(),
    val variableStringMap: Map<String, String> = mapOf(),
    val variableDoubleMap: Map<String, Double> = mapOf()
) : PrintScriptInterpreterState {
    override fun addError(error: Error): PrintScriptInterpreterState =
        this.copy(errors = errors + error)

    override fun initializeVariable(key: VariableNameNode, value: Type): PrintScriptInterpreterState =
        if (isVariableDefined(key)) {
            this.addError(VariableAlreadyExistsError())
        } else {
            this.copy(variableTypeMap = variableTypeMap + (key.value() to value))
        }
    override fun println(value: PrintlnAstParameter): PrintScriptInterpreterState =
        when (value) {
            is NumberLiteral<*> -> this.copy(printList = printList + value.value().toString())
            is StringLiteral -> this.copy(printList = printList + value.value)
            is VariableNameNode ->
                variableStringMap[value.value()]
                    ?.let { this.copy(printList = printList + value.value()) }
                    ?: variableIntegerMap[value.value()]
                        ?.let { this.copy(printList = printList + value.value()) }
                    ?: this.addError(VariableIsNotDefined())
            is Literal -> this.addError(TypeNotSupportedInPrintError())
            else -> this.addError(CannotPrintValueError())
        }
    override fun setValueToVariable(
        key: VariableNameNode,
        value: AssignationParameterNode<*>
    ): PrintScriptInterpreterState =
        if (isVariableDefined(key)) {
            when (value) {
                is IntNumberLiteral -> this.copy(variableIntegerMap = variableIntegerMap + (key.value() to value.value()))
                is DoubleNumberLiteral -> this.copy(variableDoubleMap = variableDoubleMap + (key.value() to value.value()))
                is StringLiteral -> this.copy(variableStringMap = variableStringMap + (key.value() to value.value))
                is VariableNameNode -> this.setValueToVariable(key, value)
                else -> this.addError(NotAcceptableAssignationValueError())
            }
        } else {
            this.addError(VariableIsNotDefined())
        }

    override fun get(operation: VariableNameNode): Literal? {
        TODO("Not yet implemented")
    }

    private fun setValueToVariable(
        key: VariableNameNode,
        value: VariableNameNode
    ): PrintScriptInterpreterState =
        variableTypeMap[key.value()]
            ?.let { keyType ->
                variableTypeMap[value.value()]
                    ?.let { valueType ->
                        when {
                            keyType != valueType -> this.addError(VariablesDontShareType())
                            keyType is IntType ->
                                variableIntegerMap[value.value()]
                                    ?.let { this.copy(variableIntegerMap = variableIntegerMap + (key.value() to it)) }
                                    ?: this.addError(VariableIsNotDefined())
                            keyType is StringType ->
                                variableStringMap[value.value()]
                                    ?.let { this.copy(variableStringMap = variableStringMap + (key.value() to it)) }
                                    ?: this.addError(VariableIsNotDefined())
                            else -> this.addError(NotValidType())
                        }
                    }
            } ?: this.addError(VariableIsNotDefined())

    private fun isVariableDefined(key: VariableNameNode) = variableTypeMap.containsKey(key.value())
}

data class VariableInterpreterState(
    val errors: List<Error> = listOf(),
    val variableTypeMap: Map<String, Type> = mapOf(),
    val variableLiteralMap: Map<String, Literal> = mapOf()
) {
    fun addError(error: Error): VariableInterpreterState =
        this.copy(errors = errors + error)

    fun initializeVariable(variableInstance: VariableInstance): VariableInterpreterState =
        if (isVariableDefined(variableInstance.variableNameNode)) {
            this.addError(VariableAlreadyExistsError())
        } else {
            this.copy(variableTypeMap = variableTypeMap + (variableInstance.variableNameNode.value() to variableInstance.type))
        }

    fun setValueToVariable(
        key: VariableNameNode,
        value: AssignationParameterNode<*>
    ): VariableInterpreterState =
        getVariableType(key)
            ?.let {
                when (value) {
                    is IntNumberLiteral,
                    is DoubleNumberLiteral,
                    is StringLiteral -> add(key, value as Literal)
                    else -> this.addError(NotAcceptableAssignationValueError())
                }
            }
            ?: this.addError(VariableIsNotDefined())

    private fun add(
        key: VariableNameNode,
        value: Literal
    ) = this.copy(variableLiteralMap = variableLiteralMap + (key.value() to value))
    fun get(variableName: VariableNameNode): Literal? =
        variableLiteralMap[variableName.value()]
    fun getVariableType(variableName: VariableNameNode): Type? =
        variableTypeMap[variableName.value()]
    private fun setValueToVariable(
        key: VariableNameNode,
        value: VariableNameNode
    ): VariableInterpreterState =
        variableTypeMap[key.value()]
            ?.let { keyType ->
                variableTypeMap[value.value()]
                    ?.let { valueType ->
                        when {
                            keyType != valueType -> this.addError(VariablesDontShareType())
                            keyType is IntType ->
                                variableLiteralMap[value.value()]
                                    ?.let { this.copy(variableLiteralMap = variableLiteralMap + (key.value() to it)) }
                                    ?: this.addError(VariableIsNotDefined())
                            keyType is StringType ->
                                variableLiteralMap[value.value()]
                                    ?.let { this.copy(variableLiteralMap = variableLiteralMap + (key.value() to it)) }
                                    ?: this.addError(VariableIsNotDefined())
                            else -> this.addError(NotValidType())
                        }
                    }
            } ?: this.addError(VariableIsNotDefined())

    private fun isVariableDefined(key: VariableNameNode) = variableTypeMap.containsKey(key.value())
}
