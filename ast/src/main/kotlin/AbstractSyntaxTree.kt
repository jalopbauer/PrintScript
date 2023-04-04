sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
data class ValuedNode<T>(val value: T, val lNode: AbstractSyntaxTree<*>, val rNode: AbstractSyntaxTree<*>) : AbstractSyntaxTree<T> {
    override fun value(): T =
        value
}

data class ValuedSingleNode<T>(val value: T, val node: AbstractSyntaxTree<*>) : AbstractSyntaxTree<T> {
    override fun value(): T =
        value
}

data class NodeValue<T>(val value: T) : AbstractSyntaxTree<T> {
    override fun value(): T =
        value
}
