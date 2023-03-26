import token.Token

interface SentenceBuilder {
    fun build(tokenList: List<Token>): Sentence?
}
