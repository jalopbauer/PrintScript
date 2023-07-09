package parser.astParsers.senteceParsers.assignation

import ast.AssignationAst
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.AssignationValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class AssignationTokenListParser : AstTokenListParser<AssignationValidListOfTokens, AssignationAst> {
    override fun parserAstBuilder(): AstBuilder<AssignationValidListOfTokens, AssignationAst> =
        AssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<AssignationValidListOfTokens> =
        SentenceValidListOfTokenBuilder(AssignationValidListOfTokensBuilder())
}
