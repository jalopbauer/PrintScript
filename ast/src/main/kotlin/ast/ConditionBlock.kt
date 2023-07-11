package ast

sealed interface ConditionBlock : AbstractSyntaxTree {
    fun getSentences(): List<AbstractSyntaxTree>?
}
