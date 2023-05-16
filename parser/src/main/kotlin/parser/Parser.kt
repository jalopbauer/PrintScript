package parser

import ast.AbstractSyntaxTree
import token.Token
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.AssignationValidListOfTokensBuilder
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.DeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokensBuilder
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.PrintlnValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokens
import validlistoftokens.ValidListOfTokensBuilder
interface Parser<T> {
    fun parse(tokensInCodeBlock: List<Token>): T?
}
class PrintScriptParser : Parser<AbstractSyntaxTree> {

    private val parsers = listOf(
        PrintlnParser(),
        DeclarationParser(),
        AssignationParser(),
        DeclarationAssignationParser(),
        ConstDeclarationAssignationParser()
    )
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        ListParser(parsers).parse(tokensInCodeBlock)
}

class ListParser(private val parsers: List<Parser<AbstractSyntaxTree>>) : Parser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? {
        val assignationResult: AbstractSyntaxTree? = null
        return parsers.fold(assignationResult) { acc, astParser ->
            acc ?: astParser.parse(tokensInCodeBlock)
        }
    }
}

interface AstParser<T : ValidListOfTokens> : Parser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        validListOfTokensBuilder().validate(tokensInCodeBlock)?.let { parserAstBuilder().build(it) }
    fun parserAstBuilder(): AstBuilder<T>
    fun validListOfTokensBuilder(): ValidListOfTokensBuilder<T>
}

class PrintlnParser : AstParser<PrintlnValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<PrintlnValidListOfTokens> =
        PrintlnBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<PrintlnValidListOfTokens> =
        PrintlnValidListOfTokensBuilder()
}

class DeclarationParser : AstParser<DeclarationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationValidListOfTokens> =
        DeclarationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationValidListOfTokens> =
        DeclarationValidListOfTokensBuilder()
}

class AssignationParser : AstParser<AssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<AssignationValidListOfTokens> =
        AssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<AssignationValidListOfTokens> =
        AssignationValidListOfTokensBuilder()
}

class DeclarationAssignationParser : AstParser<DeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationValidListOfTokensBuilder()
}

class ConstDeclarationAssignationParser : AstParser<ConstDeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<ConstDeclarationAssignationValidListOfTokens> =
        ConstDeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<ConstDeclarationAssignationValidListOfTokens> =
        ConstDeclarationAssignationValidListOfTokensBuilder()
}
