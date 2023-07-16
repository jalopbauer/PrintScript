package app.sca

import lexer.lexerState.NoPreviousTokenDefinedLexerState
import java.io.InputStream

class Lint(private val printScriptStaticCodeAnalyser: PrintScriptStaticCodeAnalyser) {
    fun lint(inputStream: InputStream): String {
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
        return printScriptStaticCodeAnalyser.handleLastState(state)?.string ?: state.string
    }

    private fun getNextChar(inputStream: InputStream): Char? =
        inputStream.read().takeIf { it != -1 }?.toChar()
}
