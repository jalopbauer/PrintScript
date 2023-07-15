package ast

data class IfElseStatement(val ifStatement: IfStatement, val elseStatement: ElseStatement) : ConditionBlock {
    override fun getConditionBlockParameter(): ConditionBlockParameter =
        ifStatement.getConditionBlockParameter()

    override fun getSentences(booleanLiteral: BooleanLiteral): List<AbstractSyntaxTree> =
        ifStatement.getSentences(booleanLiteral) ?: elseStatement.map
}
