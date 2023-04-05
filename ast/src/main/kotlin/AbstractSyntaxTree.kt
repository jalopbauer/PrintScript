

sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
data class PrintlnAst<T : AbstractSyntaxTree<String>>(val value: T) : AbstractSyntaxTree<String> {
    override fun value(): String =
        value.value()
}
