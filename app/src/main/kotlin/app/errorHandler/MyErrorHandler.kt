package app.errorHandler

import app.interpreter.PrintScriptInterpretStates

class MyErrorHandler : ErrorHandler<PrintScriptInterpretStates> {
    override fun handle(message: String, state: PrintScriptInterpretStates): PrintScriptInterpretStates {
        println(message)
        return state
    }
}
