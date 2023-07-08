import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import staticcodeanalyser.Error
import staticcodeanalyser.PrintScriptStaticCodeAnalyserFactory
import staticcodeanalyser.Valid
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.PrintlnToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
import token.SumToken
import token.VariableNameToken
class ScaTest {
    @Test
    fun onlyCamelCaseSendSnakeCaseShowsError() {
        val variableName = "test_case"
        val tokensCamel = listOf(
            VariableNameToken(variableName, 0, 0),
            SemicolonToken(0, variableName.length)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("")
        assertEquals(Error("Variable $variableName is not camelCase"), scaCamel.format(tokensCamel))
    }

    @Test
    fun onlyCamelCaseSendCamelCaseShowsNothing() {
        val variableName = "testCase"
        val tokensCamel = listOf(
            VariableNameToken(variableName, 0, 0),
            SemicolonToken(0, variableName.length)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("")
        assertEquals(Valid, scaCamel.format(tokensCamel))
    }

    @Test
    fun onlySnakeCaseSendCamelCaseShowsError() {
        val variableName = "testCase"

        val tokensCamel = listOf(
            VariableNameToken(variableName, 0, 0),
            SemicolonToken(0, variableName.length)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("snake-case-variable")
        assertEquals(Error("Variable $variableName is not snake_case"), scaCamel.format(tokensCamel))
    }

    @Test
    fun onlySnakeCaseSendSnakeCaseShowsNothing() {
        val variableName = "test_case"
        val tokensCamel = listOf(
            VariableNameToken(variableName, 0, 0),
            SemicolonToken(0, variableName.length)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("snake-case-variable")
        assertEquals(Valid, scaCamel.format(tokensCamel))
    }

    @Test
    fun onlyNumberLiteralShowsNothing() {
        val tokensCamel = listOf(
            IntNumberLiteralToken(1, 0, 0),
            SemicolonToken(0, 1)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("snake-case-variable")
        assertEquals(Valid, scaCamel.format(tokensCamel))
    }

    @Test
    fun printLnStringLiteralValid() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("HelloWorld", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("allow-literals-or-variable-only")
        assertEquals(Valid, scaCamel.format(tokens))
    }

    @Test
    fun printLnNumberLiteralValid() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("allow-literals-or-variable-only")
        assertEquals(Valid, scaCamel.format(tokens))
    }

    @Test
    fun printLnStringConcatenationLiteralNotValid() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("HelloWorld", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("HelloWorld", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("allow-literals-or-variable-only")
        assertEquals(Error("StringConcat not valid"), scaCamel.format(tokens))
    }

    @Test
    fun printLnStringConcatenationLiteralValid() {
        val tokens = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("HelloWorld", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("HelloWorld", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("")
        assertEquals(Valid, scaCamel.format(tokens))
    }
}
