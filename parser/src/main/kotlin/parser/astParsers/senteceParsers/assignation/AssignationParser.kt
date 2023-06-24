package parser.astParsers.senteceParsers.assignation

import parser.astParsers.AstBuilder
import parser.astParsers.AstParser
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.AssignationValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class AssignationParser : AstParser<AssignationValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<AssignationValidListOfTokens> =
        AssignationBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<AssignationValidListOfTokens> =
        AssignationValidListOfTokensBuilder()
}
