import lexer.LexerSentence
import state.PrintScriptInterpreterState
import state.PrintScriptInterpreterStateI
import token.Token
import java.io.InputStream

interface PrintScriptApp {
    fun interpret(inputStream: InputStream)
    fun format(inputStream: InputStream)
    fun lint(inputStream: InputStream)
}

class MyPrintScriptApp : PrintScriptApp {
    private val lexer = LexerSentence()
    private val parser = PrintScriptParser()
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
                            println(it.message)
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
            list += char
            if (char == ';') {
                lexer.buildTokenList(Sentence(list, line))
                    .let { lambda.invoke(it) }
                line += 1
            }
            read = inputStream.read()
        }
    }
}
