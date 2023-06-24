package validlistoftokens

import token.ElseToken
import token.LeftCurlyBracketsToken
import token.RightCurlyBracketsToken
import token.Token

data class ElseStatementValidListOfTokens(
    val sentencesValidListOfTokens: SentencesValidListOfTokens
) : ValidListOfTokens

class ElseStatementValidListOfTokensBuilder(private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder) : ValidListOfTokensBuilder<ElseStatementValidListOfTokens> {
    override fun validate(tokens: List<Token>): ElseStatementValidListOfTokens? {
        return if (
            tokens.size >= 3 &&
            tokens.component1() is ElseToken &&
            tokens.component2() is LeftCurlyBracketsToken &&
            tokens.last() is RightCurlyBracketsToken
        ) {
            sentencesValidListOfTokensBuilder.validate(tokens.subList(3, tokens.size - 1))
                ?.let { ElseStatementValidListOfTokens(it) }
        } else {
            null
        }
    }
}
