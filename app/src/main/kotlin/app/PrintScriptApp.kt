package app
import app.formatter.PrintScriptFormatter
import app.formatter.PrintScriptFormatterStates
import app.interpreter.PrintScriptInterpret
import app.interpreter.PrintScriptInterpretStates
import app.printer.PrintScriptInterpretStatesPrinter
import app.printer.Printer
import app.sca.PrintScriptStaticCodeAnalyser
import app.sca.PrintScriptStaticCodeAnalyserStates
import interpreter.state.PrintScriptInterpreterStateI
import lexer.lexerState.NoPreviousTokenDefinedLexerState
import parser.parserState.RegularParserState
import java.io.InputStream

interface PrintScriptApp {
    fun interpret(inputStream: InputStream)
    fun format(inputStream: InputStream)
    fun lint(inputStream: InputStream)
}

class MyPrintScriptApp(
    private val printScriptInterpretStatesPrinter: Printer<PrintScriptInterpretStates> = PrintScriptInterpretStatesPrinter(),
    private val printScriptInterpret: PrintScriptInterpret,
    private val printScriptFormatter: PrintScriptFormatter,
    private val printScriptStaticCodeAnalyser: PrintScriptStaticCodeAnalyser
) : PrintScriptApp {
    override fun interpret(inputStream: InputStream) {
        var state = PrintScriptInterpretStates(
            NoPreviousTokenDefinedLexerState(),
            RegularParserState(),
            PrintScriptInterpreterStateI()
        )
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar ->
                    printScriptInterpret.interpret(nextChar, state)
                        ?.let { printScriptInterpretStatesPrinter.print(it) }
                        ?.let { state = it }
                } ?: break
        }
        printScriptInterpret.handleLastState(state)
            ?.let { printScriptInterpretStatesPrinter.print(it) }
    }

    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()

    override fun format(inputStream: InputStream) {
        var state = PrintScriptFormatterStates(
            NoPreviousTokenDefinedLexerState(),
            listOf(),
            ""
        )
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar -> state = printScriptFormatter.format(nextChar, state) }
                ?: break
        }
        printScriptFormatter.handleLastState(state)?.let { println(it.string) }
    }

    override fun lint(inputStream: InputStream) {
        var state = PrintScriptStaticCodeAnalyserStates(
            NoPreviousTokenDefinedLexerState(),
            listOf(),
            ""
        )
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar -> state = printScriptStaticCodeAnalyser.format(nextChar, state) }
                ?: break
        }
        printScriptStaticCodeAnalyser.handleLastState(state)?.let { println(it.string) } ?: println("failed")
    }
}
