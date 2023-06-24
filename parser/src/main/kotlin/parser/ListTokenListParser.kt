package parser

import token.Token

class ListTokenListParser<T>(private val tokenListParsers: List<TokenListParser<T>>) : TokenListParser<T> {
    override fun parse(tokensInCodeBlock: List<Token>): T? {
        val assignationResult: T? = null
        return tokenListParsers.fold(assignationResult) { acc, astParser ->
            acc ?: astParser.parse(tokensInCodeBlock)
        }
    }
}
