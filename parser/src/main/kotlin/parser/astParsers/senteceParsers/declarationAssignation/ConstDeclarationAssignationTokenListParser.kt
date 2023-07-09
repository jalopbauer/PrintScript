package parser.astParsers.senteceParsers.declarationAssignation

import ast.AssignationDeclarationAst
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class ConstDeclarationAssignationTokenListParser : AstTokenListParser<ConstDeclarationAssignationValidListOfTokens, AssignationDeclarationAst> {
    override fun parserAstBuilder(): AstBuilder<ConstDeclarationAssignationValidListOfTokens, AssignationDeclarationAst> =
        ConstDeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<ConstDeclarationAssignationValidListOfTokens> =
        SentenceValidListOfTokenBuilder(ConstDeclarationAssignationValidListOfTokensBuilder())
}
