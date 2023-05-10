import token.Token
import token.VariableNameToken

interface Rule<T> {
    fun apply(listOfTokens: T): String?
}
interface ValidListOfTokensRule<T : ValidListOfTokens> : Rule<T>

class PrintlnParameterRule :
    ValidListOfTokensRule<PrintlnValidListOfTokens> {
    override fun apply(listOfTokens: PrintlnValidListOfTokens): String? =
        when (val printLnParameterValidListOfTokens = listOfTokens.printLnParameterValidListOfTokens) {
            is NumberLiteralParameter, is VariableParameter -> null
            is StringLiteralOrStringConcatValidListOfTokens ->
                if (printLnParameterValidListOfTokens.stringOrConcat.size == 1) {
                    null
                } else {
                    "StringConcat not valid"
                }
        }
}
interface VariableRule : Rule<Token>

class SnakeCaseRule : VariableRule {
    override fun apply(listOfTokens: Token): String? =
        when {
            listOfTokens is VariableNameToken &&
                !Regex("[a-z]+(?:_[a-z]+)*").matches(listOfTokens.value) -> "Variable ${listOfTokens.value} is not snake_case"
            else -> null
        }
}
class CamelCaseRule : VariableRule {
    override fun apply(listOfTokens: Token): String? =
        when {
            listOfTokens is VariableNameToken &&
                !Regex("[a-z]+(?:[A-Z][a-z]*)*").matches(listOfTokens.value) -> "Variable ${listOfTokens.value} is not camelCase"
            else -> null
        }
}
interface TokenListSpacingRule : Rule<List<Token>>

class CheckVariable(private val variableRule: VariableRule) : TokenListSpacingRule {
    override fun apply(listOfTokens: List<Token>): String? =
        listOfTokens.mapNotNull { variableRule.apply(it) }
            .let {
                if (it.isEmpty()) {
                    null
                } else {
                    it.joinToString("\n")
                }
            }
}
