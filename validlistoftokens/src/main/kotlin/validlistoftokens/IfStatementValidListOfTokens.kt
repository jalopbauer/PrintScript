package validlistoftokens

import token.IfStatementValidListOfTokensParameter

data class IfStatementValidListOfTokens(
    val booleanLiteralToken: IfStatementValidListOfTokensParameter,
    val sentencesValidListOfTokens: SentencesValidListOfTokens
) : ValidListOfTokens
