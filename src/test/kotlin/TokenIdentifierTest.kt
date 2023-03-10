import org.junit.jupiter.api.Test
import token.*

class TokenIdentifierTest {
    private fun stringExpectedToBeContained(string: String, tokenIdentifier: TokenIdentifier) {
        assert(tokenIdentifier.identify(string))
    }

    private fun stringNotExpectedToBeContained(string: String, tokenIdentifier: TokenIdentifier) {
        assert(!tokenIdentifier.identify(string))
    }

    private fun tokenIdentifierTest(tokenIdentifierTestInput: TokenIdentifierTestInput) {
        stringExpectedToBeContained(tokenIdentifierTestInput.stringWithValue, tokenIdentifierTestInput.tokenIdentifier)
        stringNotExpectedToBeContained(tokenIdentifierTestInput.stringWithoutValue, tokenIdentifierTestInput.tokenIdentifier)
    }

    private class TokenIdentifierTestInput(
        val tokenIdentifier: TokenIdentifier,
        val stringWithValue: String,
        val stringWithoutValue: String
    )

    private val tokensTestList: List<TokenIdentifierTestInput> = listOf(
          TokenIdentifierTestInput(TokenIdentifier.TYPE_ASSIGNATION_TOKEN, ":", "|")
        , TokenIdentifierTestInput(TokenIdentifier.VALUE_ASSIGNATION_TOKEN, "=", "|")
        , TokenIdentifierTestInput(TokenIdentifier.SUM_OPERATION_TOKEN, "+", "|")
        , TokenIdentifierTestInput(TokenIdentifier.SUB_OPERATION_TOKEN, "-", "|")
        , TokenIdentifierTestInput(TokenIdentifier.MULT_OPERATION_TOKEN, "*", "|")
        , TokenIdentifierTestInput(TokenIdentifier.DIV_OPERATION_TOKEN, "/", "|")
        , TokenIdentifierTestInput(TokenIdentifier.NUMBER_TYPE_TOKEN, "number", "|")
        , TokenIdentifierTestInput(TokenIdentifier.STRING_TYPE_TOKEN, "string", "|")
        , TokenIdentifierTestInput(TokenIdentifier.PRINTLN_TOKEN, "println", "|")
        , TokenIdentifierTestInput(TokenIdentifier.LEFT_PARENTHESIS_TOKEN, "(", "|")
        , TokenIdentifierTestInput(TokenIdentifier.RIGHT_PARENTHESIS_TOKEN, ")", "|")
        , TokenIdentifierTestInput(TokenIdentifier.SEMICOLON_TOKEN, ";", "|")
        , TokenIdentifierTestInput(TokenIdentifier.LET_TOKEN, "let", "|")
        , TokenIdentifierTestInput(TokenIdentifier.NUMBER_LITERAL_TOKEN, "69", "|")
        , TokenIdentifierTestInput(TokenIdentifier.NUMBER_LITERAL_TOKEN, "420", "|")
        , TokenIdentifierTestInput(TokenIdentifier.STRING_LITERAL_TOKEN, "'noice'", "|")
        , TokenIdentifierTestInput(TokenIdentifier.STRING_LITERAL_TOKEN, "\"toit\"", "|")
    )
    @Test
    fun test(){
        tokensTestList.forEach{ token -> tokenIdentifierTest(token)}
    }
}