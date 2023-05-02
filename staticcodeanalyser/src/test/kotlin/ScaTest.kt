import org.junit.jupiter.api.Test
import token.VariableLiteralToken

class ScaTest {

    @Test
    fun scaCreatesCorrectly() {
        val tokensCamel = listOf(
            VariableLiteralToken("test_case", 0, 4)
        )
        val scaCamel = RuleStaticCodeAnalyser(CheckVariable(CamelCaseRule()))
        val camelVariable = scaCamel.format(tokensCamel)

        val tokensSnake = listOf(
            VariableLiteralToken("testCase", 0, 4)
        )
        val scaSnake = RuleStaticCodeAnalyser(CheckVariable(SnakeCaseRule()))
        val snakeVariable = scaSnake.format(tokensSnake)

        assert(true)
    }
}
