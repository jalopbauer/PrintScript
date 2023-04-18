import state.VariableInterpreterState

sealed interface ConcatSolverResponse
data class StringLiteralResponse(val literal: StringLiteral) : ConcatSolverResponse
data class ConcatErrorResponse(val concatError: InterpreterError) : ConcatSolverResponse
interface ConcatenationParameterSolver<T : ConcatenationParameter> {
    fun solve(concatenationParameter: T, variableInterpreterState: VariableInterpreterState): ConcatSolverResponse
}
class NumberConcatSolver : ConcatenationParameterSolver<NumberLiteral<*>> {
    override fun solve(
        concatenationParameter: NumberLiteral<*>,
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
                    is NumberLiteral<*> -> NumberConcatSolver().solve(it, variableInterpreterState)
                    is StringLiteral -> StringConcatSolver().solve(it, variableInterpreterState)
                    else -> ConcatErrorResponse(NotValidType())
                }
            }
            ?: ConcatErrorResponse(VariableIsNotDefined())
}
class FullConcatSolver : ConcatenationParameterSolver<ConcatenationParameter> {
    override fun solve(concatenationParameter: ConcatenationParameter, variableInterpreterState: VariableInterpreterState): ConcatSolverResponse =
        when (concatenationParameter) {
            is DoubleNumberLiteral -> NumberConcatSolver().solve(concatenationParameter, variableInterpreterState)
            is IntNumberLiteral -> NumberConcatSolver().solve(concatenationParameter, variableInterpreterState)
            is VariableNameNode -> VariableConcatSolver().solve(concatenationParameter, variableInterpreterState)
            else -> ConcatErrorResponse(VariableIsNotDefined())
        }
}
