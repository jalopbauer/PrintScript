package staticcodeanalyser

import token.Token
import validlistoftokens.ValidListOfTokens
import validlistoftokens.ValidListOfTokensBuilder

interface StaticCodeAnalyser<T> {
    fun format(listOfTokens: List<Token>): T?
}

interface StaticCodeAnalyserString : StaticCodeAnalyser<String>

class ValidListOfTokensStaticCodeAnalyser<T : ValidListOfTokens>(
    private val validListOfTokens: ValidListOfTokensBuilder<T>,
    private val rule: Rule<T>
) : StaticCodeAnalyserString {
    override fun format(listOfTokens: List<Token>): String? =
        validListOfTokens.validate(listOfTokens)
            ?.let { rule.apply(it) }
}
class RuleStaticCodeAnalyser(
    private val rule: Rule<List<Token>>
) : StaticCodeAnalyserString {
    override fun format(listOfTokens: List<Token>): String? =
        rule.apply(listOfTokens)
}
class PrintScriptStaticCodeAnalyser(
    private val oneFormatterList: List<StaticCodeAnalyserString>
) : StaticCodeAnalyserString {
    override fun format(listOfTokens: List<Token>): String? =
        oneFormatterList.mapNotNull { it.format(listOfTokens) }
            .let {
                if (it.isEmpty()) null else it.joinToString("\n")
            }
}
