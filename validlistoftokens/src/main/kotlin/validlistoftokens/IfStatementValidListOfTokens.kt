package validlistoftokens

import token.BooleanLiteralToken
import token.IfToken
import token.LeftCurlyBracketsToken
import token.LeftParenthesisToken
import token.RightCurlyBracketsToken
import token.RightParenthesisToken
import token.Token

data class IfStatementValidListOfTokens(
    val sentencesValidListOfTokens1: BooleanLiteralToken,
    val sentencesValidListOfTokens: SentencesValidListOfTokens
) : ValidListOfTokens

class IfStatementValidListOfTokensBuilder(private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder) : ValidListOfTokensBuilder<IfStatementValidListOfTokens> {
    override fun validate(tokens: List<Token>): IfStatementValidListOfTokens? {
        return if (
            tokens.size >= 6 &&
            tokens.component1() is IfToken &&
            tokens.component2() is LeftParenthesisToken &&
            tokens.component3() is BooleanLiteralToken &&
            tokens.component4() is RightParenthesisToken &&
            tokens.component5() is LeftCurlyBracketsToken &&
            tokens.last() is RightCurlyBracketsToken
        ) {
            sentencesValidListOfTokensBuilder.validate(tokens.subList(6, tokens.size - 1))
                ?.let { IfStatementValidListOfTokens(tokens.component3() as BooleanLiteralToken, it) }
        } else {
            null
        }
    }
}
