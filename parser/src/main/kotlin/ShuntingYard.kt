import token.Token

interface ShuntingYard {
    fun check(tokens: List<Token>): List<Token>
    fun orderString(content: List<Token>): StringConcatenation?
    fun orderNumber(content: List<Token>): OperationParameter?
}
