
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

interface PrintScriptInterpreterState {
    fun addError(error: Error): PrintScriptInterpreterState
    fun initializeVariable(key: VariableNameNode, value: TypeNode): PrintScriptInterpreterState

    fun println(value: VariableNameNode): PrintScriptInterpreterState
    fun println(value: NumberLiteralStringNode): PrintScriptInterpreterState
    fun println(value: StringNode): PrintScriptInterpreterState

    fun setValueToVariable(variableNameNode: VariableNameNode, value: NumberNode): PrintScriptInterpreterState
    fun setValueToVariable(variableNameNode: VariableNameNode, value: StringNode): PrintScriptInterpreterState
    fun setValueToVariable(variableNameNode: VariableNameNode, value: VariableNameNode): PrintScriptInterpreterState
}

data class StatefullPrintScriptInterpreterState(val errors: List<Error>, val variableTypeMap: Map<String, String>) : PrintScriptInterpreterState {
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
        variableNameNode: VariableNameNode,
        value: NumberNode
    ): PrintScriptInterpreterState {
        TODO("Not yet implemented")
    }

    override fun setValueToVariable(
        variableNameNode: VariableNameNode,
        value: StringNode
    ): PrintScriptInterpreterState {
        TODO("Not yet implemented")
    }

    override fun setValueToVariable(
        variableNameNode: VariableNameNode,
        value: VariableNameNode
    ): PrintScriptInterpreterState {
        TODO("Not yet implemented")
    }

    private fun isVariableDefined(key: VariableNameNode) = variableTypeMap.containsKey(key.value())
}
