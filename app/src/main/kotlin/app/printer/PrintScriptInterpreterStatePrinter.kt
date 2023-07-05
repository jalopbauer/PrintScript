package app.printer

import interpreter.state.PrintScriptInterpreterState

class PrintScriptInterpreterStatePrinter : Printer<PrintScriptInterpreterState> {
    override fun print(t: PrintScriptInterpreterState): PrintScriptInterpreterState =
        t.print()
            .let { (string, postPrintState) ->
                string?.let { println(string) }
                postPrintState
            }
}
