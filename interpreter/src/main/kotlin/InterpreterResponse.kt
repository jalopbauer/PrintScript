
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
class TypeNotSupportedInPrintError : Error {
    override fun message(): String =
        "TypeNotSupportedInPrintError"
} class CannotPrintValueError : Error {
    override fun message(): String =
        "CannotPrintValueError"
}

class OperationNotSupportedError : Error {
    override fun message(): String =
        "OperationNotSupportedError"
}
class VariableIsNotDefinedError : Error {
    override fun message(): String =
        "VariableIsNotDefinedError"
}
class PrintlnAstParameterNotAccepted : Error {
    override fun message(): String =
        "PrintlnAstParameterNotAccepted"
}

interface PrintScriptInterpreterState {
    fun addError(error: Error): PrintScriptInterpreterState
    fun initializeVariable(key: VariableNameNode, value: Type): PrintScriptInterpreterState

    fun println(value: PrintlnAstParameter): PrintScriptInterpreterState

    fun setValueToVariable(key: VariableNameNode, value: AssignationParameterNode<*>): PrintScriptInterpreterState
    fun get(operation: VariableNameNode): LiteralNode?
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
            is StringLiteralNode -> this.copy(printList = printList + value.value)
            is VariableNameNode ->
                variableStringMap[value.value()]
                    ?.let { this.copy(printList = printList + value.value()) }
                    ?: variableIntegerMap[value.value()]
                        ?.let { this.copy(printList = printList + value.value()) }
                    ?: this.addError(VariableIsNotDefined())
            is LiteralNode -> this.addError(TypeNotSupportedInPrintError())
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
                is StringLiteralNode -> this.copy(variableStringMap = variableStringMap + (key.value() to value.value))
                is VariableNameNode -> this.setValueToVariable(key, value)
                else -> this.addError(NotAcceptableAssignationValueError())
            }
        } else {
            this.addError(VariableIsNotDefined())
        }

    override fun get(operation: VariableNameNode): LiteralNode? {
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
