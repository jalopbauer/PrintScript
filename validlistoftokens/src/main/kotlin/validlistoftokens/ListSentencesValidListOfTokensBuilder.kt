package validlistoftokens

import token.Token

class ListSentencesValidListOfTokensBuilder : ValidListOfTokensBuilder<SentenceValidListOfTokens> {
    override fun validate(tokens: List<Token>): SentenceValidListOfTokens? =
        ListValidListOfTokenBuilder(
            listOf(
                SentenceValidListOfTokenBuilder(
                    PrintlnValidListOfTokensBuilder()
                )
            )
        ).validate(tokens)
}
