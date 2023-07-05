package validlistoftokens

import token.BooleanLiteralToken

data class IfStatementValidListOfTokens(
    val booleanLiteralToken: BooleanLiteralToken,
    val sentencesValidListOfTokens: SentencesValidListOfTokens
) : ValidListOfTokens
