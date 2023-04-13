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
interface LiteralNode : AbstractSyntaxTree, Operation

// Literals
data class StringLiteralNode(val value: String) : StringNode, PrintlnAstParameter, Operation, LiteralNode
data class StringConcatNode(private val value: String, private val node: StringNode) : StringNode {
    fun value(): String = value
}
interface NumberNode : AbstractSyntaxTree, AssignationParameterNode<Int>
data class NumberLiteralNode(val number: Int) : AssignationParameterNode<Int>, NumberNode, Operation, PrintlnAstParameter, LiteralNode {
    fun value(): Int =
        number
}
data class OperationNode<T>(val rightNode: T, val operation: String, val leftNumber: NumberLiteralNode) : AbstractSyntaxTree, NumberNode {
    fun value(): Int = leftNumber.number // TODO ver como transformar operation en un operador valido
}
data class NumberLiteralStringNode(private val number: NumberLiteralNode) : AbstractSyntaxTree, PrintlnAstParameter {
    fun value(): String =
        number.number.toString()
}
data class VariableNameNode(val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode<String>, Operation {
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
interface Operable : AbstractSyntaxTree
interface Operation : Operable
interface Sum : Operation
data class NumberNumberSum(val leftOperable: NumberLiteralNode, val rightOperable: NumberLiteralNode) : Sum {
    fun leftNumber(): Int =
        leftOperable.number
    fun rightNumber(): Int =
        rightOperable.number
}
data class NumberStringSum(val leftOperable: NumberLiteralNode, val rightOperable: StringLiteralNode) : Sum
data class StringNumberSum(val leftOperable: StringLiteralNode, val rightOperable: NumberLiteralNode) : Sum
data class StringStringSum(val leftOperable: StringLiteralNode, val rightOperable: StringLiteralNode) : Sum
data class VariableStringSum(val leftOperable: VariableNameNode, val rightOperable: StringLiteralNode) : Sum
data class StringVariableSum(val leftOperable: StringLiteralNode, val rightOperable: VariableNameNode) : Sum
data class VariableNumberSum(val leftOperable: VariableNameNode, val rightOperable: NumberLiteralNode) : Sum
data class NumberVariableSum(val leftOperable: NumberLiteralNode, val rightOperable: VariableNameNode) : Sum
data class Sub<T : Operable, U : Operable> (val leftOperable: T, val rightOperable: T) : Operation
data class Mult<T : Operable, U : Operable> (val leftOperable: T, val rightOperable: T) : Operation
data class Div<T : Operable, U : Operable> (val leftOperable: T, val rightOperable: T) : Operation
