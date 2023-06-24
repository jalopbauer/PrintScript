package parser

import token.Token

class ListParser<T>(private val parsers: List<Parser<T>>) : Parser<T> {
    override fun parse(tokensInCodeBlock: List<Token>): T? {
        val assignationResult: T? = null
        return parsers.fold(assignationResult) { acc, astParser ->
            acc ?: astParser.parse(tokensInCodeBlock)
        }
    }
}
