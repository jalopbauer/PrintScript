interface LiteralNode : AbstractSyntaxTree
data class StringLiteralNode(val value: String) : PrintlnAstParameter, LiteralNode, AssignationParameterNode<String>
sealed interface NumberLiteral<T> : AbstractSyntaxTree, AssignationParameterNode<T>, PrintlnAstParameter {
    fun value(): T
}
data class IntNumberLiteralLiteral(val number: Int) : AssignationParameterNode<Int>, NumberLiteral<Int>, PrintlnAstParameter, LiteralNode {
    override fun value(): Int =
        number
}

data class DoubleNumberLiteralLiteral(val number: Double) : AssignationParameterNode<Double>, NumberLiteral<Double>, PrintlnAstParameter, LiteralNode {
    override fun value(): Double =
        number
}
