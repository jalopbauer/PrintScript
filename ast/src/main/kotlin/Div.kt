data class NumberNumberDiv(val leftOperable: NumberLiteralNode, val rightOperable: NumberLiteralNode) : Div {
    fun leftNumber(): Int =
        leftOperable.number
    fun rightNumber(): Int =
        rightOperable.number
}
data class NumberStringDiv(val leftOperable: NumberLiteralNode, val rightOperable: StringLiteralNode) : Div
data class StringNumberDiv(val leftOperable: StringLiteralNode, val rightOperable: NumberLiteralNode) : Div
data class StringStringDiv(val leftOperable: StringLiteralNode, val rightOperable: StringLiteralNode) : Div
data class VariableStringDiv(val leftOperable: VariableNameNode, val rightOperable: StringLiteralNode) : Div
data class StringVariableDiv(val leftOperable: StringLiteralNode, val rightOperable: VariableNameNode) : Div
data class VariableNumberDiv(val leftOperable: VariableNameNode, val rightOperable: NumberLiteralNode) : Div
data class NumberVariableDiv(val leftOperable: NumberLiteralNode, val rightOperable: VariableNameNode) : Div
