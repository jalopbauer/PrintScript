package validlistoftokens

import token.Token

interface ValidListOfTokensBuilder<T : ValidListOfTokens> {
    fun validate(tokens: List<Token>): T?
}
