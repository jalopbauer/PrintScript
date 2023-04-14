// String Concatenation
data class StringConcatenation(val concatenationParameterValues: ArrayDeque<ConcatenationParameter>) : AbstractSyntaxTree
interface ConcatenationParameter : AbstractSyntaxTree

data class OperationNode<T, U>(val rightNode: T, val operation: String, val leftNumber: IntNumberLiteralLiteral) : AbstractSyntaxTree {
    fun value(): Int = leftNumber.number // TODO ver como transformar operation en un operador valido
}
