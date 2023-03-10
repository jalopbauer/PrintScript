import token.TokenIdentifier

class TokenIdentifierTest {
    private fun stringExpectedToBeContained(string: String, tokenIdentifier: TokenIdentifier) {
        assert(tokenIdentifier.identify(string))
    }

    private fun stringNotExpectedToBeContained(string: String, tokenIdentifier: TokenIdentifier) {
        assert(!tokenIdentifier.identify(string))
    }

    private fun tokenIdentifierTest(tokenIdentifier: TokenIdentifier, stringWithValue: String, stringWithoutValue: String) {
        stringExpectedToBeContained(stringWithValue, tokenIdentifier)
        stringNotExpectedToBeContained(stringWithoutValue, tokenIdentifier)
    }

}