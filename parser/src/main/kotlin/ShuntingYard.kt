import token.Token

interface ShuntingYard {
    fun check(tokens: List<Token>): List<Token>
    fun orderString(content: List<Token>): AssignationParameterNode<String>
    fun orderNumber(content: List<Token>): AssignationParameterNode<Int>
}
