interface AstBuilder<T : ValidListOfTokens> {
    fun build(validListOfTokens: T): AbstractSyntaxTree<*>
}

class PrintlnBuilder : AstBuilder<PrintlnValidListOfTokens> {
    override fun build(validListOfTokens: PrintlnValidListOfTokens): AbstractSyntaxTree<*> {
        return when (validListOfTokens.printlnValidParameter) {
            is VariableParameter -> PrintlnAst(VariableNameNode(validListOfTokens.printlnValidParameter.variableToken.value))
            is NumberLiteralParameter -> PrintlnAst(NumberLiteralNode(validListOfTokens.printlnValidParameter.numberLiteralToken.value))
            is StringLiteralOrStringConcat -> PrintlnAst(StringLiteralOrStringConcatAstBuilder().build(validListOfTokens.printlnValidParameter))
        }
    }
}

class StringLiteralOrStringConcatAstBuilder : AstBuilder<StringLiteralOrStringConcat> {
    override fun build(validListOfTokens: StringLiteralOrStringConcat): PrintlnAstParameter {
        TODO("Not yet implemented")
    }
}

class DeclarationBuilder : AstBuilder<DeclarationValidListOfTokens> {
    override fun build(validListOfTokens: DeclarationValidListOfTokens): AbstractSyntaxTree<*> {
        TODO("Not yet implemented")
    }
}

class AssignationBuilder : AstBuilder<AssignationValidListOfTokens> {
    override fun build(validListOfTokens: AssignationValidListOfTokens): AbstractSyntaxTree<*> {
        TODO("Not yet implemented")
    }
}

class DeclarationAssignationBuilder : AstBuilder<DeclarationAssignationValidListOfTokens> {
    override fun build(validListOfTokens: DeclarationAssignationValidListOfTokens): AbstractSyntaxTree<*> {
        TODO("Not yet implemented")
    }
}
