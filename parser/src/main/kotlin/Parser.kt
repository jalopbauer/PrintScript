import token.Token
import token.TokenName

interface Parser {
    fun parse(nextToken: Token, possibleSentences: PossibleSentences): ParserResponse
}

class ParserImpl(private val sentenceIdentifierMapper: SentenceIdentifierMapper): Parser {
    override fun parse(nextToken: Token, possibleSentences: PossibleSentences): ParserResponse {
        val newPossibleSentences = possibleSentences(possibleSentences.possibleSentencesList, nextToken)
        if (newPossibleSentences.isEmpty()) return Error(nextToken)
        if (nextToken.tokenName == TokenName.SEMICOLON) {
            val firstOrNull = newPossibleSentences.firstOrNull { possibleSentence -> possibleSentence.isDone }
            return if (firstOrNull != null) {
                TODO("Possible Sentence to Sentence")
            } else {
                Error(nextToken)
            }
        }
        return PossibleSentences(newPossibleSentences)
    }

    private fun possibleSentences(
        possibleSentences: List<PossibleSentence>,
        nextToken: Token
    ) = possibleSentences.fold(listOf<PossibleSentence>()) { acc, possibleSentence ->
        val sentenceIdentifier = sentenceIdentifierMapper.getSentenceIdentifier(possibleSentence.sentenceType)
        val identify = sentenceIdentifier.identify(possibleSentence.tokenList, nextToken)
        if (identify != null) {
            acc + identify
        } else {
            acc
        }
    }

}