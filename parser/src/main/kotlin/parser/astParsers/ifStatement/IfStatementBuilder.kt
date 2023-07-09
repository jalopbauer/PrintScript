package parser.astParsers.ifStatement

import ast.FalseLiteral
import ast.IfStatement
import ast.TrueLiteral
import parser.astParsers.AstBuilder
import parser.astParsers.senteceParsers.SentenceBuilder
import token.FalseLiteralToken
import token.TrueLiteralToken
import validlistoftokens.IfStatementValidListOfTokens

class IfStatementBuilder(private val sentenceBuilder: SentenceBuilder) : AstBuilder<IfStatementValidListOfTokens, IfStatement> {

    override fun build(validListOfTokens: IfStatementValidListOfTokens): IfStatement =
        IfStatement(
            when (validListOfTokens.booleanLiteralToken) {
                is FalseLiteralToken -> FalseLiteral
                is TrueLiteralToken -> TrueLiteral
            },
            validListOfTokens.sentencesValidListOfTokens.sentences.map {
                sentenceBuilder.build(it)
            }
        )
}
