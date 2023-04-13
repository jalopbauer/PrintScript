data class NumberNumberMult(val leftOperable: NumberLiteralNode, val rightOperable: NumberLiteralNode) : Mult {
    fun leftNumber(): Int =
        leftOperable.number
    fun rightNumber(): Int =
        rightOperable.number
}
data class NumberStringMult(val leftOperable: NumberLiteralNode, val rightOperable: StringLiteralNode) : Mult
data class StringNumberMult(val leftOperable: StringLiteralNode, val rightOperable: NumberLiteralNode) : Mult
data class StringStringMult(val leftOperable: StringLiteralNode, val rightOperable: StringLiteralNode) : Mult
data class VariableStringMult(val leftOperable: VariableNameNode, val rightOperable: StringLiteralNode) : Mult
data class StringVariableMult(val leftOperable: StringLiteralNode, val rightOperable: VariableNameNode) : Mult
data class VariableNumberMult(val leftOperable: VariableNameNode, val rightOperable: NumberLiteralNode) : Mult
data class NumberVariableMult(val leftOperable: NumberLiteralNode, val rightOperable: VariableNameNode) : Mult
