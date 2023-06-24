package app

import formatter.PrintScriptFormatterFactory
import interpreter.PrintScriptInterpreter
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.PrintScriptInterpreterStateI
import lexer.previousImplementation.LexerSentence
import lexer.previousImplementation.Sentence
import parser.PrintScriptTokenListParser
import staticcodeanalyser.PrintScriptStaticCodeAnalyserFactory
import token.Token
import java.io.InputStream

interface PrintScriptApp {
    fun interpret(inputStream: InputStream)
    fun format(inputStream: InputStream)
    fun lint(inputStream: InputStream)
}

class MyPrintScriptApp : PrintScriptApp {
    private val lexer = LexerSentence()
    private val parser = PrintScriptTokenListParser()
    private val interpreter = PrintScriptInterpreter()
    private var interpreterState: PrintScriptInterpreterState = PrintScriptInterpreterStateI()
    private val formatter = PrintScriptFormatterFactory()
        .build(
            """
            
            """.trimIndent()
        )
    private val sca = PrintScriptStaticCodeAnalyserFactory()
        .build(
            """
            
            """.trimIndent()
        )
    override fun interpret(inputStream: InputStream) =
        loop(
            inputStream
        ) { tokens ->
            parser.parse(tokens)
                ?.let { interpreter.interpret(it, interpreterState) }
                .let {
                    when (it) {
                        is PrintScriptInterpreterState -> {
                            interpreterState = it
                        }

                        is Error -> {
                            println(it)
                        }
                    }
                }
        }

    override fun format(inputStream: InputStream) =
        loop(inputStream) { formatter.format(it) }

    override fun lint(inputStream: InputStream) =
        loop(inputStream) { sca.format(it) }

    private fun loop(
        inputStream: InputStream,
        lambda: (tokens: List<Token>) -> Unit
    ) {
        var read = inputStream.read()
        var list = ""
        var line = 0
        while (read != -1) {
            val char = read.toChar()
            if (!(char == '\n' || char == '\r')) {
                list += char
                if (char == ';') {
                    lexer.tokenize(Sentence(list, line))
                        .let { lambda.invoke(it) }
                    line += 1
                    list = ""
                }
            }
            read = inputStream.read()
        }
    }
}
