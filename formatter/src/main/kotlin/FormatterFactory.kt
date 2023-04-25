interface FormatterFactory {
    fun build(configString: String): Formatter?
}

class PrintScriptFormatterFactory() : FormatterFactory {
    override fun build(configString: String): Formatter {
        val tokenListSpacingRule = TokenListSpacingRuleFactory().build(configString)
        val list: List<OneFormatter<*>> = listOf()
        val newList = AssignationFormatterFactory(tokenListSpacingRule).build(configString)
            ?.let { list + it } ?: list
        val newNewList = DeclarationFormatterFactory().build(configString)
            ?.let { newList + it } ?: newList
        val newNewNewList = EnterBeforePrintlnFormatterFactory(tokenListSpacingRule).build(configString)
            ?.let { newNewList + it } ?: newNewList
        return PrintScriptFormatter(newNewNewList, tokenListSpacingRule)
    }
}

class AssignationFormatterFactory(private val tokenListSpacingRule: TokenListSpacingRule) : FormatterFactory {
    override fun build(configString: String): OneFormatter<*>? =
        AssignationValidListOfTokensRuleFactory(tokenListSpacingRule).build(configString)
            ?.let { OneFormatter(AssignationValidListOfTokensBuilder(), it) }
}

class DeclarationFormatterFactory : FormatterFactory {
    override fun build(configString: String): OneFormatter<*>? =
        DeclarationValidListOfTokensRuleFactory().build(configString)
            ?.let { OneFormatter(DeclarationValidListOfTokensBuilder(), it) }
}

class EnterBeforePrintlnFormatterFactory(private val tokenListSpacingRule: TokenListSpacingRule) : FormatterFactory {
    override fun build(configString: String): OneFormatter<*>? =
        EnterBeforePrintlnRuleFactory(tokenListSpacingRule).build(configString)
            ?.let { OneFormatter(PrintlnValidListOfTokensBuilder(), it) }
}
