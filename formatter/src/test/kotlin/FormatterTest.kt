import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.DeclarationToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.LetToken
import token.NumberTypeToken
import token.PrintToken
import token.RightParenthesisToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.TokenWithoutValue
import token.VariableLiteralToken
class FormatterTest {

    @Test
    fun declarationFormatterSpacingBeforeAndAfterTest() {
        val tokens: List<Token> = listOf(
            LetToken(0, 0),
            VariableLiteralToken("test", 0, 4),
            DeclarationToken(0, 9),
            NumberTypeToken(0, 11),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 17)
        )

        val formatterBoth = PrintScriptFormatterFactory().build(
            """
                declaration-spacing-both
            """.trimIndent()
        )
        val sentenceBoth = formatterBoth.format(tokens)
        assert(sentenceBoth == "let test : number;\n")
    }

    @Test
    fun declarationFormatterSpacingAfterTest() {
        val tokens: List<Token> = listOf(
            LetToken(0, 0),
            VariableLiteralToken("test", 0, 4),
            DeclarationToken(0, 9),
            TokenWithoutValue(TokenName.STRING_TYPE, 0, 11),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 17)
        )
        val formatterAfter = PrintScriptFormatterFactory().build(
            """
                    declaration-spacing-after
            """.trimIndent()
        )
        val sentenceAfter = formatterAfter.format(tokens)
        assert(sentenceAfter == "let test: string;\n")
    }

    @Test
    fun declarationFormatterSpacingBeforeTest() {
        val tokens: List<Token> = listOf(
            LetToken(0, 0),
            VariableLiteralToken("test", 0, 4),
            DeclarationToken(0, 9),
            NumberTypeToken(0, 11),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 17)
        )
        val formatterBefore = PrintScriptFormatterFactory().build(
            """
                    declaration-spacing-before
            """.trimIndent()
        )
        val sentenceBefore = formatterBefore.format(tokens)
        assert(sentenceBefore == "let test :number;\n")
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
        val correctSentence = "test = " + '"' + "Hello World" + '"' + ";\n"
        assert(sentence == correctSentence)
    }

    @Test
    fun printStringParameterFormatter() {
        val tokens = listOf(
            PrintToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            RightParenthesisToken(0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(\"Hello World\");"
        assert(sentence == sentenceCorrect)
    }

    @Test
    fun printNumberParameterFormatter() {
        val tokens = listOf(
            PrintToken(0, 0),
            LeftParenthesisToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            RightParenthesisToken(0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(1);"
        assert(sentence == sentenceCorrect)
    }

    @Test
    fun printVariableParameterFormatter() {
        val tokens = listOf(
            PrintToken(0, 0),
            LeftParenthesisToken(0, 0),
            VariableLiteralToken("HelloWorld", 0, 0),
            RightParenthesisToken(0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(HelloWorld);"
        assert(sentence == sentenceCorrect)
    }

    @Test
    fun oneSpaceBetweenEveryToken() {
        val tokens = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            StringLiteralToken("Hello World", 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val formatter = PrintScriptFormatterFactory().build("")
        val sentence = formatter.format(tokens)
        val correctSentence = "test = \"Hello World\" ;\n"
        assertEquals(correctSentence, sentence)
    }

    @Test
    fun previousSpacingBetweenEveryToken() {
        val tokens = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 4),
            StringLiteralToken("Hello World", 0, 5),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 18)
        )
        val formatter = PrintScriptFormatterFactory().build("no-conventional")
        val sentence = formatter.format(tokens)
        val correctSentence = "test=\"Hello World\";\n"
        assertEquals(correctSentence, sentence)
    }
}
