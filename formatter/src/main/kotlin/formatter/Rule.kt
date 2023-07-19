package formatter

import token.AssignationToken
import token.DeclarationToken
import token.FalseLiteralToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.NumberTypeToken
import token.PrintlnToken
import token.ReadInputToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
import token.StringTypeToken
import token.SubToken
import token.SumToken
import token.Token
import token.VariableNameToken
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.BooleanLiteralParameter
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.NumberLiteralParameter
import validlistoftokens.OperationValidListOfConcatTokens
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.ReadInputValidListOfTokens
import validlistoftokens.StringLiteralOrStringConcatValidListOfTokens
import validlistoftokens.ValidListOfTokens
import validlistoftokens.VariableParameter

interface Rule<T> {
    fun apply(listOfTokens: T): String
}
interface ValidListOfTokensRule<T : ValidListOfTokens> : Rule<T>
interface DeclarationRule : ValidListOfTokensRule<DeclarationValidListOfTokens>
class AddSpaceBeforeAndAfterDeclaration : DeclarationRule {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.variable.value} : ${TokenToString().apply(listOfTokens.type)};"
}

class AddSpaceBeforeDeclaration : DeclarationRule {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.variable.value} :${TokenToString().apply(listOfTokens.type)};"
}

class AddSpaceAfterDeclaration : DeclarationRule {
    override fun apply(listOfTokens: DeclarationValidListOfTokens): String =
        "let ${listOfTokens.variable.value}: ${TokenToString().apply(listOfTokens.type)};"
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
                is BooleanLiteralParameter -> listOf(printlnValidParameter.booleanLiteralToken)
                is ReadInputValidListOfTokens -> listOf(printlnValidParameter.readInputToken, printlnValidParameter.leftParenthesisToken, printlnValidParameter.stringLiteralToken, printlnValidParameter.rightParenthesisToken)
                is OperationValidListOfConcatTokens -> printlnValidParameter.operationConcat
            }
        ).padStart(amount, '\n') + ");"
}

interface TokenListSpacingRule : Rule<List<Token>>
class OneSpaceBetweenEveryToken : TokenListSpacingRule {
    override fun apply(listOfTokens: List<Token>): String =
        listOfTokens.joinToString(separator = " ") {
            TokenToString().apply(it)
        }
}

class PreviousSpacingBetweenEveryToken : TokenListSpacingRule {
    override fun apply(listOfTokens: List<Token>): String =

        listOfTokens.fold(Pair("", 0)) { (string, lastIndex), token ->
            val diff = token.position() - lastIndex
            val tokenValueInStringForm = TokenToString().apply(token)
            val newString = string + tokenValueInStringForm.padStart(diff, ' ')

            val newLastIndex = newString.length
            Pair(newString, newLastIndex)
        }.first
}

class EnterAfterEndOfLine : Rule<String> {
    override fun apply(listOfTokens: String): String =
        listOfTokens
}

class TokenToString : Rule<Token> {
    override fun apply(listOfTokens: Token): String =
        when (listOfTokens) {
            is StringLiteralToken -> "\"${listOfTokens.value}\""
            is IntNumberLiteralToken -> listOfTokens.value.toString()
            is VariableNameToken -> listOfTokens.value
            is AssignationToken -> "="
            is DeclarationToken -> ":"
            is FalseLiteralToken -> "false"
            is SumToken -> "+"
            is SubToken -> "-"
            is ReadInputToken -> "readInput"
            is NumberTypeToken -> "number"
            is StringTypeToken -> "string"
            is RightParenthesisToken -> ")"
            is LeftParenthesisToken -> "("
            is PrintlnToken -> "println"
            is SemicolonToken -> ";"
            else -> "error"
        }
}
