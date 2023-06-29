package parser.astBeingCreatedParser

import ast.IfStatement
import parser.Parser
import parser.parserState.astBeingCreated.AstBeingCreated
import parser.parserState.astBeingCreated.ElseStatementBeingCreated
import parser.parserState.astBeingCreated.IfStatementBeingCreated
import parser.parserState.astBeingCreated.SentenceBeingCreated
import token.ElseToken
import token.IfToken

class AstBeingCreatedParser : Parser<AstBeingCreated, AstBeingCreatedParserInput> {
    override fun parse(tokensInCodeBlock: AstBeingCreatedParserInput): AstBeingCreated? {
        val (nextToken, abstractSyntaxTree) = tokensInCodeBlock
        return when {
            nextToken is IfToken -> IfStatementBeingCreated(nextToken)
            nextToken is ElseToken && abstractSyntaxTree is IfStatement -> ElseStatementBeingCreated(
                nextToken,
                abstractSyntaxTree
            )

            nextToken !is IfToken && nextToken !is ElseToken -> SentenceBeingCreated(nextToken)
            else -> null
        }
    }
}
