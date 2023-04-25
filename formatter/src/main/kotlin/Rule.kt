import token.Token

interface Rule<T> {
    fun apply(listOfTokens: T): String
}
interface ValidListOfTokensRule<T : ValidListOfTokens> : Rule<T>
class AddSpaceBeforeAndAfterDeclaration : ValidListOfTokensRule<DeclarationValidListOfTokens> {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.type.tokenName()} : ${listOfTokens.variable.value}"
}

class AddSpaceBeforeDeclaration : ValidListOfTokensRule<DeclarationValidListOfTokens> {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.type.tokenName()} :${listOfTokens.variable.value}"
}

class AddSpaceAfterDeclaration : ValidListOfTokensRule<DeclarationValidListOfTokens> {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.type.tokenName()}: ${listOfTokens.variable.value}"
}

class AddSpaceBeforeAndAfterAssignation(private val tokenListSpacingRule: TokenListSpacingRule) :
    ValidListOfTokensRule<AssignationValidListOfTokens> {
    override fun apply(listOfTokens: AssignationValidListOfTokens): String =
        "${listOfTokens.variable.value} = ${tokenListSpacingRule.apply(listOfTokens.content)}"
}

class EnterBeforePrintln(private val tokenListSpacingRule: TokenListSpacingRule, val amount: Int) :
    ValidListOfTokensRule<PrintlnValidListOfTokens> {
    override fun apply(listOfTokens: PrintlnValidListOfTokens): String {
        val printlnValidParameter = listOfTokens.printLnParameterValidListOfTokens
        return tokenListSpacingRule.apply(
            when (printlnValidParameter) {
                is NumberLiteralParameter -> listOf(printlnValidParameter.numberLiteralToken)
                is StringLiteralOrStringConcatValidListOfTokens -> printlnValidParameter.stringOrConcat
                is VariableParameter -> listOf(printlnValidParameter.variableToken)
            }
        ).padStart(amount, '\n')
    }
}

interface TokenListSpacingRule : Rule<List<Token>>
class OneSpaceBetweenEveryToken : TokenListSpacingRule {
    override fun apply(listOfTokens: List<Token>): String =
        listOfTokens.joinToString(separator = " ")
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
        if (listOfTokens.last() == ';') {
            listOfTokens + '\n'
        } else {
            listOfTokens
        }
}
