// Extensible
interface AbstractSyntaxTree

// Literals
data class StringConcatNode(private val value: String) : AbstractSyntaxTree {
    fun value(): String = value
}

data class OperationNode<T, U>(val rightNode: T, val operation: String, val leftNumber: IntNumberLiteralLiteral) : AbstractSyntaxTree {
    fun value(): Int = leftNumber.number // TODO ver como transformar operation en un operador valido
}
data class VariableNameNode(val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode<String> {
    fun value(): String =
        variableName
}

// Types
data class TypeNode(private val type: Type) : AbstractSyntaxTree {
    fun value(): Type =
        type
}

enum class Type {
    STRING,
    INT
}
