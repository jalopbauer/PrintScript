import token.Token
interface ParserAstValidator {
    fun validate(tokens: List<Token>): ValidListOfTokens?
}
