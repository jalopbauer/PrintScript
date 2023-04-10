import lexer.LexerImp
import org.junit.jupiter.api.Test
import token.Token
import java.io.File

class LexerTester {
    @Test
    fun test() {
        val lexerTokenResultTransformer = LexerTokenResultTransformer()
        val csvReader = CsvReader(lexerTokenResultTransformer)
        val lexerImp = LexerImp()
        (7..7).forEach { number ->
            val line = File("src/test/resources/$number/input").useLines { it.firstOrNull() }
            line?.let {
                val buildTokenList = lexerImp.buildTokenList(it)
                Tester(csvReader, buildTokenList).test("src/test/resources/$number/tokenResult")
            }
        }
    }
}
class LexerTokenResultTransformer : Transformer<LexerTokenResult> {
    override fun to(from: String): LexerTokenResult =
        from.split(',', limit = 3).let {
                (tokenName, lineNumber, position) ->
            LexerTokenResult(tokenName.trim(), lineNumber.trim().toInt(), position.trim().toInt())
        }
}

class LexerTokenResult(private val tokenName: String, private val lineNumber: Int, private val position: Int) : TestValue<Token> {
    override fun hasError(comparedTo: Token): String? {
        val errorMessages = listOfNotNull(
            tokenNameErrorString(comparedTo),
            lineNumberErrorString(comparedTo),
            positionErrorString(comparedTo)
        )
        return when (errorMessages) {
            listOf<String>() -> null
            else -> errorMessages.joinToString(prefix = "<", postfix = ">", separator = ", ")
        }
    }

    private fun tokenNameErrorString(comparedTo: Token): String? =
        if (tokenName != comparedTo.tokenName().toString()) {
            errorMessage(tokenName, comparedTo.tokenName().toString())
        } else {
            null
        }

    private fun lineNumberErrorString(comparedTo: Token): String? =
        if (lineNumber != comparedTo.lineNumber()) {
            errorMessage(lineNumber.toString(), comparedTo.lineNumber().toString())
        } else {
            null
        }

    private fun positionErrorString(comparedTo: Token): String? =
        if (position != comparedTo.position()) {
            errorMessage(position.toString(), comparedTo.position().toString())
        } else {
            null
        }
}
