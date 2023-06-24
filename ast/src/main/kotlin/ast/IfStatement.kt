package ast

data class IfStatement(val map1: BooleanLiteral, val map: List<AbstractSyntaxTree>) : AbstractSyntaxTree
