import token.Token
interface Parser<T> {
    fun parse(tokensInCodeBlock: List<Token>): T?
}

interface AstParser : Parser<AbstractSyntaxTree<*>> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree<*>? =
        parserAstValidator().validate(tokensInCodeBlock)?.let { parserAstBuilder().build(it) }
    fun parserAstBuilder(): ParserAstBuilder
    fun parserAstValidator(): ParserAstValidator
}
