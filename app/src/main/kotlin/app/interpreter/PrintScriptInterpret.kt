package app.interpreter

interface PrintScriptInterpret {
    fun interpret(
        nextChar: Char,
        states: PrintScriptInterpretStates

    ): PrintScriptInterpretStates?
}
