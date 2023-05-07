import org.junit.jupiter.api.Test
import token.ClosedBracketToken
import token.OpenBracketToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.TokenWithoutValue
import token.VariableLiteralToken

class FormatterTest {

    @Test
    fun declarationFormatterSpacingBeforeAndAfterTest() {
        val tokens: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 4),
            TokenWithoutValue(TokenName.DECLARATION, 0, 9),
            TokenWithoutValue(TokenName.NUMBER_TYPE, 0, 11),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 17)
        )

        val formatterBoth = PrintScriptFormatterFactory().build(
            """
                declaration-spacing-both
            """.trimIndent()
        )
        val sentenceBoth = formatterBoth.format(tokens)
        assert(sentenceBoth == "let test : number;")
    }

    @Test
    fun declarationFormatterSpacingAfterTest() {
        val tokens: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 4),
            TokenWithoutValue(TokenName.DECLARATION, 0, 9),
            TokenWithoutValue(TokenName.STRING_TYPE, 0, 11),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 17)
        )
        val formatterAfter = PrintScriptFormatterFactory().build(
            """
                    declaration-spacing-after
            """.trimIndent()
        )
        val sentenceAfter = formatterAfter.format(tokens)
        assert(sentenceAfter == "let test: string;")
    }

    @Test
    fun declarationFormatterSpacingBeforeTest() {
        val tokens: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 4),
            TokenWithoutValue(TokenName.DECLARATION, 0, 9),
            TokenWithoutValue(TokenName.NUMBER_TYPE, 0, 11),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 17)
        )
        val formatterBefore = PrintScriptFormatterFactory().build(
            """
                    declaration-spacing-before
            """.trimIndent()
        )
        val sentenceBefore = formatterBefore.format(tokens)
        assert(sentenceBefore == "let test :number;")
    }

    @Test
    fun assignationFormatterWorks() {
        val tokens = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            StringLiteralToken("Hello World", 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val formatter = PrintScriptFormatterFactory().build(
            """
                assignation-spacing-both
            """.trimIndent()
        )
        val sentence = formatter.format(tokens)
        val correctSentence = "test = " + '"' + "Hello World" + '"' + ";"
        assert(sentence == correctSentence)
    }

    @Test
    fun printFormatterWorks() {
        val tokens = listOf(
            TokenWithoutValue(TokenName.PRINT, 0, 0),
            OpenBracketToken(TokenName.LEFT_PARENTHESIS, 0, 0),
            StringLiteralToken("Hello World", 0, 0),
            ClosedBracketToken(TokenName.RIGHT_PARENTHESIS, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(" + '"' + "Hello World" + '"' + ");"
        assert(sentence == sentenceCorrect)
    }
}
