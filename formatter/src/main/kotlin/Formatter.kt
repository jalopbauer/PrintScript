import token.Token

interface Formatter {
    fun format(listOfTokens: List<Token>): String?
}

class ValidListOfTokensFormatter<T : ValidListOfTokens>(
    private val validListOfTokens: ValidListOfTokensBuilder<T>,
    private val rule: Rule<T>
) : Formatter {
    override fun format(listOfTokens: List<Token>): String? =
        validListOfTokens.validate(listOfTokens)
            ?.let { rule.apply(it) }
}

class PrintScriptFormatter(
    private val validListOfTokensFormatterList: List<ValidListOfTokensFormatter<*>>,
    private val tokenListRule: TokenListSpacingRule
) : Formatter {
    override fun format(listOfTokens: List<Token>): String {
        val initial: String? = null
        return (
            validListOfTokensFormatterList.fold(initial) { acc, oneFormatter ->
                acc ?: oneFormatter.format(listOfTokens)
            } ?: tokenListRule.apply(listOfTokens)
            ).let { EnterAfterEndOfLine().apply(it) }
    }
}
