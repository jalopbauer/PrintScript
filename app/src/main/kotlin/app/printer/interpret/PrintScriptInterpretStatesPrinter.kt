package app.printer.interpret

import app.interpreter.PrintScriptInterpretStates
import app.printer.Printer

class PrintScriptInterpretStatesPrinter(private val func: (string: String) -> Unit = { println() }) :
    Printer<PrintScriptInterpretStates> {
    override fun print(t: PrintScriptInterpretStates): PrintScriptInterpretStates =
        t.copy(printScriptInterpreterState = PrintScriptInterpreterStatePrinter(func).print(t.printScriptInterpreterState))
}
