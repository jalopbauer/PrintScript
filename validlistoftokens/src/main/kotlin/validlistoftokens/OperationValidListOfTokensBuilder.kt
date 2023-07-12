package validlistoftokens

import token.NumberLiteralToken
import token.Token
import token.TokenName

class OperationValidListOfTokensBuilder : ValidListOfTokensBuilder<OperationValidListOfTokens> {
    override fun validate(tokens: List<Token>): OperationValidListOfTokens? {
        var previousToken = tokens[0]
        if (tokens.size == 1) {
            when (previousToken) {
                is NumberLiteralToken -> return NumberLiteralParameter(previousToken)
                else -> return null
            }
        }
        for (token in tokens.subList(1, tokens.size)) {
            when (token.tokenName()) {
                TokenName.NUMBER_LITERAL, TokenName.VARIABLE -> if (!(
                    previousToken.tokenName() == TokenName.LEFT_PARENTHESIS ||
                        previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return null }
                TokenName.SUM, TokenName.SUB, TokenName.MULT, TokenName.DIV -> if (!(
                    previousToken.tokenName() == TokenName.NUMBER_LITERAL ||
                        previousToken.tokenName() == TokenName.RIGHT_PARENTHESIS ||
                        previousToken.tokenName() == TokenName.VARIABLE
                    )
                ) { return null }
                TokenName.LEFT_PARENTHESIS -> if (!(
                    previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return null }
                TokenName.RIGHT_PARENTHESIS -> if (previousToken.tokenName() != TokenName.NUMBER_LITERAL ||
                    previousToken.tokenName() != TokenName.VARIABLE
                ) { return null }
                else -> return null
            }
            previousToken = token
        }
        return OperationValidListOfConcatTokens(tokens)
    }
    fun validateChain(tokens: List<Token>): Boolean {
        var previousToken = tokens[0]
        if (tokens.size == 1) {
            when (previousToken) {
                is NumberLiteralToken -> return true
                else -> return false
            }
        }
        for (token in tokens.subList(1, tokens.size)) {
            when (token.tokenName()) {
                TokenName.NUMBER_LITERAL, TokenName.VARIABLE -> if (!(
                    previousToken.tokenName() == TokenName.LEFT_PARENTHESIS ||
                        previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return false }
                TokenName.SUM, TokenName.SUB, TokenName.MULT, TokenName.DIV -> if (!(
                    previousToken.tokenName() == TokenName.NUMBER_LITERAL ||
                        previousToken.tokenName() == TokenName.RIGHT_PARENTHESIS ||
                        previousToken.tokenName() == TokenName.VARIABLE
                    )
                ) { return false }
                TokenName.LEFT_PARENTHESIS -> if (!(
                    previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return false }
                TokenName.RIGHT_PARENTHESIS -> if (previousToken.tokenName() != TokenName.NUMBER_LITERAL ||
                    previousToken.tokenName() !== TokenName.VARIABLE
                ) { return false }
                else -> return false
            }
            previousToken = token
        }
        return true
    }
}
