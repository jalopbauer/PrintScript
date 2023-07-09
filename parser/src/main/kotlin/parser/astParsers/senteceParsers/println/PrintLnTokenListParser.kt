package parser.astParsers.senteceParsers.println

import ast.PrintlnAst
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.PrintlnValidListOfTokensBuilder
import validlistoftokens.SentenceValidListOfTokenBuilder
import validlistoftokens.ValidListOfTokensBuilder

class PrintLnTokenListParser : AstTokenListParser<PrintlnValidListOfTokens, PrintlnAst> {
    override fun parserAstBuilder(): AstBuilder<PrintlnValidListOfTokens, PrintlnAst> =
        PrintlnBuilder()

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<PrintlnValidListOfTokens> =
        SentenceValidListOfTokenBuilder(PrintlnValidListOfTokensBuilder())
}
