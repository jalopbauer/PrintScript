
sealed interface AbstractSyntaxTree

sealed interface LeftRightValuedNode<T, U> : AbstractSyntaxTree {
    fun leftValue(): T
    fun rightValue(): U
}
sealed interface ValuedNode<T> : AbstractSyntaxTree {
    fun value(): T
}
class PrintlnAst(private val value: PrintlnAstParameter) : ValuedNode<PrintlnAstParameter> {
    override fun value(): PrintlnAstParameter =
        value
}

sealed interface PrintlnAstParameter : ValuedNode<String>

class VariableNameNode(private val variableName: String) : ValuedNode<String>, PrintlnAstParameter {
    override fun value(): String =
        variableName
}

class NumberLiteralNode(val number: Int) : ValuedNode<Int> {
    override fun value(): Int =
        number
}

class NumberLiteralStringNode(private val number: NumberLiteralNode) : ValuedNode<String>, PrintlnAstParameter {
    override fun value(): String =
        number.number.toString()
}

sealed interface StringNode : ValuedNode<String>
class StringLiteralNode(private val value: String) : StringNode, PrintlnAstParameter {
    override fun value(): String =
        value
}

class DeclarationAst(private val variableNameNode: VariableNameNode, private val typeNode: TypeNode) : LeftRightValuedNode<VariableNameNode, TypeNode> {
    override fun leftValue(): VariableNameNode =
        variableNameNode

    override fun rightValue(): TypeNode =
        typeNode
}

class TypeNode(private val typeName: String) : ValuedNode<String> {
    override fun value(): String =
        typeName
}
