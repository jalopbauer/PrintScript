

sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
data class PrintlnAst(val value: PrintlnAstParameter) : AbstractSyntaxTree<String> {
    override fun value(): String =
        value.value()
}

interface PrintlnAstParameter : AbstractSyntaxTree<String>

data class DeclarationAst(val variableNameNode: VariableNameNode, val variableType: VariableTypeNode) : AbstractSyntaxTree<String> {
    override fun value(): String =
        ":"
}
class VariableTypeNode(private val variableType: String) : AbstractSyntaxTree<String> {
    override fun value(): String =
        variableType
}

data class VariableNameNode(val variableName: String) : AbstractSyntaxTree<String>, PrintlnAstParameter {
    override fun value(): String =
        variableName
}

data class NumberLiteralNode(val number: Int) : AbstractSyntaxTree<String>, PrintlnAstParameter {
    override fun value(): String =
        number.toString()
}
