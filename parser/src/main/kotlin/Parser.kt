import token.Token

interface Parser {
    fun parse(tokenList: List<Token>): Ast
}