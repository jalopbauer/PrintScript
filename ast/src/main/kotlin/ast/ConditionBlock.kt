package ast

sealed interface ConditionBlock : AbstractSyntaxTree {

    fun getConditionBlockParameter(): ConditionBlockParameter
    fun getSentences(booleanLiteral: BooleanLiteral): List<AbstractSyntaxTree>?
}

sealed interface ConditionBlockParameter : AbstractSyntaxTree
