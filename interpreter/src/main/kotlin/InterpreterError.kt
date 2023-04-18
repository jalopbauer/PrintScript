interface InterpreterError : InterpreterResponse {
    fun message(): String
}
class VariableAlreadyExistsError : InterpreterError {
    override fun message(): String =
        "VariableAlreadyExistsError"
}

class AstStructureNotDefinedError : InterpreterError {
    override fun message(): String =
        "AssignationParameterNotValidError"
}
class VariableIsNotDefined : InterpreterError {
    override fun message(): String =
        "VariableIsNotDefined"
}
class VariableAndLiteralTypeDoNotMatch : InterpreterError {
    override fun message(): String =
        "VariableAndLiteralTypeDoNotMatch"
}
class NotValidType : InterpreterError {
    override fun message(): String =
        "NotValidType"
}

class VariablesDontShareType : InterpreterError {
    override fun message(): String =
        "VariablesDontShareType"
}

class NotAcceptableAssignationValueError : InterpreterError {
    override fun message(): String =
        "NotAcceptableAssignationValueError"
}
class TypeNotSupportedInPrintError : InterpreterError {
    override fun message(): String =
        "TypeNotSupportedInPrintError"
} class CannotPrintValueError : InterpreterError {
    override fun message(): String =
        "CannotPrintValueError"
}

class OperationNotSupportedError : InterpreterError {
    override fun message(): String =
        "OperationNotSupportedError"
}
class VariableIsNotDefinedError : InterpreterError {
    override fun message(): String =
        "VariableIsNotDefinedError"
}
class PrintlnAstParameterNotAccepted : InterpreterError {
    override fun message(): String =
        "PrintlnAstParameterNotAccepted"
}
