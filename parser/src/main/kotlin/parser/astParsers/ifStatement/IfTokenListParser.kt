package parser.astParsers.ifStatement

import ast.IfStatement
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import parser.astParsers.senteceParsers.SentenceBuilder
import validlistoftokens.IfStatementValidListOfTokens
import validlistoftokens.IfStatementValidListOfTokensBuilder
import validlistoftokens.SentencesValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class IfTokenListParser(
    private val sentenceBuilder: SentenceBuilder = SentenceBuilder(),
    private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder = SentencesValidListOfTokensBuilder()
) : AstTokenListParser<IfStatementValidListOfTokens, IfStatement> {
    override fun parserAstBuilder(): AstBuilder<IfStatementValidListOfTokens, IfStatement> =
        IfStatementBuilder(sentenceBuilder)

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<IfStatementValidListOfTokens> =
        IfStatementValidListOfTokensBuilder(sentencesValidListOfTokensBuilder)
}
