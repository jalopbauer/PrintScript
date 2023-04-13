// Extensible
sealed interface AbstractSyntaxTree

// Defined Structures AST
class PrintlnAst(private val value: PrintlnAstParameter) : AbstractSyntaxTree {
    fun value(): PrintlnAstParameter =
        value
}
sealed interface PrintlnAstParameter : AbstractSyntaxTree

class DeclarationAst(private val variableNameNode: VariableNameNode, private val typeNode: TypeNode) : AbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): TypeNode =
        typeNode
}

class AssignationAst<T>(private val variableNameNode: VariableNameNode, private val assignationParameter: AssignationParameterNode<T>) : AbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): AssignationParameterNode<T> =
        assignationParameter
}

class AssignationDeclarationAst<T>(private val assignation: AssignationAst<T>, private val declaration: DeclarationAst) : AbstractSyntaxTree {
    fun leftValue(): AssignationAst<T> =
        assignation

    fun rightValue(): DeclarationAst =
        declaration
}

sealed interface AssignationParameterNode<T> : AbstractSyntaxTree

// Value interfaces
sealed interface StringNode : AssignationParameterNode<String>
sealed interface NumberNode : AssignationParameterNode<Int>

// Literals
class StringLiteralNode(private val value: String) : StringNode, PrintlnAstParameter {
    fun value(): String =
        value
}
class StringConcatNode(private val value: String, private val node: StringNode) : StringNode {
    fun value(): String = value
}
class NumberLiteralNode(val number: Int) : NumberNode {
    fun value(): Int =
        number
}
class OperationNode(val rightNode: NumberNode, val operation: String, val leftNumber: NumberLiteralNode) : NumberNode {
    fun value(): Int = leftNumber.number // TODO ver como transformar operation en un operador valido
}
class NumberLiteralStringNode(private val number: NumberLiteralNode) : AbstractSyntaxTree, PrintlnAstParameter {
    fun value(): String =
        number.number.toString()
}
class VariableNameNode(private val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode<String> {
    fun value(): String =
        variableName
}

// Types
class TypeNode(private val type: Type) : AbstractSyntaxTree {
    fun value(): Type =
        type
}

enum class Type {
    STRING,
    INT
}
