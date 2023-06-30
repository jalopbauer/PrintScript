package parser.astParsers.ifStatement

import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import parser.astParsers.senteceParsers.SentenceBuilder
import validlistoftokens.ElseStatementValidListOfTokens
import validlistoftokens.ElseStatementValidListOfTokensBuilder
import validlistoftokens.SentencesValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class ElseTokenListParser(private val sentenceBuilder: SentenceBuilder, private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder) : AstTokenListParser<ElseStatementValidListOfTokens> {
    override fun parserAstBuilder(): AstBuilder<ElseStatementValidListOfTokens> =
        ElseStatementBuilder(sentenceBuilder)

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<ElseStatementValidListOfTokens> =
        ElseStatementValidListOfTokensBuilder(sentencesValidListOfTokensBuilder)
}
