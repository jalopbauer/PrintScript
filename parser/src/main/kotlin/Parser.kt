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
    override fun parserAstBuilder(): AstBuilder<PrintlnValidListOfTokens> =
        PrintlnBuilder()

    override fun parserAstValidator(): AstValidator<PrintlnValidListOfTokens> =
        PrintlnAstValidator()
}

class DeclarationParser : AstParser<DeclarationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationValidListOfTokens> =
        DeclarationBuilder()

    override fun parserAstValidator(): AstValidator<DeclarationValidListOfTokens> =
        DeclarationValidator()
}

class AssignationParser : AstParser<AssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<AssignationValidListOfTokens> =
        AssignationBuilder()

    override fun parserAstValidator(): AstValidator<AssignationValidListOfTokens> =
        AssignationValidator()
}

class DeclarationAssignationParser : AstParser<DeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationBuilder()

    override fun parserAstValidator(): AstValidator<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationValidator()
}
