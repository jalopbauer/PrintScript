interface Literal : AbstractSyntaxTree
data class StringLiteral(val value: String) : PrintlnAstParameter, Literal, AssignationParameterNode<String>
sealed interface NumberLiteral<T> : AbstractSyntaxTree, AssignationParameterNode<T>, PrintlnAstParameter, OperationParameter {
    fun value(): T
}
data class IntNumberLiteral(val number: Int) : AssignationParameterNode<Int>, NumberLiteral<Int>, PrintlnAstParameter, Literal {
    override fun value(): Int =
        number
}

data class DoubleNumberLiteral(val number: Double) : AssignationParameterNode<Double>, NumberLiteral<Double>, PrintlnAstParameter, Literal {
    override fun value(): Double =
        number
}
