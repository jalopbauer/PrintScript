interface OperationSolver<T : Operable> {
    fun solver(operation: T, printScriptInterpreterState: PrintScriptInterpreterState): OperationResult
}
class PrintlnOperationSolver : OperationSolver<Operable> {
    override fun solver(operation: Operable, printScriptInterpreterState: PrintScriptInterpreterState): OperationResult =
        when (operation) {
            is EndingOperable -> EndingOperableSolver().solver(operation, printScriptInterpreterState)
            is Sum<*, *> -> SumSolver().solver(operation, printScriptInterpreterState)
//            is NumberNumberSum -> this.solver(NumberLiteralNode(operation.leftNumber() + operation.rightNumber()), printScriptInterpreterState)
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

class EndingOperableSolver : OperationSolver<EndingOperable> {
    override fun solver(
        operation: EndingOperable,
        printScriptInterpreterState: PrintScriptInterpreterState
    ): OperationResult =
        when (operation) {
            is StringLiteralNode -> ValueOperationResult(operation.value)
            is NumberLiteralNode -> ValueOperationResult(operation.value().toString())
            is VariableNameNode ->
                printScriptInterpreterState.get(operation)
                    ?.let { this.solver(it, printScriptInterpreterState) }
                    ?: OperationError(VariableIsNotDefinedError())
            else -> OperationError(OperationNotSupportedError())
        }
}

class SumSolver : OperationSolver<Sum<*, *>> {
    override fun solver(
        operation: Sum<*, *>,
        printScriptInterpreterState: PrintScriptInterpreterState
    ): OperationResult =
        when (operation) {
            else -> OperationError(OperationNotSupportedError())
        }
}

class ValueOperationResult<T>(val operation: T) : OperationResult

interface OperationResult
class OperationError<T : Error> (val error: T) : OperationResult
