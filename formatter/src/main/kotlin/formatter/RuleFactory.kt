package formatter

import token.Token
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.PrintlnValidListOfTokens

interface RuleFactory<T> {
    fun build(configString: String): Rule<T>?
}

class DeclarationValidListOfTokensRuleFactory : RuleFactory<DeclarationValidListOfTokens> {
    override fun build(configString: String): Rule<DeclarationValidListOfTokens>? =
        when {
            configString.contains("declaration-spacing-before") -> AddSpaceBeforeDeclaration()
            configString.contains("declaration-spacing-after") -> AddSpaceAfterDeclaration()
            configString.contains("declaration-spacing-both") -> AddSpaceBeforeAndAfterDeclaration()
            else -> null
        }
}

class AssignationValidListOfTokensRuleFactory(private val tokenListSpacingRule: TokenListSpacingRule) :
    RuleFactory<AssignationValidListOfTokens> {
    override fun build(configString: String): Rule<AssignationValidListOfTokens>? =
        when (configString) {
            "assignation-spacing-both" -> AddSpaceBeforeAndAfterAssignation(tokenListSpacingRule)
            else -> null
        }
}

class TokenListSpacingRuleFactory : RuleFactory<List<Token>> {
    override fun build(configString: String): TokenListSpacingRule =
        when {
            configString.contains("no-conventional") -> PreviousSpacingBetweenEveryToken()
            else -> OneSpaceBetweenEveryToken()
        }
}

class EnterBeforePrintlnRuleFactory(private val tokenListSpacingRule: TokenListSpacingRule) :
    RuleFactory<PrintlnValidListOfTokens> {
    override fun build(configString: String): EnterBeforePrintln? =
        when {
            configString.contains("enter-before-println-0") -> EnterBeforePrintln(tokenListSpacingRule, 0)
            configString.contains("enter-before-println-1") -> EnterBeforePrintln(tokenListSpacingRule, 1)
            configString.contains("enter-before-println-2") -> EnterBeforePrintln(tokenListSpacingRule, 2)
            else -> null
        }
}
