sealed interface AbstractSyntaxTree
data class ValuedNode<T>(val value: NodeValue<T>, val lNode: AbstractSyntaxTree, val rNode: AbstractSyntaxTree) : AbstractSyntaxTree
data class ValuedSingleNode<T>(val value: NodeValue<T>, val node: AbstractSyntaxTree) : AbstractSyntaxTree
data class NodeValue<T>(val value: T) : AbstractSyntaxTree
