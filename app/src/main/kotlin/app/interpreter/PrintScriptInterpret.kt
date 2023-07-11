package app.interpreter

interface PrintScriptInterpret {
    fun interpret(
        nextChar: Char,
        states: PrintScriptInterpretStates

    ): PrintScriptInterpretStates?

    fun handleLastState(states: PrintScriptInterpretStates): PrintScriptInterpretStates?
}
