package interpreter

import ast.Div
import ast.DoubleNumberLiteral
import ast.IntNumberLiteral
import ast.Mult
import ast.NumberLiteral
import ast.Operation
import ast.OperationParameter
import ast.Sub
import ast.Sum
import ast.VariableNameNode
import interpreter.state.VariableInterpreterState

sealed interface SolverResponse
data class NumberLiteralResponse(val literal: NumberLiteral) : SolverResponse
data class InterpreterErrorResponse(val interpreterError: InterpreterError) : SolverResponse
interface Solver<T : OperationParameter> {
    fun solve(operationParameter: T, variableInterpreterState: VariableInterpreterState): SolverResponse
}
class NumberSolver : Solver<NumberLiteral> {
    override fun solve(
        operationParameter: NumberLiteral,
        variableInterpreterState: VariableInterpreterState
    ): SolverResponse =
        NumberLiteralResponse(operationParameter)
}

class VariableSolver : Solver<VariableNameNode> {
    override fun solve(
        operationParameter: VariableNameNode,
        variableInterpreterState: VariableInterpreterState
    ): SolverResponse =
        variableInterpreterState.get(operationParameter)
            ?.let {
                when (it) {
                    is NumberLiteral -> NumberSolver().solve(it, variableInterpreterState)
                    else -> InterpreterErrorResponse(InterpreterError())
                }
            }
            ?: InterpreterErrorResponse(InterpreterError())
}
class FullSolver : Solver<OperationParameter> {
    override fun solve(operationParameter: OperationParameter, variableInterpreterState: VariableInterpreterState): SolverResponse =
        when (operationParameter) {
            is DoubleNumberLiteral -> NumberSolver().solve(operationParameter, variableInterpreterState)
            is IntNumberLiteral -> NumberSolver().solve(operationParameter, variableInterpreterState)
            is VariableNameNode -> VariableSolver().solve(operationParameter, variableInterpreterState)

            is Operation -> {
                val leftSolve = this.solve(operationParameter.left, variableInterpreterState)
                val rightSolve = this.solve(operationParameter.right, variableInterpreterState)
                if (leftSolve is NumberLiteralResponse && rightSolve is NumberLiteralResponse) {
                    when (operationParameter.operation) {
                        is Sum -> NumberLiteralResponse(sum(leftSolve.literal, rightSolve.literal))
                        is Sub -> NumberLiteralResponse(sub(leftSolve.literal, rightSolve.literal))
                        is Div -> NumberLiteralResponse(div(leftSolve.literal, rightSolve.literal))
                        is Mult -> NumberLiteralResponse(mult(leftSolve.literal, rightSolve.literal))
                    }
                } else {
                    InterpreterErrorResponse(InterpreterError())
                }
            }
        }

    fun sum(leftLiteral: NumberLiteral, rightLiteral: NumberLiteral): NumberLiteral =
        DoubleNumberLiteral(leftLiteral.value().toDouble() + rightLiteral.value().toDouble())
    fun sub(leftLiteral: NumberLiteral, rightLiteral: NumberLiteral): NumberLiteral =
        DoubleNumberLiteral(leftLiteral.value().toDouble() - rightLiteral.value().toDouble())

    fun mult(leftLiteral: NumberLiteral, rightLiteral: NumberLiteral): NumberLiteral =
        DoubleNumberLiteral(leftLiteral.value().toDouble() * rightLiteral.value().toDouble())

    fun div(leftLiteral: NumberLiteral, rightLiteral: NumberLiteral): NumberLiteral =
        DoubleNumberLiteral(leftLiteral.value().toDouble() / rightLiteral.value().toDouble())
}
