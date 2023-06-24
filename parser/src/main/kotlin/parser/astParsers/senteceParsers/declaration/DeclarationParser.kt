package parser.astParsers.senteceParsers.declaration

import parser.astParsers.AstBuilder
import parser.astParsers.AstParser
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class DeclarationParser : AstParser<DeclarationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationValidListOfTokens> =
        DeclarationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationValidListOfTokens> =
        DeclarationValidListOfTokensBuilder()
}
