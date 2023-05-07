import token.DoubleNumberLiteralToken
import token.IntNumberLiteralToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.VariableLiteralToken

interface Rule<T> {
    fun apply(listOfTokens: T): String
}
interface ValidListOfTokensRule<T : ValidListOfTokens> : Rule<T>
interface DeclarationRule : ValidListOfTokensRule<DeclarationValidListOfTokens> {
    fun getType(tokenName: TokenName): String =
        when (tokenName) {
            TokenName.STRING_TYPE -> "string"
            TokenName.NUMBER_TYPE -> "number"
            else -> "error"
        }
}
class AddSpaceBeforeAndAfterDeclaration : DeclarationRule {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.variable.value} : ${getType(listOfTokens.type.tokenName())};"
}

class AddSpaceBeforeDeclaration : DeclarationRule {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.variable.value} :${getType(listOfTokens.type.tokenName())};"
}

class AddSpaceAfterDeclaration : DeclarationRule {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.variable.value}: ${getType(listOfTokens.type.tokenName())};"
}

class AddSpaceBeforeAndAfterAssignation(private val tokenListSpacingRule: TokenListSpacingRule) :
    ValidListOfTokensRule<AssignationValidListOfTokens> {
    override fun apply(listOfTokens: AssignationValidListOfTokens): String =
        "${listOfTokens.variable.value} = ${tokenListSpacingRule.apply(listOfTokens.content)};"
}

class EnterBeforePrintln(private val tokenListSpacingRule: TokenListSpacingRule, val amount: Int) :
    ValidListOfTokensRule<PrintlnValidListOfTokens> {
    override fun apply(listOfTokens: PrintlnValidListOfTokens): String =
        "println(" + tokenListSpacingRule.apply(
            when (val printlnValidParameter = listOfTokens.printLnParameterValidListOfTokens) {
                is NumberLiteralParameter -> listOf(printlnValidParameter.numberLiteralToken)
                is StringLiteralOrStringConcatValidListOfTokens -> printlnValidParameter.stringOrConcat
                is VariableParameter -> listOf(printlnValidParameter.variableToken)
            }
        ).padStart(amount, '\n') + ");"
}

interface TokenListSpacingRule : Rule<List<Token>>
class OneSpaceBetweenEveryToken : TokenListSpacingRule {
    override fun apply(listOfTokens: List<Token>): String =
        listOfTokens.joinToString(separator = " ") {
            when (it) {
                is StringLiteralToken -> "\"${it.value}\""
                is IntNumberLiteralToken -> it.value.toString()
                is DoubleNumberLiteralToken -> it.value.toString()
                is VariableLiteralToken -> it.value
                else -> it.toString()
            }
        }
}

class PreviousSpacingBetweenEveryToken : TokenListSpacingRule {
    override fun apply(listOfTokens: List<Token>): String =
        listOfTokens.drop(1).fold(Pair("", 0)) { (string, lastIndex), token ->
            val tokenValueInStringForm = token.toString()
            val newString = string + tokenValueInStringForm.padStart(token.position() - lastIndex, ' ')
            val newLastIndex = lastIndex + tokenValueInStringForm.length
            Pair(newString, newLastIndex)
        }.first
}

class EnterAfterEndOfLine : Rule<String> {
    override fun apply(listOfTokens: String): String =
        listOfTokens + '\n'
}
