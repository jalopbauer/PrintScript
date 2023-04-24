import token.Token

interface StaticCodeAnalyser {
    fun format(listOfTokens: List<Token>): String?
}

class OneStaticCodeAnalyser<T : ValidListOfTokens>(
    private val validListOfTokens: ValidListOfTokensBuilder<T>,
    private val rule: Rule<T>
) : StaticCodeAnalyser {
    override fun format(listOfTokens: List<Token>): String? =
        validListOfTokens.validate(listOfTokens)
            ?.let { rule.apply(it) }
}

class PrintScriptStaticCodeAnalyser(
    private val oneFormatterList: List<OneStaticCodeAnalyser<*>>
) : StaticCodeAnalyser {
    override fun format(listOfTokens: List<Token>): String? {
        val initial: String? = null
        return oneFormatterList.fold(initial) { acc, oneFormatter ->
            acc ?: oneFormatter.format(listOfTokens)
        }
    }
}
