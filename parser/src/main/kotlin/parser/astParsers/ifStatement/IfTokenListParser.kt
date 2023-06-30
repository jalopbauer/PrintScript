package parser.astParsers.ifStatement

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import parser.astParsers.senteceParsers.SentenceBuilder
import validlistoftokens.IfStatementValidListOfTokens
import validlistoftokens.IfStatementValidListOfTokensBuilder
import validlistoftokens.SentencesValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class IfTokenListParser(private val sentenceBuilder: SentenceBuilder, private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder) : AstTokenListParser<IfStatementValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<IfStatementValidListOfTokens> =
        IfStatementBuilder(sentenceBuilder)

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<IfStatementValidListOfTokens> =
        IfStatementValidListOfTokensBuilder(sentencesValidListOfTokensBuilder)
}
