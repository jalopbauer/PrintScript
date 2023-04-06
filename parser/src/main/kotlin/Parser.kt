import token.Token
interface Parser<T> {
    fun parse(tokensInCodeBlock: List<Token>): T?
}

interface AstParser<T : ValidListOfTokens> : Parser<AbstractSyntaxTree<*>> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree<*>? =
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
