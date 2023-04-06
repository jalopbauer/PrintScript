

sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
data class PrintlnAst(val value: PrintlnAstParameter) : AbstractSyntaxTree<String> {
    override fun value(): String =
        value.value()
}

interface PrintlnAstParameter : AbstractSyntaxTree<String>

data class DeclarationAst(val variableNameNode: VariableNameNode) : AbstractSyntaxTree<String> {
    override fun value(): String =
        variableNameNode.value()
}

data class VariableNameNode(val variableName: String) : AbstractSyntaxTree<String>, PrintlnAstParameter {
    override fun value(): String =
        variableName
}

data class NumberLiteralNode(val number: Int) : AbstractSyntaxTree<String>, PrintlnAstParameter {
    override fun value(): String =
        number.toString()
}
