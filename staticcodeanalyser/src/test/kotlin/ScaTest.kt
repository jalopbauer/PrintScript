import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.IntNumberLiteralToken
import token.VariableLiteralToken

class ScaTest {
    @Test
    fun onlyCamelCaseSendSnakeCaseShowsError() {
        val variableName = "test_case"
        val tokensCamel = listOf(
            VariableLiteralToken(variableName, 0, 4)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("")
        assertEquals("Variable $variableName is not camelCase", scaCamel.format(tokensCamel))
    }

    @Test
    fun onlyCamelCaseSendCamelCaseShowsNothing() {
        val variableName = "testCase"
        val tokensCamel = listOf(
            VariableLiteralToken(variableName, 0, 4)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("")
        assertEquals(null, scaCamel.format(tokensCamel))
    }

    @Test
    fun onlySnakeCaseSendCamelCaseShowsError() {
        val variableName = "testCase"

        val tokensCamel = listOf(
            VariableLiteralToken(variableName, 0, 4)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("snake-case-variable")
        assertEquals("Variable $variableName is not snake_case", scaCamel.format(tokensCamel))
    }

    @Test
    fun onlySnakeCaseSendSnakeCaseShowsNothing() {
        val variableName = "test_case"
        val tokensCamel = listOf(
            VariableLiteralToken(variableName, 0, 4)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("snake-case-variable")
        assertEquals(null, scaCamel.format(tokensCamel))
    }

    @Test
    fun onlyNumberLiteralShowsNothing() {
        val tokensCamel = listOf(
            IntNumberLiteralToken(1, 0, 4)
        )
        val scaCamel = PrintScriptStaticCodeAnalyserFactory().build("snake-case-variable")
        assertEquals(null, scaCamel.format(tokensCamel))
    }
}
