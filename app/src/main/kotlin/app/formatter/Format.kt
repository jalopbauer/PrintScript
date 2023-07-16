package app.formatter

import lexer.lexerState.NoPreviousTokenDefinedLexerState
import java.io.InputStream

class Format(private val printScriptFormatter: PrintScriptFormatter) {

    fun format(inputStream: InputStream): String {
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
        return printScriptFormatter.handleLastState(state)?.string ?: "Missing"
    }
    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()
}
