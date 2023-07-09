package parser.astParsers.ifStatement

import ast.ElseStatement
import parser.astParsers.AstBuilder
import parser.astParsers.AstTokenListParser
import parser.astParsers.senteceParsers.SentenceBuilder
import validlistoftokens.ElseStatementValidListOfTokens
import validlistoftokens.ElseStatementValidListOfTokensBuilder
import validlistoftokens.SentencesValidListOfTokensBuilder
import validlistoftokens.ValidListOfTokensBuilder

class ElseTokenListParser(
    private val sentenceBuilder: SentenceBuilder = SentenceBuilder(),
    private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder = SentencesValidListOfTokensBuilder()
) : AstTokenListParser<ElseStatementValidListOfTokens, ElseStatement> {
    override fun parserAstBuilder(): AstBuilder<ElseStatementValidListOfTokens, ElseStatement> =
        ElseStatementBuilder(sentenceBuilder)

    override fun validListOfTokensBuilder(): ValidListOfTokensBuilder<ElseStatementValidListOfTokens> =
        ElseStatementValidListOfTokensBuilder(sentencesValidListOfTokensBuilder)
}
