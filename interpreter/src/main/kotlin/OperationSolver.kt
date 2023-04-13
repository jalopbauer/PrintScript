interface OperationSolver {
    fun solver(operation: Operation, printScriptInterpreterState: PrintScriptInterpreterState): OperationResult
}
class PrintlnOperationSolver : OperationSolver {
    override fun solver(operation: Operation, printScriptInterpreterState: PrintScriptInterpreterState): OperationResult =
        when (operation) {
            is StringLiteralNode -> ValueOperationResult(operation.value)
            is NumberLiteralNode -> ValueOperationResult(operation.value().toString())
            is VariableNameNode ->
                printScriptInterpreterState.get(operation)
                    ?.let {
                        this.solver(it, printScriptInterpreterState)
                    }
                    ?: OperationError(VariableIsNotDefinedError())
            is NumberNumberSum -> this.solver(NumberLiteralNode(operation.leftNumber() + operation.rightNumber()), printScriptInterpreterState)
//            is NumberStringSum -> this.solver(NumberLiteralNode(leftOperable.number + rightOperable.number), printScriptInterpreterState)
//                    (leftOperable is NumberLiteralNode && rightOperable is StringLiteralNode) ->
//                        this.solver(StringLiteralNode(leftOperable.number.toString() + rightOperable.value), printScriptInterpreterState)
//                    (leftOperable is StringLiteralNode && rightOperable is NumberLiteralNode) ->
//                        this.solver(StringLiteralNode(leftOperable.value + rightOperable.number.toString()), printScriptInterpreterState)
//                    else ->
//                        OperationError(OperationNotSupportedError())
//                }
//            }
            else -> OperationError(OperationNotSupportedError())
        }
}

class ValueOperationResult<T>(val operation: T) : OperationResult

interface OperationResult
class OperationError<T : Error> (val error: T) : OperationResult
