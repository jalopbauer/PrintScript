package parser.astParsers.senteceParsers.println

import parser.astParsers.AstBuilder
import parser.astParsers.AstParser
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.PrintlnValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class PrintlnParser : AstParser<PrintlnValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<PrintlnValidListOfTokens> =
        PrintlnBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<PrintlnValidListOfTokens> =
        PrintlnValidListOfTokensBuilder()
}
