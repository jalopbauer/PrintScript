import token.Token
import token.VariableLiteralToken

interface Rule<T> {
    fun apply(listOfTokens: T): String?
}
interface ValidListOfTokensRule<T : ValidListOfTokens> : Rule<T>

class PrintlnParameterRule(private val variableRule: VariableRule) :
    ValidListOfTokensRule<PrintlnValidListOfTokens> {
    override fun apply(listOfTokens: PrintlnValidListOfTokens): String? =
        when (val printLnParameterValidListOfTokens = listOfTokens.printLnParameterValidListOfTokens) {
            is NumberLiteralParameter -> null
            is VariableParameter -> variableRule.apply(printLnParameterValidListOfTokens.variableToken)
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
            listOfTokens is VariableLiteralToken &&
                Regex("[a-zA-Z]+(_[a-zA-Z]+)*").matches(listOfTokens.value) -> "Variable ${listOfTokens.value} is not snake-case"
            else -> null
        }
}
class CamelCaseRule : VariableRule {
    override fun apply(listOfTokens: Token): String? =
        when {
            listOfTokens is VariableLiteralToken &&
                Regex("/[a-z]+((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?/g").matches(listOfTokens.value) -> "Variable ${listOfTokens.value} is not camelCase"
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
                    it.joinToString { "\n" }
                }
            }
}
