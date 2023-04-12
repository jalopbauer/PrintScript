import token.Token
interface Parser<T> {
    fun parse(tokensInCodeBlock: List<Token>): T?
}

interface AstParser<T : ValidListOfTokens> : Parser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        parserAstValidator().validate(tokensInCodeBlock)?.let { parserAstBuilder().build(it) }
    fun parserAstBuilder(): AstBuilder<T>
    fun parserAstValidator(): AstValidator<T>
}

class PrintlnParser : AstParser<PrintlnValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<PrintlnValidListOfTokens> {
        TODO("Not yet implemented")
    }

    override fun parserAstValidator(): AstValidator<PrintlnValidListOfTokens> =
        PrintlnAstValidator()
}

class DeclarationParser : AstParser<DeclarationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationValidListOfTokens> {
        TODO("Not yet implemented")
    }

    override fun parserAstValidator(): AstValidator<DeclarationValidListOfTokens> {
        TODO("Not yet implemented")
    }
}

class AssignationParser : AstParser<AssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<AssignationValidListOfTokens> {
        TODO("Not yet implemented")
    }

    override fun parserAstValidator(): AstValidator<AssignationValidListOfTokens> {
        TODO("Not yet implemented")
    }
}

class DeclarationAssignationParser : AstParser<DeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationAssignationValidListOfTokens> {
        TODO("Not yet implemented")
    }

    override fun parserAstValidator(): AstValidator<DeclarationAssignationValidListOfTokens> {
        TODO("Not yet implemented")
    }
}
