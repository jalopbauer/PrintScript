package app.interpreter

import app.printer.Printer
import app.printer.interpret.PrintScriptInterpretStatesPrinter
import interpreter.state.PrintScriptInterpreterStateI
import lexer.lexerState.NoPreviousTokenDefinedLexerState
import parser.parserState.RegularParserState
import java.io.InputStream

class Interpret(
    private val interpretStatesPrinter: Printer<PrintScriptInterpretStates> = PrintScriptInterpretStatesPrinter(),
    private val printScriptInterpret: PrintScriptInterpret
) {
    fun interpret(inputStream: InputStream) {
        var state = PrintScriptInterpretStates(
            NoPreviousTokenDefinedLexerState(),
            RegularParserState(),
            PrintScriptInterpreterStateI()
        )
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar ->
                    printScriptInterpret.interpret(nextChar, state)
                        ?.let { interpretStatesPrinter.print(it) }
                        ?.let { state = it }
                } ?: break
        }
        printScriptInterpret.handleLastState(state)
            ?.let { interpretStatesPrinter.print(it) }
    }

    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()
}
