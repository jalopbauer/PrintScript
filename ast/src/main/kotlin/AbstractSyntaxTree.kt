// Extensible
sealed interface AbstractSyntaxTree
sealed interface LeftRightValuedNode<T, U> : AbstractSyntaxTree {
    fun leftValue(): T
    fun rightValue(): U
}
sealed interface ValuedNode<T> : AbstractSyntaxTree {
    fun value(): T
}

// Defined Structures AST
class PrintlnAst(private val value: PrintlnAstParameter) : ValuedNode<PrintlnAstParameter> {
    override fun value(): PrintlnAstParameter =
        value
}
sealed interface PrintlnAstParameter : ValuedNode<String>

class DeclarationAst(private val variableNameNode: VariableNameNode, private val typeNode: TypeNode) : LeftRightValuedNode<VariableNameNode, TypeNode> {
    override fun leftValue(): VariableNameNode =
        variableNameNode

    override fun rightValue(): TypeNode =
        typeNode
}

class AssignationAst<T>(private val variableNameNode: VariableNameNode, private val assignationParameter: AssignationParameterNode<T>) : LeftRightValuedNode<VariableNameNode, AssignationParameterNode<T>> {
    override fun leftValue(): VariableNameNode =
        variableNameNode

    override fun rightValue(): AssignationParameterNode<T> =
        assignationParameter
}

class AssignationDeclarationAst<T>(private val assignation: AssignationAst<T>, private val declaration: DeclarationAst) : LeftRightValuedNode<AssignationAst<T>, DeclarationAst> {
    override fun leftValue(): AssignationAst<T> =
        assignation

    override fun rightValue(): DeclarationAst =
        declaration
}

sealed interface AssignationParameterNode<T> : ValuedNode<T>

// Value interfaces
sealed interface StringNode : AssignationParameterNode<String>
sealed interface NumberNode : AssignationParameterNode<Int>

// Literals
class StringLiteralNode(private val value: String) : StringNode, PrintlnAstParameter {
    override fun value(): String =
        value
}
class StringConcatNode(private val value: String, private val node: StringNode) : StringNode {
    override fun value(): String = value
}
class NumberLiteralNode(val number: Int) : NumberNode {
    override fun value(): Int =
        number
}
class OperationNode(val rightNode: NumberNode, val operation: String, val leftNumber: NumberLiteralNode) : NumberNode {
    override fun value(): Int = leftNumber.number // TODO ver como transformar operation en un operador valido
}
class NumberLiteralStringNode(private val number: NumberLiteralNode) : ValuedNode<String>, PrintlnAstParameter {
    override fun value(): String =
        number.number.toString()
}
class VariableNameNode(private val variableName: String) : ValuedNode<String>, PrintlnAstParameter, AssignationParameterNode<String> {
    override fun value(): String =
        variableName
}

// Types
class TypeNode(private val type: Type) : ValuedNode<Type> {
    override fun value(): Type =
        type
}

enum class Type {
    STRING,
    INT
}
