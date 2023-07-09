package parser.astParsers.senteceParsers.declarationAssignation

import ast.AssignationDeclarationAst
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.DeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationAssignationValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class DeclarationAssignationTokenListParser : AstTokenListParser<DeclarationAssignationValidListOfTokens, AssignationDeclarationAst> {
    override fun parserAstBuilder(): AstBuilder<DeclarationAssignationValidListOfTokens, AssignationDeclarationAst> =
        DeclarationAssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<DeclarationAssignationValidListOfTokens> =
        SentenceValidListOfTokenBuilder(DeclarationAssignationValidListOfTokensBuilder())
}
