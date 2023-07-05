package validlistoftokens

import token.Token
import token.TokenName

class PrintlnValidListOfTokensBuilder : ValidListOfTokensBuilder<PrintlnValidListOfTokens> {
    override fun validate(tokens: List<Token>): PrintlnValidListOfTokens? =
        if (tokens.size < 5 ||
            tokens.component1().tokenName() != TokenName.PRINT ||
            tokens.component2().tokenName() != TokenName.LEFT_PARENTHESIS ||
            tokens[tokens.size - 2].tokenName() != TokenName.RIGHT_PARENTHESIS
        ) {
            null
        } else {
            PrintlnParameterValidListOfTokensBuilder().validate(tokens.subList(2, tokens.size - 2))?.let {
                PrintlnValidListOfTokens(
                    it
                )
            }
        }
}
