package app
import app.interpreter.PrintScriptInterpetI
import app.interpreter.PrintScriptInterpretStates
import app.printer.PrintScriptInterpretStatesPrinter
import interpreter.state.PrintScriptInterpreterStateI
import lexer.lexerState.NoPreviousTokenDefinedLexerState
import parser.parserState.ParserState
import java.io.InputStream

interface PrintScriptApp {
    fun interpret(inputStream: InputStream)
    fun format(inputStream: InputStream)
    fun lint(inputStream: InputStream)
}

class MyPrintScriptApp : PrintScriptApp {
    override fun interpret(inputStream: InputStream) {
        var state = PrintScriptInterpretStates(
            NoPreviousTokenDefinedLexerState(),
            ParserState(),
            PrintScriptInterpreterStateI()
        )
        val printScriptInterpetI = PrintScriptInterpetI()
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar ->
                    printScriptInterpetI.interpret(nextChar, state)
                        ?.let { PrintScriptInterpretStatesPrinter().print(it) }
                        ?.let { state = it }
                } ?: break
        }
        printScriptInterpetI.handleLastState(state)
            ?.let { PrintScriptInterpretStatesPrinter().print(it) }
    }

    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()

    override fun format(inputStream: InputStream) {
        TODO("Not yet implemented")
    }

    override fun lint(inputStream: InputStream) {
        TODO("Not yet implemented")
    }
}
