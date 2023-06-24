package parser.astParsers.senteceParsers.declaration

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class DeclarationTokenListParser : AstTokenListParser<DeclarationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationValidListOfTokens> =
        DeclarationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationValidListOfTokens> =
        DeclarationValidListOfTokensBuilder()
}
