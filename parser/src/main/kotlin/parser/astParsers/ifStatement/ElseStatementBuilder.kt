package parser.astParsers.ifStatement

import ast.AbstractSyntaxTree
import ast.ElseStatement
import parser.astParsers.AstBuilder
import parser.astParsers.senteceParsers.SentenceBuilder
import validlistoftokens.ElseStatementValidListOfTokens

class ElseStatementBuilder(private val sentenceBuilder: SentenceBuilder) : AstBuilder<ElseStatementValidListOfTokens> {

    override fun build(validListOfTokens: ElseStatementValidListOfTokens): AbstractSyntaxTree =
        ElseStatement(
            validListOfTokens.sentencesValidListOfTokens.sentences.map {
                sentenceBuilder.build(it)
            }
        )
}
