package app.printer.interpret

import app.printer.Printer
import interpreter.state.PrintScriptInterpreterState

class PrintScriptInterpreterStatePrinter(val func: (string: String) -> Unit = { println() }) :
    Printer<PrintScriptInterpreterState> {
    override fun print(t: PrintScriptInterpreterState): PrintScriptInterpreterState =
        t.print()
            .let { (string, postPrintState) ->
                string.map { func(it) }
                postPrintState
            }
}
