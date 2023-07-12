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

class PrintScriptApp(
    private val interpretStatesPrinter: Printer<PrintScriptInterpretStates> = PrintScriptInterpretStatesPrinter(),
    private val formatterStates: Printer<PrintScriptFormatterStates>,
    private val staticCodeAnalyserStatesPrinter: Printer<PrintScriptStaticCodeAnalyserStates>,
    private val printScriptInterpret: PrintScriptInterpret,
    private val printScriptFormatter: PrintScriptFormatter,
    private val printScriptStaticCodeAnalyser: PrintScriptStaticCodeAnalyser
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

    fun format(inputStream: InputStream) {
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
        printScriptFormatter.handleLastState(state)?.let { formatterStates.print(it) }
    }

    fun lint(inputStream: InputStream) {
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
        printScriptStaticCodeAnalyser.handleLastState(state)?.let { staticCodeAnalyserStatesPrinter.print(it) } ?: println("failed")
    }

    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()
}
