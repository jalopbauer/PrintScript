package validlistoftokens

import token.StringLiteralToken
import token.Token
import token.TokenName
import token.VariableNameToken

class StringLiteralOrConcatValidListOfTokensBuilder :
    ValidListOfTokensBuilder<StringLiteralOrStringConcatValidListOfTokens> {
    override fun validate(tokens: List<Token>): StringLiteralOrStringConcatValidListOfTokens? {
        var tokenCounter = 1
        for (token in tokens) {
            if (tokenCounter % 2 == 0) {
                if (token.tokenName() != TokenName.SUM) {
                    return null
                }
            } else if (token !is StringLiteralToken && token !is VariableNameToken) {
                return null
            }
            tokenCounter++
        }
        return StringLiteralOrStringConcatValidListOfTokens(tokens)
    }
    fun validateChain(tokens: List<Token>): Boolean {
        var tokenCounter = 1
        for (token in tokens) {
            if (tokenCounter % 2 == 0) {
                if (token.tokenName() != TokenName.SUM) {
                    return false
                }
            } else if (token.tokenName() != TokenName.STRING_LITERAL) {
                return false
            }
            tokenCounter++
        }
        return true
    }
}
