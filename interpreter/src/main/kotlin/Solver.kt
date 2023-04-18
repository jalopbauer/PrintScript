import state.VariableInterpreterState

sealed interface SolverResponse
data class NumberLiteralResponse(val literal: NumberLiteral<*>) : SolverResponse
data class InterpreterErrorResponse(val interpreterError: InterpreterError) : SolverResponse
interface Solver<T : OperationParameter> {
    fun solve(operationParameter: T, variableInterpreterState: VariableInterpreterState): SolverResponse
}
class NumberSolver : Solver<NumberLiteral<*>> {
    override fun solve(
        operationParameter: NumberLiteral<*>,
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
                    is NumberLiteral<*> -> NumberSolver().solve(it, variableInterpreterState)
                    else -> InterpreterErrorResponse(NotValidType())
                }
            }
            ?: InterpreterErrorResponse(VariableIsNotDefined())
}
class FullSolver : Solver<OperationParameter> {
    override fun solve(operationParameter: OperationParameter, variableInterpreterState: VariableInterpreterState): SolverResponse =
        when (operationParameter) {
            is DoubleNumberLiteral -> NumberSolver().solve(operationParameter, variableInterpreterState)
            is IntNumberLiteral -> NumberSolver().solve(operationParameter, variableInterpreterState)
            is VariableNameNode -> VariableSolver().solve(operationParameter, variableInterpreterState)

            is Operation -> {
//                when {
//                    operationParameter.left is Operation -> {
//                        this.solve()
//                    }
//                }
                InterpreterErrorResponse(VariableIsNotDefined())
            }
//                when(operationParameter.operation) {
//                    is Sum -> sum(operationParameter.left, operationParameter.right)
//                    is Sub -> sub(operationParameter.left, operationParameter.right)
//                    is Div -> div
//                    is Mult -> {}
//                    else -> {}
//                }
        }

    fun sum(leftLiteral: NumberLiteral<*>, rightLiteral: NumberLiteral<*>): NumberLiteral<*>? =
        when {
            leftLiteral is DoubleNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number + rightLiteral.number)
            leftLiteral is DoubleNumberLiteral && rightLiteral is IntNumberLiteral -> DoubleNumberLiteral(leftLiteral.number + rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number + rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is IntNumberLiteral -> IntNumberLiteral(leftLiteral.number + rightLiteral.value())
            else -> null
        }
    fun sub(leftLiteral: NumberLiteral<*>, rightLiteral: NumberLiteral<*>): NumberLiteral<*>? =
        when {
            leftLiteral is DoubleNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number - rightLiteral.number)
            leftLiteral is DoubleNumberLiteral && rightLiteral is IntNumberLiteral -> DoubleNumberLiteral(leftLiteral.number - rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number - rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is IntNumberLiteral -> IntNumberLiteral(leftLiteral.number - rightLiteral.value())
            else -> null
        }

    fun mult(leftLiteral: NumberLiteral<*>, rightLiteral: NumberLiteral<*>): NumberLiteral<*>? =
        when {
            leftLiteral is DoubleNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number * rightLiteral.number)
            leftLiteral is DoubleNumberLiteral && rightLiteral is IntNumberLiteral -> DoubleNumberLiteral(leftLiteral.number * rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number * rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is IntNumberLiteral -> IntNumberLiteral(leftLiteral.number * rightLiteral.value())
            else -> null
        }

    fun div(leftLiteral: NumberLiteral<*>, rightLiteral: NumberLiteral<*>): NumberLiteral<*>? =
        when {
            rightLiteral is DoubleNumberLiteral && rightLiteral.number == 0.0 ||
                rightLiteral is IntNumberLiteral && rightLiteral.number == 0 -> null
            leftLiteral is DoubleNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number * rightLiteral.number)
            leftLiteral is DoubleNumberLiteral && rightLiteral is IntNumberLiteral -> DoubleNumberLiteral(leftLiteral.number * rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is DoubleNumberLiteral -> DoubleNumberLiteral(leftLiteral.number * rightLiteral.number)
            leftLiteral is IntNumberLiteral && rightLiteral is IntNumberLiteral -> IntNumberLiteral(leftLiteral.number * rightLiteral.value())
            else -> null
        }
}
