import token.Token

class LexerTester
class LexerTokenResultTranformer : Transformer<LexerTokenResult> {
    override fun to(from: String): LexerTokenResult =
        from.split(',', limit = 3).let {
                (tokenName, lineNumber, position) ->
            LexerTokenResult(tokenName.trim(), lineNumber.trim().toInt(), position.trim().toInt())
        }
}

class LexerTokenResult(private val tokenName: String, private val lineNumber: Int, private val position: Int) : TestValue<Token> {
    override fun hasError(comparedTo: Token): String? {
        val errorMessages = listOfNotNull(
            tokenNameErrorString(comparedTo),
            lineNumberErrorString(comparedTo),
            positionErrorString(comparedTo)
        )
        return when (errorMessages) {
            listOf<String>() -> null
            else -> errorMessages.joinToString(prefix = "<", postfix = ">", separator = ", ")
        }
    }

    private fun tokenNameErrorString(comparedTo: Token): String? =
        if (tokenName == comparedTo.tokenName().toString()) {
            errorMessage(tokenName, comparedTo.tokenName().toString())
        } else {
            null
        }

    private fun lineNumberErrorString(comparedTo: Token): String? =
        if (lineNumber == comparedTo.lineNumber()) {
            errorMessage(lineNumber.toString(), comparedTo.lineNumber().toString())
        } else {
            null
        }

    private fun positionErrorString(comparedTo: Token): String? =
        if (position == comparedTo.position()) {
            errorMessage(position.toString(), comparedTo.position().toString())
        } else {
            null
        }
}
