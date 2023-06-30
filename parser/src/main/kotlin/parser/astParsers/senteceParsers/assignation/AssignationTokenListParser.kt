package parser.astParsers.senteceParsers.assignation

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.AssignationValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class AssignationTokenListParser : AstTokenListParser<AssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<AssignationValidListOfTokens> =
        AssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<AssignationValidListOfTokens> =
        SentenceValidListOfTokenBuilder(AssignationValidListOfTokensBuilder())
}
