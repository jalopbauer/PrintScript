sealed interface InterpreterResponse
interface Error : InterpreterResponse {
    fun message(): String
}

class AssignationParameterNotValidError : Error {
    override fun message(): String =
        "AssignationParameterNotValidError"
}

interface InterpreterState : InterpreterResponse {
    fun addVariableValueToPrintln(value: String): InterpreterResponse
    fun addValueToPrintln(value: String): InterpreterResponse
    fun initializeVariable(value: String, value1: String): InterpreterResponse
    fun setValueToVariable(variableNameNode: VariableNameNode, value: NumberNode): InterpreterResponse
    fun setValueToVariable(variableNameNode: VariableNameNode, value: StringNode): InterpreterResponse
    fun setValueToVariable(variableNameNode: VariableNameNode, value: VariableNameNode): InterpreterResponse
}
