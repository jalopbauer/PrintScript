package parser.astParsers.senteceParsers.declarationAssignation

import parser.astParsers.AstBuilder
import parser.astParsers.AstParser
import validlistoftokens.DeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class DeclarationAssignationParser : AstParser<DeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationAssignationValidListOfTokens> =
        DeclarationAssignationValidListOfTokensBuilder()
}
