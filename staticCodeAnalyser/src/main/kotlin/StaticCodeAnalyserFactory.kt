interface StaticCodeAnalyserFactory {
    fun build(configString: String): StaticCodeAnalyser?
}
class PrintScriptStaticCodeAnalyserFactory() : StaticCodeAnalyserFactory {
    override fun build(configString: String): StaticCodeAnalyser {
        val build = VariableCamelCaseDefaultFactory().build(configString)
        val list: List<OneStaticCodeAnalyser<*>> = listOf()
        val newList = PrintlnParameterFactoryFactory(build).build(configString)
            ?.let { list + it } ?: list
        return PrintScriptStaticCodeAnalyser(newList)
    }
}
class PrintlnParameterFactoryFactory(private val variableRule: VariableRule) : StaticCodeAnalyserFactory {
    override fun build(configString: String): OneStaticCodeAnalyser<*>? =
        PrintlnParameterFactory(variableRule).build(configString)
            ?.let { OneStaticCodeAnalyser(PrintlnValidListOfTokensBuilder(), it) }
}
