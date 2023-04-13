data class NumberNumberSum(val leftOperable: NumberLiteralNode, val rightOperable: NumberLiteralNode) : Sum {
    fun leftNumber(): Int =
        leftOperable.number
    fun rightNumber(): Int =
        rightOperable.number
}
data class NumberStringSum(val leftOperable: NumberLiteralNode, val rightOperable: StringLiteralNode) : Sum
data class StringNumberSum(val leftOperable: StringLiteralNode, val rightOperable: NumberLiteralNode) : Sum
data class StringStringSum(val leftOperable: StringLiteralNode, val rightOperable: StringLiteralNode) : Sum
data class VariableStringSum(val leftOperable: VariableNameNode, val rightOperable: StringLiteralNode) : Sum
data class StringVariableSum(val leftOperable: StringLiteralNode, val rightOperable: VariableNameNode) : Sum
data class VariableNumberSum(val leftOperable: VariableNameNode, val rightOperable: NumberLiteralNode) : Sum
data class NumberVariableSum(val leftOperable: NumberLiteralNode, val rightOperable: VariableNameNode) : Sum
