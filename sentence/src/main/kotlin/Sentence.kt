import token.Token
import token.VariableLiteralToken

sealed interface Sentence {
    fun getAst(): ValuedNode<*>
}
data class Declaration(val variableName: VariableLiteralToken, val variableType: Token) : Sentence {
    override fun getAst(): ValuedNode<*> {
        TODO("Not yet implemented")
    }
}
data class Println(val stringValue: StringValue) : Sentence {
    override fun getAst(): ValuedNode<*> {
        TODO("Not yet implemented")
    }
}

data class Assignation(val value: Value) : Sentence {
    override fun getAst(): ValuedNode<*> {
        TODO("Not yet implemented")
    }
}

data class DeclarationAssignation(val declaration: Declaration, val assignation: Assignation) : Sentence {
    override fun getAst(): ValuedNode<*> {
        TODO("Not yet implemented")
    }
}

interface Value : Sentence
data class StringValue(val tokensInsideParenthesis: List<Token>) : Value {
    override fun getAst(): ValuedNode<*> {
        TODO("Not yet implemented")
    }
}

data class NumberValue(val tokensInsideParenthesis: List<Token>) : Value {
    override fun getAst(): ValuedNode<*> {
        TODO("Not yet implemented")
    }
}
