package formatter

import validlistoftokens.AssignationValidListOfTokensBuilder
import validlistoftokens.DeclarationValidListOfTokensBuilder
import validlistoftokens.PrintlnValidListOfTokensBuilder

interface FormatterFactory {
    fun build(configString: String): Formatter?
}

class PrintScriptFormatterFactory : FormatterFactory {
    override fun build(configString: String): Formatter {
        val tokenListSpacingRule = TokenListSpacingRuleFactory().build(configString)
        val list: List<ValidListOfTokensFormatter<*>> = listOf()
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
    override fun build(configString: String): ValidListOfTokensFormatter<*>? =
        AssignationValidListOfTokensRuleFactory(tokenListSpacingRule).build(configString)
            ?.let { ValidListOfTokensFormatter(AssignationValidListOfTokensBuilder(), it) }
}

class DeclarationFormatterFactory : FormatterFactory {
    override fun build(configString: String): ValidListOfTokensFormatter<*>? =
        DeclarationValidListOfTokensRuleFactory().build(configString)
            ?.let { ValidListOfTokensFormatter(DeclarationValidListOfTokensBuilder(), it) }
}

class EnterBeforePrintlnFormatterFactory(private val tokenListSpacingRule: TokenListSpacingRule) : FormatterFactory {
    override fun build(configString: String): ValidListOfTokensFormatter<*>? =
        EnterBeforePrintlnRuleFactory(tokenListSpacingRule).build(configString)
            ?.let { ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), it) }
}
