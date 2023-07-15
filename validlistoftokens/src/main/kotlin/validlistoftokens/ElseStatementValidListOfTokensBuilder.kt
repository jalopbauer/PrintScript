package validlistoftokens

import token.ElseToken
import token.LeftCurlyBracketsToken
import token.RightCurlyBracketsToken
import token.Token

class ElseStatementValidListOfTokensBuilder(private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder) : ValidListOfTokensBuilder<ElseStatementValidListOfTokens> {
    override fun validate(tokens: List<Token>): ElseStatementValidListOfTokens? {
        return if (
            tokens.size >= 3 &&
            tokens.getOrNull(0) is ElseToken &&
            tokens.getOrNull(1) is LeftCurlyBracketsToken &&
            tokens.last() is RightCurlyBracketsToken
        ) {
            sentencesValidListOfTokensBuilder.validate(tokens.subList(2, tokens.size - 1))
                ?.let { ElseStatementValidListOfTokens(it) }
        } else {
            null
        }
    }
}
