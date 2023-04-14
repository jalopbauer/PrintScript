// String Concatenation
data class StringConcatenation(val concatenationParameterValues: ArrayDeque<ConcatenationParameter>) : AbstractSyntaxTree
interface ConcatenationParameter : AbstractSyntaxTree

// Operation
interface Operation : AbstractSyntaxTree {
    fun leftNode(): OperationParameter
    fun rightNode(): OperationParameter
}
interface OperationParameter
