package ast

data class IfElseStatement(val ifStatement: IfStatement, val elseStatement: ElseStatement) : ConditionBlock {
    override fun getSentences(): List<AbstractSyntaxTree> =
        ifStatement.getSentences() ?: elseStatement.map
}
