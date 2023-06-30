package parser.astParsers.senteceParsers.println

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.PrintlnValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class PrintLnTokenListParser : AstTokenListParser<PrintlnValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<PrintlnValidListOfTokens> =
        PrintlnBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<PrintlnValidListOfTokens> =
        SentenceValidListOfTokenBuilder(PrintlnValidListOfTokensBuilder())
}
