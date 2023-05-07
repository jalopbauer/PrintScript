import lexer.LexerSentence
import java.io.InputStream

interface PrintScriptApp {
    fun interpret(inputStream: InputStream)
    fun format(inputStream: InputStream)
    fun lint(inputStream: InputStream)
}

class MyPrintScriptApp : PrintScriptApp {
    private val lexer = LexerSentence()
//    private val printScriptParser

    override fun interpret(inputStream: InputStream) {
        var read = inputStream.read()
        var list = ""
        var line = 0
        while (read != -1) {
            val char = read.toChar()
            list += char
            if (char == ';') {
                val buildTokenList = lexer.buildTokenList(Sentence(list, line))

                line += 1
            }
            read = inputStream.read()
        }
    }

    override fun format(inputStream: InputStream) {
        TODO("Not yet implemented")
    }

    override fun lint(inputStream: InputStream) {
        TODO("Not yet implemented")
    }
}
