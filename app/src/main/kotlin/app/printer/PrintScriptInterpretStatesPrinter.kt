package app.printer

import app.interpreter.PrintScriptInterpretStates

class PrintScriptInterpretStatesPrinter(private val func: (string: String) -> Unit = { println() }) : Printer<PrintScriptInterpretStates> {
    override fun print(t: PrintScriptInterpretStates): PrintScriptInterpretStates =
        t.copy(printScriptInterpreterState = PrintScriptInterpreterStatePrinter(func).print(t.printScriptInterpreterState))
}
