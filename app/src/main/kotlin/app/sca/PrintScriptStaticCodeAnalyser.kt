package app.sca

interface PrintScriptStaticCodeAnalyser {
    fun format(
        nextChar: Char,
        states: PrintScriptStaticCodeAnalyserStates

    ): PrintScriptStaticCodeAnalyserStates?
}
