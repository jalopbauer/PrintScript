import formatter.EnterBeforePrintln
import formatter.OneSpaceBetweenEveryToken
import formatter.PrintScriptFormatterFactory
import formatter.ValidListOfTokensFormatter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.AssignationToken
import token.DeclarationToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.LetToken
import token.NumberTypeToken
import token.PrintlnToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
import token.StringTypeToken
import token.Token
import token.VariableNameToken
import validlistoftokens.PrintlnValidListOfTokensBuilder

class FormatterTest {

    @Test
    fun declarationFormatterSpacingBeforeAndAfterTest() {
        val tokens: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 4),
            DeclarationToken(0, 9),
            NumberTypeToken(0, 11),
            SemicolonToken(0, 17)
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
            LetToken(0, 0),
            VariableNameToken("test", 0, 4),
            DeclarationToken(0, 9),
            StringTypeToken(0, 11),
            SemicolonToken(0, 17)
        )
        val formatterAfter = PrintScriptFormatterFactory().build(
            """
                    declaration-spacing-after
            """.trimIndent()
        )
        val sentenceAfter = formatterAfter.format(tokens)
        println(sentenceAfter)
        assert(sentenceAfter == "let test: string;")
    }

    @Test
    fun declarationFormatterSpacingBeforeTest() {
        val tokens: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 4),
            DeclarationToken(0, 9),
            NumberTypeToken(0, 11),
            SemicolonToken(0, 17)
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
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
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
    fun printStringParameterFormatter() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(\"Hello World\");"
        assert(sentence == sentenceCorrect)
    }

    @Test
    fun printNumberParameterFormatter() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(1);"
        assert(sentence == sentenceCorrect)
    }

    @Test
    fun printVariableParameterFormatter() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            VariableNameToken("HelloWorld", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val formatter = ValidListOfTokensFormatter(PrintlnValidListOfTokensBuilder(), EnterBeforePrintln(OneSpaceBetweenEveryToken(), 1))
        val sentence = formatter.format(tokens)
        val sentenceCorrect = "println(HelloWorld);"
        assert(sentence == sentenceCorrect)
    }

    @Test
    fun oneSpaceBetweenEveryToken() {
        val tokens = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val formatter = PrintScriptFormatterFactory().build("")
        val sentence = formatter.format(tokens)
        val correctSentence = "test = \"Hello World\" ;"
        assertEquals(correctSentence, sentence)
    }

    @Test
    fun previousSpacingBetweenEveryToken() {
        val tokens = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 4),
            StringLiteralToken("Hello World", 0, 5),
            SemicolonToken(0, 18)
        )
        val formatter = PrintScriptFormatterFactory().build("no-conventional")
        val sentence = formatter.format(tokens)
        val correctSentence = "test=\"Hello World\";"
        assertEquals(correctSentence, sentence)
    }
}
