package app
import app.formatter.Format
import app.formatter.PrintScriptFormatterStates
import app.interpreter.Interpret
import app.printer.Printer
import app.sca.PrintScriptStaticCodeAnalyser
import app.sca.PrintScriptStaticCodeAnalyserStates
import lexer.lexerState.NoPreviousTokenDefinedLexerState
import java.io.InputStream

class PrintScriptApp(
    private val interpret: Interpret,
    private val formatterStates: Printer<PrintScriptFormatterStates>,
    private val staticCodeAnalyserStatesPrinter: Printer<PrintScriptStaticCodeAnalyserStates>,
    private val format: Format,
    private val printScriptStaticCodeAnalyser: PrintScriptStaticCodeAnalyser
) {
    fun interpret(inputStream: InputStream) {
        interpret.interpret(inputStream)
    }

    fun format(inputStream: InputStream) {
        format.format(inputStream)
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
