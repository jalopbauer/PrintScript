import token.Token

interface RuleFactory<T> {
    fun build(configString: String): Rule<T>?
}

class PrintlnParameterFactory(private val variableRule: VariableRule) : RuleFactory<PrintlnValidListOfTokens> {
    override fun build(configString: String): PrintlnParameterRule? =
        when {
            configString.contains("allow-literals-or-variable-only") -> PrintlnParameterRule(variableRule)
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
