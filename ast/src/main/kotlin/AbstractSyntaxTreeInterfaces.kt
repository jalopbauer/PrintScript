// Extensible
interface AbstractSyntaxTree

// Defined Structures AST
data class PrintlnAst(private val value: PrintlnAstParameter) : AbstractSyntaxTree {
    fun value(): PrintlnAstParameter =
        value
}
interface PrintlnAstParameter : AbstractSyntaxTree

data class DeclarationAst(private val variableNameNode: VariableNameNode, private val typeNode: TypeNode) : AbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): TypeNode =
        typeNode
}

data class AssignationAst<T>(private val variableNameNode: VariableNameNode, private val assignationParameter: AssignationParameterNode<T>) : AbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): AssignationParameterNode<T> =
        assignationParameter
}

data class AssignationDeclarationAst<T>(private val assignation: AssignationAst<T>, private val declaration: DeclarationAst) : AbstractSyntaxTree {
    fun leftValue(): AssignationAst<T> =
        assignation

    fun rightValue(): DeclarationAst =
        declaration
}

interface AssignationParameterNode<T> : AbstractSyntaxTree

// Value interfaces
interface StringNode : AssignationParameterNode<String>
interface LiteralNode : AbstractSyntaxTree

// Literals
data class StringLiteralNode(val value: String) : StringNode, PrintlnAstParameter, LiteralNode
data class StringConcatNode(private val value: String, private val node: StringNode) : StringNode {
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
