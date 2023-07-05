package validlistoftokens

import token.Token

class ListValidListOfTokenBuilder<T : ValidListOfTokens>(private val tokenListParsers: List<ValidListOfTokensBuilder<T>>) : ValidListOfTokensBuilder<T> {
    override fun validate(tokens: List<Token>): T? {
        val assignationResult: T? = null
        return tokenListParsers.fold(assignationResult) { acc, astParser ->
            acc ?: astParser.validate(tokens)
        }
    }
}
