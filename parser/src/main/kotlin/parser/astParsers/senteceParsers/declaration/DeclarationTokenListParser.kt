package parser.astParsers.senteceParsers.declaration

import ast.DeclarationAst
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class DeclarationTokenListParser : AstTokenListParser<DeclarationValidListOfTokens, DeclarationAst> {
    override fun parserAstBuilder(): AstBuilder<DeclarationValidListOfTokens, DeclarationAst> =
        DeclarationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationValidListOfTokens> =
        SentenceValidListOfTokenBuilder(DeclarationValidListOfTokensBuilder())
}
