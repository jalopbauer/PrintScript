// Extensible
interface AbstractSyntaxTree

interface LiteralNode : AbstractSyntaxTree

// Literals
data class StringLiteralNode(val value: String) : PrintlnAstParameter, LiteralNode, AssignationParameterNode<String>
data class StringConcatNode(private val value: String) : AbstractSyntaxTree {
    fun value(): String = value
}
sealed interface NumberNode<T> : AbstractSyntaxTree, AssignationParameterNode<T>, PrintlnAstParameter {
    fun value(): T
}
data class IntNumberLiteralNode(val number: Int) : AssignationParameterNode<Int>, NumberNode<Int>, PrintlnAstParameter, LiteralNode {
    override fun value(): Int =
        number
}

data class DoubleNumberLiteralNode(val number: Double) : AssignationParameterNode<Double>, NumberNode<Double>, PrintlnAstParameter, LiteralNode {
    override fun value(): Double =
        number
}
data class OperationNode<T, U>(val rightNode: T, val operation: String, val leftNumber: IntNumberLiteralNode) : AbstractSyntaxTree {
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
