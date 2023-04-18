interface Literal : AbstractSyntaxTree {
    fun type(): Type
}
data class StringLiteral(val value: String) : PrintlnAstParameter, Literal, AssignationParameterNode, ConcatenationParameter {
    override fun type(): StringType = StringType
}

sealed interface NumberLiteral<T> : AbstractSyntaxTree, AssignationParameterNode, PrintlnAstParameter, FinalOperationParameter, ConcatenationParameter {
    fun value(): T
}
data class IntNumberLiteral(val number: Int) : AssignationParameterNode, NumberLiteral<Int>, PrintlnAstParameter, Literal {
    override fun value(): Int =
        number

    override fun type(): IntType = IntType
}

data class DoubleNumberLiteral(val number: Double) : AssignationParameterNode, NumberLiteral<Double>, PrintlnAstParameter, Literal {
    override fun value(): Double =
        number

    override fun type(): DoubleType = DoubleType
}
