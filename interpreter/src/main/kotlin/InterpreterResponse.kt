
interface Error {
    fun message(): String
}

class AstStructureNotDefinedError : Error {
    override fun message(): String =
        "AssignationParameterNotValidError"
}

interface PrintScriptInterpreterState {
    fun addError(error: Error): PrintScriptInterpreterState
    fun initializeVariable(value: VariableNameNode, type: TypeNode): PrintScriptInterpreterState

    fun println(value: VariableNameNode): PrintScriptInterpreterState
    fun println(value: NumberLiteralStringNode): PrintScriptInterpreterState
    fun println(value: StringNode): PrintScriptInterpreterState

    fun setValueToVariable(variableNameNode: VariableNameNode, value: NumberNode): PrintScriptInterpreterState
    fun setValueToVariable(variableNameNode: VariableNameNode, value: StringNode): PrintScriptInterpreterState
    fun setValueToVariable(variableNameNode: VariableNameNode, value: VariableNameNode): PrintScriptInterpreterState
}

data class StatefullPrintScriptInterpreterState(val errors: List<Error>) : PrintScriptInterpreterState {
    override fun addError(error: Error): PrintScriptInterpreterState =
        this.copy(errors = errors + error)

    override fun initializeVariable(value: VariableNameNode, type: TypeNode): PrintScriptInterpreterState {
        TODO("Not yet implemented")
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
}
