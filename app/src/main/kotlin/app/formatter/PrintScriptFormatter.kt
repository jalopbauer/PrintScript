package app.formatter

interface PrintScriptFormatter {
    fun format(
        nextChar: Char,
        states: PrintScriptFormatterStates

    ): PrintScriptFormatterStates

    fun handleLastState(states: PrintScriptFormatterStates): PrintScriptFormatterStates?
}
