import token.Token
import token.TokenName

interface Parser<T> {
    fun parse(nextToken: Token, previousRelevantTokens: List<Token>): T
}

class ParserImpl(private val sentenceValidator: SentenceValidator) : Parser<ParserResponse> {
    override fun parse(nextToken: Token, previousRelevantTokens: List<Token>): ParserResponse {
        val tokens = previousRelevantTokens + nextToken
        return if (nextToken.tokenName() == TokenName.SEMICOLON) {
            val sentence = sentenceValidator.build(tokens)
            if (sentence != null) {
                ValidSentence(sentence)
            } else {
                Error(nextToken)
            }
        } else {
            Tokens(tokens)
        }
    }
}
