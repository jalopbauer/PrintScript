package parser.astParsers.senteceParsers.declarationAssignation

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class ConstDeclarationAssignationTokenListParser : AstTokenListParser<ConstDeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<ConstDeclarationAssignationValidListOfTokens> =
        ConstDeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<ConstDeclarationAssignationValidListOfTokens> =
        ConstDeclarationAssignationValidListOfTokensBuilder()
}
