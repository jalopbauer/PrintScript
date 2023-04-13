data class NumberNumberSub(val leftOperable: NumberLiteralNode, val rightOperable: NumberLiteralNode) : Sub {
    fun leftNumber(): Int =
        leftOperable.number
    fun rightNumber(): Int =
        rightOperable.number
}
data class NumberStringSub(val leftOperable: NumberLiteralNode, val rightOperable: StringLiteralNode) : Sub
data class StringNumberSub(val leftOperable: StringLiteralNode, val rightOperable: NumberLiteralNode) : Sub
data class StringStringSub(val leftOperable: StringLiteralNode, val rightOperable: StringLiteralNode) : Sub
data class VariableStringSub(val leftOperable: VariableNameNode, val rightOperable: StringLiteralNode) : Sub
data class StringVariableSub(val leftOperable: StringLiteralNode, val rightOperable: VariableNameNode) : Sub
data class VariableNumberSub(val leftOperable: VariableNameNode, val rightOperable: NumberLiteralNode) : Sub
data class NumberVariableSub(val leftOperable: NumberLiteralNode, val rightOperable: VariableNameNode) : Sub
