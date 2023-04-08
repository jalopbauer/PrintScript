interface AstBuilder<T : ValidListOfTokens> {
    fun build(validListOfTokens: T): ValuedNode<*>
}

class PrintlnBuilder : AstBuilder<PrintlnValidListOfTokens> {
    override fun build(validListOfTokens: PrintlnValidListOfTokens): ValuedNode<*> {
        return when (validListOfTokens.printlnValidParameter) {
            is VariableParameter -> PrintlnAst(VariableNameNode(validListOfTokens.printlnValidParameter.variableToken.value))
            is NumberLiteralParameter -> PrintlnAst(NumberLiteralStringNode(NumberLiteralNode(validListOfTokens.printlnValidParameter.numberLiteralToken.value)))
            is StringLiteralOrStringConcat -> PrintlnAst(StringLiteralOrStringConcatAstBuilder().build(validListOfTokens.printlnValidParameter))
        }
    }
}

class StringLiteralOrStringConcatAstBuilder : AstBuilder<StringLiteralOrStringConcat> {
    override fun build(validListOfTokens: StringLiteralOrStringConcat): PrintlnAstParameter {
        TODO("Not yet implemented")
    }
}
