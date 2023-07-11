package validlistoftokens

import token.LeftParenthesisToken
import token.ReadInputToken
import token.RightParenthesisToken
import token.StringLiteralToken
import token.Token

class ReadInputValidListOfTokensBuilder : ValidListOfTokensBuilder<ReadInputValidListOfTokens> {
    override fun validate(tokens: List<Token>): ReadInputValidListOfTokens? =
        if (tokens.size == 4 &&
            tokens.component1() is ReadInputToken &&
            tokens.component2() is LeftParenthesisToken &&
            tokens.component3() is StringLiteralToken &&
            tokens.component4() is RightParenthesisToken
        ) {
            ReadInputValidListOfTokens(
                tokens.component1() as ReadInputToken,
                tokens.component2() as LeftParenthesisToken,
                tokens.component3() as StringLiteralToken,
                tokens.component4() as RightParenthesisToken
            )
        } else {
            null
        }
}
