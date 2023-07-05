package validlistoftokens

import token.Token
import token.TokenName

class StringLiteralOrConcatValidListOfTokensBuilder :
    ValidListOfTokensBuilder<StringLiteralOrStringConcatValidListOfTokens> {
    override fun validate(tokens: List<Token>): StringLiteralOrStringConcatValidListOfTokens? {
        var tokenCounter = 1
        for (token in tokens) {
            if (tokenCounter % 2 == 0) {
                if (token.tokenName() != TokenName.SUM) {
                    return null
                }
            } else if (token.tokenName() != TokenName.STRING_LITERAL) {
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
