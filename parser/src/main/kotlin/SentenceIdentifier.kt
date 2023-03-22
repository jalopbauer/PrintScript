import token.Token

interface SentenceIdentifier {
    fun identify(previousTokensList: List<Token>, nextToken: Token): PossibleSentence?
}