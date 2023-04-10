import token.ClosedBracketToken
import token.NumberLiteralToken
import token.OpenBracketToken
import token.OperatorHighToken
import token.OperatorLowToken
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
}
