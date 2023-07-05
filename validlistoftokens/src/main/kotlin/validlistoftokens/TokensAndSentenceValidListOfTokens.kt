package validlistoftokens

import token.Token

data class TokensAndSentenceValidListOfTokens(val tokens: List<Token> = listOf(), val sentenceValidListOfTokens: List<SentenceValidListOfTokens?> = listOf())
