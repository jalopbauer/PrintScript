import token.Token

data class PossibleSentence(val tokenList: List<Token>, val sentenceType: SentenceType, val isDone: Boolean)
sealed interface ParserResponse
data class Error(val token: Token): ParserResponse
data class PossibleSentences(val possibleSentencesList: List<PossibleSentence>): ParserResponse
interface Sentence: ParserResponse