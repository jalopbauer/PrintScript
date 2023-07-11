package validlistoftokens

import token.LeftParenthesisToken
import token.ReadInputToken
import token.RightParenthesisToken
import token.StringLiteralToken

data class ReadInputValidListOfTokens(
    val readInputToken: ReadInputToken,
    val leftParenthesisToken: LeftParenthesisToken,
    val stringLiteralToken: StringLiteralToken,
    val rightParenthesisToken: RightParenthesisToken
) : PrintlnParameterValidListOfTokens
