package staticcodeanalyser

import token.Token
import validlistoftokens.PrintlnValidListOfTokens

interface RuleFactory<T> {
    fun build(configString: String): Rule<T>?
}

class PrintlnParameterFactory : RuleFactory<PrintlnValidListOfTokens> {
    override fun build(configString: String): PrintlnParameterRule? =
        when {
            configString.contains("allow-literals-or-variable-only") -> PrintlnParameterRule()
            else -> null
        }
}

class VariableCamelCaseDefaultFactory : RuleFactory<Token> {
    override fun build(configString: String): VariableRule =
        when {
            configString.contains("snake-case-variable") -> SnakeCaseRule()
            else -> CamelCaseRule()
        }
}

class CheckVariableFactory : RuleFactory<List<Token>> {
    override fun build(configString: String): Rule<List<Token>> =
        CheckVariable(VariableCamelCaseDefaultFactory().build(configString))
}
