package ast

data class IfStatement(val map1: ConditionBlockParameter, val map: List<AbstractSyntaxTree>) : ConditionBlock {
    override fun getConditionBlockParameter(): ConditionBlockParameter =
        map1

    override fun getSentences(booleanLiteral: BooleanLiteral): List<AbstractSyntaxTree>? =
        when (booleanLiteral) {
            FalseLiteral -> null
            TrueLiteral -> map
        }
}
