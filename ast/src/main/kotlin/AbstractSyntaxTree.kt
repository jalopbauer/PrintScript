

sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
data class PrintlnAst<T : AbstractSyntaxTree<String>>(val value: T) : AbstractSyntaxTree<String> {
    override fun value(): String =
        value.value()
}

data class DeclarationAst(val variableNameNode: VariableNameNode) : AbstractSyntaxTree<String> {
    override fun value(): String =
        variableNameNode.value()
}

data class VariableNameNode(val variableName: String) : AbstractSyntaxTree<String> {
    override fun value(): String =
        variableName
}
