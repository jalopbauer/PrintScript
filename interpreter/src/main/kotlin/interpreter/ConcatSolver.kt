package interpreter

import ast.ConcatenationParameter
import ast.DoubleNumberLiteral
import ast.IntNumberLiteral
import ast.NumberLiteral
import ast.StringConcatenation
import ast.StringLiteral
import ast.VariableNameNode
import interpreter.state.VariableInterpreterState

sealed interface ConcatSolverResponse
data class StringLiteralResponse(val literal: StringLiteral) : ConcatSolverResponse
data class ConcatErrorResponse(val concatError: InterpreterError) : ConcatSolverResponse
interface ConcatenationParameterSolver<T : ConcatenationParameter> {
    fun solve(concatenationParameter: T, variableInterpreterState: VariableInterpreterState): ConcatSolverResponse
}
class NumberConcatSolver : ConcatenationParameterSolver<NumberLiteral> {
    override fun solve(
        concatenationParameter: NumberLiteral,
        variableInterpreterState: VariableInterpreterState
    ): ConcatSolverResponse =
        StringLiteralResponse(StringLiteral(concatenationParameter.value().toString()))
}
class StringConcatSolver : ConcatenationParameterSolver<StringLiteral> {
    override fun solve(
        concatenationParameter: StringLiteral,
        variableInterpreterState: VariableInterpreterState
    ): ConcatSolverResponse =
        StringLiteralResponse(concatenationParameter)
}
class VariableConcatSolver : ConcatenationParameterSolver<VariableNameNode> {
    override fun solve(
        concatenationParameter: VariableNameNode,
        variableInterpreterState: VariableInterpreterState
    ): ConcatSolverResponse =
        variableInterpreterState.get(concatenationParameter)
            ?.let {
                when (it) {
                    is NumberLiteral -> NumberConcatSolver().solve(it, variableInterpreterState)
                    is StringLiteral -> StringConcatSolver().solve(it, variableInterpreterState)
                    else -> ConcatErrorResponse(InterpreterError())
                }
            }
            ?: ConcatErrorResponse(InterpreterError())
}
class ConcatMapperSolver : ConcatenationParameterSolver<ConcatenationParameter> {
    override fun solve(concatenationParameter: ConcatenationParameter, variableInterpreterState: VariableInterpreterState): ConcatSolverResponse =
        when (concatenationParameter) {
            is DoubleNumberLiteral -> NumberConcatSolver().solve(concatenationParameter, variableInterpreterState)
            is IntNumberLiteral -> NumberConcatSolver().solve(concatenationParameter, variableInterpreterState)
            is VariableNameNode -> VariableConcatSolver().solve(concatenationParameter, variableInterpreterState)
            is StringLiteral -> StringConcatSolver().solve(concatenationParameter, variableInterpreterState)
        }
}

class ConcatenationSolver {
    fun solve(stringConcatenation: StringConcatenation, variableInterpreterState: VariableInterpreterState): ConcatSolverResponse {
        val initial: ConcatSolverResponse? = null
        return stringConcatenation.concatenationParameterValues
            .fold(initial) { acc, concatenationParameter ->
                when (acc) {
                    null -> ConcatMapperSolver().solve(concatenationParameter, variableInterpreterState)
                    is StringLiteralResponse -> {
                        when (val solve = ConcatMapperSolver().solve(concatenationParameter, variableInterpreterState)) {
                            is ConcatErrorResponse -> solve
                            is StringLiteralResponse -> StringLiteralResponse(StringLiteral(acc.literal.value + solve.literal.value))
                        }
                    }
                    else -> acc
                }
            }
            ?: ConcatErrorResponse(InterpreterError())
    }
}
