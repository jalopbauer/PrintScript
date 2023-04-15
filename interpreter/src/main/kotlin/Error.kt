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
