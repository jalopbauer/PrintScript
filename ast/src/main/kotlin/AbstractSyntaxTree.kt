

sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
class PrintlnAst(val value: PrintlnAstParameter) : AbstractSyntaxTree<PrintlnAstParameter> {
    override fun value(): PrintlnAstParameter =
        value
}

sealed interface PrintlnAstParameter : AbstractSyntaxTree<String>

class VariableNameNode(val variableName: String) : AbstractSyntaxTree<String>, PrintlnAstParameter {
    override fun value(): String =
        variableName
}

class NumberLiteralNode(val number: Int) : AbstractSyntaxTree<Int> {
    override fun value(): Int =
        number
}

class NumberLiteralStringNode(val number: NumberLiteralNode) : AbstractSyntaxTree<String>, PrintlnAstParameter {
    override fun value(): String =
        number.number.toString()
}

sealed interface StringNode : AbstractSyntaxTree<String>
class StringLiteralNode(val value: String) : StringNode, PrintlnAstParameter {
    override fun value(): String =
        value
}
