package parser.astParsers.ifStatement

import ast.FalseLiteral
import ast.IfStatement
import ast.TrueLiteral
import ast.VariableNameNode
import parser.astParsers.AstBuilder
import parser.astParsers.senteceParsers.SentenceBuilder
import token.FalseLiteralToken
import token.TrueLiteralToken
import token.VariableNameToken
import validlistoftokens.IfStatementValidListOfTokens

class IfStatementBuilder(private val sentenceBuilder: SentenceBuilder) : AstBuilder<IfStatementValidListOfTokens, IfStatement> {

    override fun build(validListOfTokens: IfStatementValidListOfTokens): IfStatement =
        IfStatement(
            when (val token = validListOfTokens.booleanLiteralToken) {
                is FalseLiteralToken -> FalseLiteral
                is TrueLiteralToken -> TrueLiteral
                is VariableNameToken -> VariableNameNode(token.value)
            },
            validListOfTokens.sentencesValidListOfTokens.sentences.map {
                sentenceBuilder.build(it)
            }
        )
}
