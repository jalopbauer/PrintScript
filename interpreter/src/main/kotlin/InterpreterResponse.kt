sealed interface InterpreterResponse
interface Error : InterpreterResponse {
    fun message(): String
}

class AssignationParameterNotValidError : Error {
    override fun message(): String =
        "AssignationParameterNotValidError"
}

class AstStructureNotDefinedError : Error {
    override fun message(): String =
        "AssignationParameterNotValidError"
}

interface InterpreterState : InterpreterResponse
interface AssignationDeclarationInterpreterState : DeclarationInterpreterState, AssignationInterpreterState

interface DeclarationInterpreterState : InterpreterState {
    fun initializeVariable(value: VariableNameNode, type: TypeNode): InterpreterResponse
}
interface PrintlnInterpreterState : InterpreterState {
    fun println(value: VariableNameNode): InterpreterResponse
    fun println(value: NumberLiteralStringNode): InterpreterResponse
    fun println(value: StringNode): InterpreterResponse
}
interface AssignationInterpreterState : InterpreterState {
    fun setValueToVariable(variableNameNode: VariableNameNode, value: NumberNode): InterpreterResponse
    fun setValueToVariable(variableNameNode: VariableNameNode, value: StringNode): InterpreterResponse
    fun setValueToVariable(variableNameNode: VariableNameNode, value: VariableNameNode): InterpreterResponse
}
interface PrintScriptInterpreterState : InterpreterState, PrintlnInterpreterState, AssignationInterpreterState, DeclarationInterpreterState, AssignationDeclarationInterpreterState
