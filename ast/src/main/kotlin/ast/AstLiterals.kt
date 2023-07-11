package ast

interface Literal : AbstractSyntaxTree, AssignationParameterNode, PrintlnAstParameter {
    fun type(): Type
}
data class StringLiteral(val value: String) : PrintlnAstParameter, Literal, ConcatenationParameter {
    override fun type(): StringType = StringType
}

sealed interface NumberLiteral :
    AbstractSyntaxTree,
    PrintlnAstParameter,
    FinalOperationParameter,
    ConcatenationParameter {
    fun value(): Number
}
data class IntNumberLiteral(val number: Int) : AssignationParameterNode, NumberLiteral, PrintlnAstParameter, Literal {
    override fun value(): Int =
        number

    override fun type(): NumberType = NumberType
}

data class DoubleNumberLiteral(val number: Double) :
    AssignationParameterNode,
    NumberLiteral,
    PrintlnAstParameter,
    Literal {
    override fun value(): Double =
        number

    override fun type(): NumberType = NumberType
}

sealed interface BooleanLiteral : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode, Literal

object FalseLiteral : BooleanLiteral {
    override fun type(): Type = BooleanType
}

object TrueLiteral : BooleanLiteral {
    override fun type(): Type = BooleanType
}
