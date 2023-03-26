import token.Token

sealed interface ParserResponse
data class Error(val token: Token): ParserResponse
data class Tokens(val tokens: List<Token>): ParserResponse
data class ValidSentence(val sentence: Sentence): ParserResponse
