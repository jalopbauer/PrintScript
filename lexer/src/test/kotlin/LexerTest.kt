import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.IntermediateLexerState
import lexer.lexerState.NoPreviousTokenDefinedLexerState
import lexer.lexerState.PreviousTokenDefinedLexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer
import lexer.tokenLexer.SecondVersionPrintScriptLexer
import lexer.tokenLexer.VersionPrintScriptLexer
import org.junit.jupiter.api.Test
import token.Token
class LexerTester {
    @Test
    fun firstVersionPrintScriptLexer() {
        ListTester(
            LexerTesterBuilder(),
            LexerExpectedValuesBuilder(FirstVersionPrintScriptLexer()),
            LexerTestFolderPathBuilder(8),
            "src/test/resources/",
            "tokenResult",
            "input"
        ).test()
    }

    @Test
    fun secondVersionPrintScriptLexer() {
        ListTester(
            LexerTesterBuilder(),
            LexerExpectedValuesBuilder(SecondVersionPrintScriptLexer()),
            LexerTestFolderPathBuilder(9),
            "src/test/resources/",
            "tokenResult",
            "input"
        ).test()
    }
}
class LexerTokenResultTransformer : Transformer<LexerTokenResult> {
    override fun to(from: String): LexerTokenResult =
        from.split(',', limit = 3).let {
                (tokenName, lineNumber, position) ->
            LexerTokenResult(tokenName.trim(), lineNumber.trim().toInt(), position.trim().toInt())
        }
}

data class LexerTokenResult(private val tokenName: String, private val lineNumber: Int, private val position: Int) :
    TestValue<Token> {
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

class LexerExpectedValuesBuilder(val firstVersionPrintScriptLexer: VersionPrintScriptLexer) : ExpectedValuesBuilder<Token> {
    override fun build(s: String): List<Token> {
        val initial: Pair<IntermediateLexerState, List<Token>> = Pair(NoPreviousTokenDefinedLexerState(), listOf())

        return s.fold(initial) { (state, tokens), nextChar ->
            val input = LexerInput(nextChar, state)
            NewTokenListLexer(firstVersionPrintScriptLexer).tokenize(input)
                .let {
                    when (it) {
                        is IntermediateLexerStateResponse -> Pair(it.intermediateLexerState, tokens)
                        is TokenFoundLexerStateResponse -> Pair(it.intermediateLexerState, tokens + it.token)
                    }
                }
        }.let { (state, tokens) ->
            when (state) {
                is PreviousTokenDefinedLexerState -> tokens + state.previousToken
                else -> tokens
            }
        }
    }
}
class LexerTestFolderPathBuilder(val int: Int) : TestFolderPathBuilder {
    override fun build(): List<String> = (1..int).map { it.toString() }
}
class LexerTesterBuilder : TesterBuilder<Token, Tester<Token, LexerTokenResult, LexerTokenResultTransformer>> {
    override fun build(expectedValues: List<Token>): Tester<Token, LexerTokenResult, LexerTokenResultTransformer> =
        Tester(CsvReader(LexerTokenResultTransformer()), expectedValues)
}
