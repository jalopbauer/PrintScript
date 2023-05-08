import token.Token

interface StaticCodeAnalyser {
    fun format(listOfTokens: List<Token>): String?
}

class ValidListOfTokensStaticCodeAnalyser<T : ValidListOfTokens>(
    private val validListOfTokens: ValidListOfTokensBuilder<T>,
    private val rule: Rule<T>
) : StaticCodeAnalyser {
    override fun format(listOfTokens: List<Token>): String? =
        validListOfTokens.validate(listOfTokens)
            ?.let { rule.apply(it) }
}
class RuleStaticCodeAnalyser(
    private val rule: Rule<List<Token>>
) : StaticCodeAnalyser {
    override fun format(listOfTokens: List<Token>): String? =
        rule.apply(listOfTokens)
}
class PrintScriptStaticCodeAnalyser(
    private val oneFormatterList: List<StaticCodeAnalyser>
) : StaticCodeAnalyser {
    override fun format(listOfTokens: List<Token>): String? =
        oneFormatterList.mapNotNull { it.format(listOfTokens) }
            .let {
                if (it.isEmpty()) null else it.joinToString("\n")
            }
}
