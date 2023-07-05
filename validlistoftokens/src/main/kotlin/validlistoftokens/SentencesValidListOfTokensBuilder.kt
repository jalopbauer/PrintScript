package validlistoftokens

import token.SemicolonToken
import token.Token

class SentencesValidListOfTokensBuilder(private val validListOfTokensBuilders: ValidListOfTokensBuilder<out SentenceValidListOfTokens> = ListSentencesValidListOfTokensBuilder()) : ValidListOfTokensBuilder<SentencesValidListOfTokens> {
    override fun validate(tokens: List<Token>): SentencesValidListOfTokens? =
        if (tokens.last() is SemicolonToken) { // If semicolon not last is always invalid
            val sentenceValidListOfTokens =
                tokens.fold(TokensAndSentenceValidListOfTokens()) { (tokens, validListOfTokens), token -> // Split every SemicolonToken and validate it
                    if (token is SemicolonToken) {
                        TokensAndSentenceValidListOfTokens(
                            listOf(),
                            validListOfTokens + validListOfTokensBuilders.validate(tokens)
                        )
                    } else {
                        TokensAndSentenceValidListOfTokens(tokens + token, validListOfTokens)
                    }
                }.sentenceValidListOfTokens
            val sentenceValidListOfTokensWithoutNull = sentenceValidListOfTokens.filterNotNull()
            if (sentenceValidListOfTokens.size == sentenceValidListOfTokensWithoutNull.size) {
                SentencesValidListOfTokens(sentenceValidListOfTokensWithoutNull)
            } else {
                null
            }
        } else {
            null
        }
}

data class TokensAndSentenceValidListOfTokens(val tokens: List<Token> = listOf(), val sentenceValidListOfTokens: List<SentenceValidListOfTokens?> = listOf())
