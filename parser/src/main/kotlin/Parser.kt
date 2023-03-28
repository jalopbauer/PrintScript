import token.Token
import token.TokenName

interface Parser {
    fun parse(nextToken: Token, previousRelevantTokens: List<Token>): ParserResponse
}

class ParserImpl(private val sentenceBuilder: SentenceBuilder):Parser {
    override fun parse(nextToken: Token, previousRelevantTokens: List<Token>): ParserResponse {
        val tokens = previousRelevantTokens + nextToken
        return if (nextToken.tokenName == TokenName.SEMICOLON)   {
            val sentence = sentenceBuilder.build(tokens)


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
