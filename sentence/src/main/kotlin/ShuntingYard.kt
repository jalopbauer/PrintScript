import token.Token

interface ShuntingYard {
    fun check(tokens: List<Token>): List<Token>
}
