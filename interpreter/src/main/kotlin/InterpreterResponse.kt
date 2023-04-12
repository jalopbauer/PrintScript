
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

class NotAcceptableAssignationValueError : Error {
    override fun message(): String =
        "NotAcceptableAssignationValueError"
}

interface PrintScriptInterpreterState {
    fun addError(error: Error): PrintScriptInterpreterState
    fun initializeVariable(key: VariableNameNode, value: TypeNode): PrintScriptInterpreterState

    fun println(value: PrintlnAstParameter): PrintScriptInterpreterState

    fun setValueToVariable(key: VariableNameNode, value: AssignationParameterNode<*>): PrintScriptInterpreterState
}

data class StatefullPrintScriptInterpreterState(
    val errors: List<Error> = listOf(),
    val printList: List<String> = listOf(),
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
    override fun println(value: PrintlnAstParameter): PrintScriptInterpreterState =
        when (value) {
            is NumberLiteralStringNode, is StringLiteralNode -> this.copy(printList = printList + value.value())
            is VariableNameNode ->
                variableStringMap[value.value()]
                    ?.let { this.copy(printList = printList + value.value()) }
                    ?: variableIntegerMap[value.value()]
                        ?.let { this.copy(printList = printList + value.value()) }
                    ?: this.addError(VariableIsNotDefined())
        }
    override fun setValueToVariable(
        key: VariableNameNode,
        value: AssignationParameterNode<*>
    ): PrintScriptInterpreterState =
        if (isVariableDefined(key)) {
            when (value) {
                is NumberNode -> this.copy(variableIntegerMap = variableIntegerMap + (key.value() to value.value()))
                is StringNode -> this.copy(variableStringMap = variableStringMap + (key.value() to value.value()))
                is VariableNameNode -> this.setValueToVariable(key, value)
                else -> this.addError(NotAcceptableAssignationValueError())
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
