import token.Token

sealed interface Sentence {
    fun getAst(): AbstractSyntaxTree
}
data class Declaration(val variableName: Token, val variableType: Token) : Sentence {
    override fun getAst(): AbstractSyntaxTree {
        return Node(
            ":",
            NodeValue(variableName.value),
            NodeValue(variableType.value)
        )
    }
}

data class Println(val tokensInsideParenthesis: List<Token>) : Sentence {
    override fun getAst(): AbstractSyntaxTree {
        TODO("Not yet implemented")
    }
}

data class Assignation(val tokensInsideParenthesis: List<Token>) : Sentence {
    override fun getAst(): AbstractSyntaxTree {
        TODO("Not yet implemented")
    }
}

data class DeclarationAssignation(val declaration: Declaration, val assignation: Assignation) : Sentence {
    override fun getAst(): AbstractSyntaxTree {
        TODO("Not yet implemented")
    }
}
