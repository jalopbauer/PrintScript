// String Concatenation
data class StringConcatenation(val concatenationParameterValues: ArrayDeque<ConcatenationParameter>) : AbstractSyntaxTree
interface ConcatenationParameter : AbstractSyntaxTree

// Operation
sealed interface OperationParameter : AbstractSyntaxTree
data class Operation(val left: OperationParameter, val operation: OperationType, val right: OperationParameter) : OperationParameter
interface OperationType
