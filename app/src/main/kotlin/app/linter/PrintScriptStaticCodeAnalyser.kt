package app.linter

interface PrintScriptStaticCodeAnalyser {
    fun format(
        nextChar: Char,
        states: PrintScriptStaticCodeAnalyserStates

    ): PrintScriptStaticCodeAnalyserStates?
}
