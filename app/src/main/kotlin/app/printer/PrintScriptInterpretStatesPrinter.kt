package app.printer

import app.interpreter.PrintScriptInterpretStates

class PrintScriptInterpretStatesPrinter : Printer<PrintScriptInterpretStates> {
    override fun print(t: PrintScriptInterpretStates): PrintScriptInterpretStates =
        t.copy(printScriptInterpreterState = PrintScriptInterpreterStatePrinter().print(t.printScriptInterpreterState))
}
