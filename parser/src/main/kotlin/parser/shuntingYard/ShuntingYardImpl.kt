package parser.shuntingYard

import ast.ConcatenationParameter
import ast.Div
import ast.DoubleNumberLiteral
import ast.IntNumberLiteral
import ast.Mult
import ast.Operation
import ast.OperationParameter
import ast.StringConcatenation
import ast.StringLiteral
import ast.Sub
import ast.Sum
import ast.VariableNameNode
import token.DoubleNumberLiteralToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.NumberLiteralToken
import token.OperatorHighToken
import token.OperatorLowToken
import token.RightParenthesisToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.VariableNameToken

class ShuntingYardImpl : ShuntingYard {
    override fun check(tokens: List<Token>): List<Token> {
        val stack = ArrayDeque<Token>()
        val queue = ArrayDeque<Token>()
        for (token in tokens) {
            when (token) {
                is NumberLiteralToken -> queue.add(token)
                is VariableNameToken -> queue.add(token)
                is OperatorHighToken -> checkOperators(stack, queue, token)
                is OperatorLowToken -> stack.add(token)
                is LeftParenthesisToken -> stack.add(token)
                is RightParenthesisToken -> findBracket(stack, queue)
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
    override fun orderString(content: List<Token>): StringConcatenation {
        var concatenation: MutableList<ConcatenationParameter> = mutableListOf()
        if (content.size == 1) {
            when (val stringToken = content.component1()) {
                is StringLiteralToken -> concatenation.add(StringLiteral(stringToken.value))
                is VariableNameToken -> concatenation.add(VariableNameNode(stringToken.value))
            }
            return StringConcatenation(concatenation)
        }
        for (token in content) {
            when (token) {
                is StringLiteralToken -> concatenation.add(StringLiteral(token.value))
                is VariableNameToken -> concatenation.add(VariableNameNode(token.value))
            }
        }
        return StringConcatenation(concatenation)
    }
    override fun orderNumber(content: List<Token>): OperationParameter {
        if (content.size == 1) {
            var token = content[0]
            when (token) {
                is IntNumberLiteralToken -> return IntNumberLiteral(token.value)
                is DoubleNumberLiteralToken -> return DoubleNumberLiteral(token.value)
            }
        }
        // aca tengo que hacer el algoritmo de creacion de arbol
        val orderOperation = check(content)
        val stack = ArrayDeque<OperationParameter>()
        for (token in orderOperation) {
            when (token) {
                is IntNumberLiteralToken -> stack.add(IntNumberLiteral(token.value))
                is DoubleNumberLiteralToken -> stack.add(DoubleNumberLiteral(token.value))
                is VariableNameToken -> stack.add(VariableNameNode(token.value))
                is OperatorHighToken, is OperatorLowToken -> getOperationType(token, stack)
            }
        }
        return stack.removeLast()
    }
    private fun getOperationType(token: Token, stack: ArrayDeque<OperationParameter>) {
        when (token.tokenName()) {
            TokenName.SUM -> stack.add(Operation(stack.removeLast(), Sum(), stack.removeLast()))
            TokenName.SUB -> stack.add(Operation(stack.removeLast(), Sub(), stack.removeLast()))
            TokenName.MULT -> stack.add(Operation(stack.removeLast(), Mult(), stack.removeLast()))
            TokenName.DIV -> stack.add(Operation(stack.removeLast(), Div(), stack.removeLast()))
            else -> {}
        }
    }
}
