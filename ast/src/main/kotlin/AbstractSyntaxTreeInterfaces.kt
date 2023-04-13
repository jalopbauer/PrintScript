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
interface LiteralNode : AbstractSyntaxTree, EndingOperable

// Literals
data class StringLiteralNode(val value: String) : StringNode, PrintlnAstParameter, EndingOperable, LiteralNode
data class StringConcatNode(private val value: String, private val node: StringNode) : StringNode {
    fun value(): String = value
}
interface NumberNode : AbstractSyntaxTree, AssignationParameterNode<Int>
data class NumberLiteralNode(val number: Int) : AssignationParameterNode<Int>, NumberNode, EndingOperable, PrintlnAstParameter, LiteralNode {
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
data class VariableNameNode(val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode<String>, EndingOperable {
    fun value(): String =
        variableName
}

// Types
data class TypeNode(private val type: Type) : AbstractSyntaxTree {
    fun value(): Type =
        type
}

interface Type : AbstractSyntaxTree
class StringType : Type
class NumberType : Type

interface Operable : AbstractSyntaxTree
interface EndingOperable : Operable
interface Operation<T : Operable, U : Operable> : Operable
data class Sum<T : Operable, U : Operable>(val left: T, val right: U) : Operation<T, U>
data class Sub<T : Operable, U : Operable>(val left: T, val right: U) : Operation<T, U>
data class Mult<T : Operable, U : Operable>(val left: T, val right: U) : Operation<T, U>
data class Div<T : Operable, U : Operable>(val left: T, val right: U) : Operation<T, U>
