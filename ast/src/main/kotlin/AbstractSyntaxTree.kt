

sealed interface AbstractSyntaxTree<T> {
    fun value(): T
}
interface ValuedNode<T> : AbstractSyntaxTree<T>

data class ValuedSingleNode<T>(val value: T, val node: AbstractSyntaxTree<*>) : AbstractSyntaxTree<T> {
    override fun value(): T =
        value
}

data class NodeValue<T>(val value: T) : AbstractSyntaxTree<T> {
    override fun value(): T =
        value
}

data class SumStringNode(val lNode: AbstractSyntaxTree<String>, val rNode: AbstractSyntaxTree<String>) : AbstractSyntaxTree<String> {
    override fun value(): String =
        lNode.value() + rNode.value()
}

data class SumNumberNode(val lNode: AbstractSyntaxTree<Int>, val rNode: AbstractSyntaxTree<Int>) : AbstractSyntaxTree<Int> {
    override fun value(): Int =
        lNode.value() + rNode.value()
}
