package parser.astParsers.senteceParsers.declarationAssignation

import parser.astParsers.AstBuilder
import parser.astParsers.AstParser
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class ConstDeclarationAssignationParser : AstParser<ConstDeclarationAssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<ConstDeclarationAssignationValidListOfTokens> =
        ConstDeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<ConstDeclarationAssignationValidListOfTokens> =
        ConstDeclarationAssignationValidListOfTokensBuilder()
}
