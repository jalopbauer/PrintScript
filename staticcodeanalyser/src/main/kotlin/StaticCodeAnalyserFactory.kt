

interface StaticCodeAnalyserFactory {
    fun build(configString: String): StaticCodeAnalyser?
}
class PrintScriptStaticCodeAnalyserFactory : StaticCodeAnalyserFactory {
    override fun build(configString: String): StaticCodeAnalyser {
        val list = listOf(
            RuleStaticCodeAnalyser(CheckVariableFactory().build(configString))
        )
        val newList = PrintlnParameterFactoryFactory().build(configString)
            ?.let { list + it } ?: list
        return PrintScriptStaticCodeAnalyser(newList)
    }
}
class PrintlnParameterFactoryFactory : StaticCodeAnalyserFactory {
    override fun build(configString: String): ValidListOfTokensStaticCodeAnalyser<*>? =
        PrintlnParameterFactory().build(configString)
            ?.let { ValidListOfTokensStaticCodeAnalyser(PrintlnValidListOfTokensBuilder(), it) }
}
