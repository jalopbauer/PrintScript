interface Literal : AbstractSyntaxTree {
    fun type(): Type
}
data class StringLiteral(val value: String) : PrintlnAstParameter, Literal, AssignationParameterNode, ConcatenationParameter {
    override fun type(): StringType = StringType
}

sealed interface NumberLiteral : AbstractSyntaxTree, AssignationParameterNode, PrintlnAstParameter, FinalOperationParameter, ConcatenationParameter {
    fun value(): Number
}
data class IntNumberLiteral(val number: Int) : AssignationParameterNode, NumberLiteral, PrintlnAstParameter, Literal {
    override fun value(): Int =
        number

    override fun type(): IntType = IntType
}

data class DoubleNumberLiteral(val number: Double) : AssignationParameterNode, NumberLiteral, PrintlnAstParameter, Literal {
    override fun value(): Double =
        number

    override fun type(): DoubleType = DoubleType
}
