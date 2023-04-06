interface ParserAstBuilder {
    fun build(validListOfTokens: ValidListOfTokens): AbstractSyntaxTree<*>
}
