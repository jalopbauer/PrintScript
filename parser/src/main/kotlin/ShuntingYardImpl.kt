import token.ClosedBracketToken
import token.NumberLiteralToken
import token.OpenBracketToken
import token.OperatorHighToken
import token.OperatorLowToken
import token.StringLiteralToken
import token.Token
import token.TokenName

class ShuntingYardImpl : ShuntingYard {
    override fun check(tokens: List<Token>): List<Token> {
        val stack = ArrayDeque<Token>()
        val queue = ArrayDeque<Token>()

        for (token in tokens) {
            when (token) {
                is NumberLiteralToken -> queue.add(token)
                is OperatorHighToken -> checkOperators(stack, queue, token)
                is OperatorLowToken -> stack.add(token)
                is OpenBracketToken -> stack.add(token)
                is ClosedBracketToken -> findBracket(stack, queue)
            }
        }
        // los operadores restantes se van metiendo en la queue hasta que quede vacio el stack
        for (i in stack.size downTo 1) {
            queue.add(stack.removeLast())
        }

        return queue
    }

    private fun findBracket(stack: ArrayDeque<Token>, queue: ArrayDeque<Token>) {
        for (i in (stack.size - 1) downTo 0) {
            if (stack[i].tokenName() == TokenName.LEFT_PARENTHESIS) {
                stack.remove(stack[i])
                break
            }
            queue.add(stack.removeLast())
        }
    }

    private fun checkOperators(stack: ArrayDeque<Token>, queue: ArrayDeque<Token>, operator: Token) {
        if (!stack.isEmpty()) {
            when (stack.last()) {
                is OperatorLowToken -> swap(stack, queue)
            }
        }
        stack.add(operator)
    }

    private fun swap(stack: ArrayDeque<Token>, queue: ArrayDeque<Token>) {
        queue.add(stack.removeLast())
    }

    override fun orderString(content: List<Token>): AssignationParameterNode<String> {
        if (content.size == 1) {
            val stringToken = content.component1() as StringLiteralToken
            return StringLiteralNode(stringToken.value)
        }
        return StringConcatNode((content.component1() as StringLiteralToken).value, addToStringTree(content.drop(1)))
    }
    private fun addToStringTree(content: List<Token>): StringNode {
        if (content.size == 1) {
            val stringToken = content.component1() as StringLiteralToken
            return StringLiteralNode(stringToken.value)
        }
        return StringConcatNode((content.component1() as StringLiteralToken).value, addToStringTree(content.drop(1)))
    }

    override fun orderNumber(content: List<Token>): AssignationParameterNode<Int> {
        if (content.size == 1) {
            val numberToken = content.component1() as NumberLiteralToken
            return NumberLiteralNode(numberToken.value)
        }
        // aca tengo que hacer el algoritmo de creacion de arbol
        val orderOperation = check(content)
        val stack = ArrayDeque<NumberNode>()
        for (token in orderOperation) {
            when (token) {
                is NumberLiteralToken -> stack.add(NumberLiteralNode(token.value))
                is OperatorHighToken -> stack.add(OperationNode(stack.removeLast(), token.tokenName.name, stack.removeLast() as NumberLiteralNode))
            }
        }
        return stack.removeLast()
    }
}
