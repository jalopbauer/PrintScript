package parser.astParsers.senteceParsers.declarationAssignation

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.DeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class DeclarationAssignationTokenListParser : AstTokenListParser<DeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationAssignationValidListOfTokens> =
        SentenceValidListOfTokenBuilder(DeclarationAssignationValidListOfTokensBuilder())
}
