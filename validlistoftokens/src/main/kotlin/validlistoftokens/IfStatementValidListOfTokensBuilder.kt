package validlistoftokens

import token.IfStatementValidListOfTokensParameter
import token.IfToken
import token.LeftCurlyBracketsToken
import token.LeftParenthesisToken
import token.RightCurlyBracketsToken
import token.RightParenthesisToken
import token.Token

class IfStatementValidListOfTokensBuilder(private val sentencesValidListOfTokensBuilder: SentencesValidListOfTokensBuilder) : ValidListOfTokensBuilder<IfStatementValidListOfTokens> {
    override fun validate(tokens: List<Token>): IfStatementValidListOfTokens? {
        return if (
            tokens.size >= 6 &&
            tokens.getOrNull(0) is IfToken &&
            tokens.getOrNull(1) is LeftParenthesisToken &&
            tokens.getOrNull(2) is IfStatementValidListOfTokensParameter &&
            tokens.getOrNull(3) is RightParenthesisToken &&
            tokens.getOrNull(4) is LeftCurlyBracketsToken &&
            tokens.lastOrNull() is RightCurlyBracketsToken
        ) {
            sentencesValidListOfTokensBuilder.validate(tokens.subList(5, tokens.size - 1))
                ?.let { IfStatementValidListOfTokens(tokens[2] as IfStatementValidListOfTokensParameter, it) }
        } else {
            null
        }
    }
}
