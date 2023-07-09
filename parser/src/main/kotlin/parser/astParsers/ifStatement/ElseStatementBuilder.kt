package parser.astParsers.ifStatement

import ast.ElseStatement
import parser.astParsers.AstBuilder
import parser.astParsers.senteceParsers.SentenceBuilder
import validlistoftokens.ElseStatementValidListOfTokens

class ElseStatementBuilder(private val sentenceBuilder: SentenceBuilder) : AstBuilder<ElseStatementValidListOfTokens, ElseStatement> {

    override fun build(validListOfTokens: ElseStatementValidListOfTokens): ElseStatement =
        ElseStatement(
            validListOfTokens.sentencesValidListOfTokens.sentences.map {
                sentenceBuilder.build(it)
            }
        )
}
