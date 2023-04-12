
interface Error {
    fun message(): String
}
class VariableAlreadyExistsError : Error {
    override fun message(): String =
        "VariableAlreadyExistsError"
}

class AstStructureNotDefinedError : Error {
    override fun message(): String =
        "AssignationParameterNotValidError"
}
class VariableIsNotDefined : Error {
    override fun message(): String =
        "VariableIsNotDefined"
}

class NotValidType : Error {
    override fun message(): String =
        "NotValidType"
}

class VariablesDontShareType : Error {
    override fun message(): String =
        "VariablesDontShareType"
}

interface InterpreterState
interface PrintScriptInterpreterState {
    fun addError(error: Error): PrintScriptInterpreterState
    fun initializeVariable(key: VariableNameNode, value: TypeNode): PrintScriptInterpreterState

    fun println(value: VariableNameNode): PrintScriptInterpreterState
    fun println(value: NumberLiteralStringNode): PrintScriptInterpreterState
    fun println(value: StringNode): PrintScriptInterpreterState

    fun setValueToVariable(key: VariableNameNode, value: AssignationParameterNode<*>): PrintScriptInterpreterState
}

data class StatefullPrintScriptInterpreterState(
    val errors: List<Error>,
    val variableTypeMap: Map<String, Type> = mapOf(),
    val variableIntegerMap: Map<String, Int?> = mapOf(),
    val variableStringMap: Map<String, String?> = mapOf()
) : PrintScriptInterpreterState {
    override fun addError(error: Error): PrintScriptInterpreterState =
        this.copy(errors = errors + error)

    override fun initializeVariable(key: VariableNameNode, value: TypeNode): PrintScriptInterpreterState =
        if (isVariableDefined(key)) {
            this.addError(VariableAlreadyExistsError())
        } else {
            this.copy(variableTypeMap = variableTypeMap + (key.value() to value.value()))
        }
    override fun println(value: VariableNameNode): PrintScriptInterpreterState {
        TODO("Not yet implemented")
    }

    override fun println(value: NumberLiteralStringNode): PrintScriptInterpreterState {
        TODO("Not yet implemented")
    }

    override fun println(value: StringNode): PrintScriptInterpreterState {
        TODO("Not yet implemented")
    }

    override fun setValueToVariable(
        key: VariableNameNode,
        value: AssignationParameterNode<*>
    ): PrintScriptInterpreterState =
        if (isVariableDefined(key)) {
            when (value) {
                is NumberLiteralNode -> this.copy(variableIntegerMap = variableIntegerMap + (key.value() to value.value()))
                is StringLiteralNode -> this.copy(variableStringMap = variableStringMap + (key.value() to value.value()))
                is VariableNameNode -> this.setValueToVariable(key, value)
            }
        } else {
            this.addError(VariableIsNotDefined())
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
                            keyType == Type.INT -> this.copy(variableIntegerMap = variableIntegerMap + (key.value() to variableIntegerMap[value.value()]))
                            keyType == Type.STRING -> this.copy(variableStringMap = variableStringMap + (key.value() to variableStringMap[value.value()]))
                            else -> this.addError(NotValidType())
                        }
                    }
            } ?: this.addError(VariableIsNotDefined())

    private fun isVariableDefined(key: VariableNameNode) = variableTypeMap.containsKey(key.value())
}
