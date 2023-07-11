package ast

data class IfStatement(val map1: BooleanLiteral, val map: List<AbstractSyntaxTree>) : ConditionBlock {
    override fun getSentences(): List<AbstractSyntaxTree>? =
        when (map1) {
            FalseLiteral -> null
            TrueLiteral -> map
        }
}
