package ast

data class IfElseStatement(val ifStatement: IfStatement, val elseStatement: ElseStatement) : AbstractSyntaxTree
