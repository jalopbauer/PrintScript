package validlistoftokens

import token.BooleanLiteralToken
import token.NumberLiteralToken
import token.ReadInputToken
import token.StringLiteralToken
import token.Token
import token.VariableNameToken

class PrintlnParameterValidListOfTokensBuilder : ValidListOfTokensBuilder<PrintlnParameterValidListOfTokens> {
    override fun validate(tokens: List<Token>): PrintlnParameterValidListOfTokens? =
        when (tokens.size) {
            1 -> {
                when (val singleToken = tokens.component1()) {
                    is ReadInputToken -> ReadInputParameter(singleToken)
                    is VariableNameToken -> VariableParameter(singleToken)
                    is StringLiteralToken -> StringLiteralOrStringConcatValidListOfTokens(listOf(singleToken))
                    is NumberLiteralToken -> NumberLiteralParameter(singleToken)
                    is BooleanLiteralToken -> BooleanLiteralParameter(singleToken)
                    else -> null
                }
            }
            else -> StringLiteralOrConcatValidListOfTokensBuilder().validate(tokens)
        }
}
