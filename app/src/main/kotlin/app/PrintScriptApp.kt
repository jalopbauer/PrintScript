package app
import app.formatter.PrintScriptFormatterI
import app.formatter.PrintScriptFormatterStates
import app.interpreter.PrintScriptInterpetI
import app.interpreter.PrintScriptInterpretStates
import app.printer.PrintScriptInterpretStatesPrinter
import app.printer.Printer
import app.sca.PrintScriptStaticCodeAnalyserI
import app.sca.PrintScriptStaticCodeAnalyserStates
import formatter.PrintScriptFormatterFactory
import interpreter.state.PrintScriptInterpreterStateI
import lexer.lexerState.NoPreviousTokenDefinedLexerState
import parser.parserState.ParserState
import staticcodeanalyser.PrintScriptStaticCodeAnalyserFactory
import java.io.InputStream

interface PrintScriptApp {
    fun interpret(inputStream: InputStream)
    fun format(inputStream: InputStream)
    fun lint(inputStream: InputStream)
}

class MyPrintScriptApp(private val printScriptInterpretStatesPrinter: Printer<PrintScriptInterpretStates> = PrintScriptInterpretStatesPrinter()) : PrintScriptApp {
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
                        ?.let { printScriptInterpretStatesPrinter.print(it) }
                        ?.let { state = it }
                } ?: break
        }
        printScriptInterpetI.handleLastState(state)
            ?.let { printScriptInterpretStatesPrinter.print(it) }
    }

    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()

    override fun format(inputStream: InputStream) {
        val printScriptFormatter = PrintScriptFormatterFactory().build("declaration-spacing-both assignation-spacing-both")
        val printScriptFormatterI = PrintScriptFormatterI(printScriptFormatter)

        var state = PrintScriptFormatterStates(
            NoPreviousTokenDefinedLexerState(),
            listOf(),
            ""
        )
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar -> state = printScriptFormatterI.format(nextChar, state) }
                ?: break
        }
        printScriptFormatterI.handleLastState(state)?.let { println(it.string) }
    }

    override fun lint(inputStream: InputStream) {
        val printScriptFormatter = PrintScriptStaticCodeAnalyserFactory().build("allow-literals-or-variable-only")
        val printScriptFormatterI = PrintScriptStaticCodeAnalyserI(printScriptFormatter)

        var state = PrintScriptStaticCodeAnalyserStates(
            NoPreviousTokenDefinedLexerState(),
            listOf(),
            ""
        )
        while (true) {
            getNextChar(inputStream)
                ?.let { nextChar -> state = printScriptFormatterI.format(nextChar, state) }
                ?: break
        }
        printScriptFormatterI.handleLastState(state)?.let { println(it.string) } ?: println("failed")
    }
}
