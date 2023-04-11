
interface Error {
    fun message(): String
}

class AstStructureNotDefinedError : Error {
    override fun message(): String =
        "AssignationParameterNotValidError"
}

interface PrintScriptInterpreterState {
    fun addError(astStructureNotDefinedError: Error): PrintScriptInterpreterState
    fun initializeVariable(value: VariableNameNode, type: TypeNode): PrintScriptInterpreterState

    fun println(value: VariableNameNode): PrintScriptInterpreterState
    fun println(value: NumberLiteralStringNode): PrintScriptInterpreterState
    fun println(value: StringNode): PrintScriptInterpreterState

    fun setValueToVariable(variableNameNode: VariableNameNode, value: NumberNode): PrintScriptInterpreterState
    fun setValueToVariable(variableNameNode: VariableNameNode, value: StringNode): PrintScriptInterpreterState
    fun setValueToVariable(variableNameNode: VariableNameNode, value: VariableNameNode): PrintScriptInterpreterState
}
