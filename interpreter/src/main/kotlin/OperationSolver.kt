interface OperationSolver {
    fun solver(operation: Operation, printScriptInterpreterState: PrintScriptInterpreterState): OperationResult
}
class PrintlnOperationSolver : OperationSolver {
    override fun solver(operation: Operation, printScriptInterpreterState: PrintScriptInterpreterState): OperationResult =
        when (operation) {
            is StringLiteralNode -> ValueOperationResult(operation.value())
            is NumberLiteralNode -> ValueOperationResult(operation.value().toString())
            is VariableNameNode ->
                printScriptInterpreterState.get(operation)
                    ?.let {
                        this.solver(it, printScriptInterpreterState)
                    }
                    ?: OperationError(VariableIsNotDefinedError())
            else -> OperationError(OperationNotSupportedError())
        }
}

class ValueOperationResult<T>(val operation: T) : OperationResult

interface OperationResult
class OperationError<T : Error> (val error: T) : OperationResult
