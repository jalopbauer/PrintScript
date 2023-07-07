package app.formatter

interface PrintScriptFormatter {
    fun interpret(
        nextChar: Char,
        states: PrintScriptFormatterStates

    ): PrintScriptFormatterStates?
}
