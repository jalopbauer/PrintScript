

sealed interface AbstractSyntaxTree

data class Node(val value: String, val lNode: AbstractSyntaxTree, val rNode: AbstractSyntaxTree) : AbstractSyntaxTree

data class NodeValue(val value: String) : AbstractSyntaxTree
