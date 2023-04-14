data class StringConcatNode(private val value: String) : AbstractSyntaxTree {
    fun value(): String = value
}

data class OperationNode<T, U>(val rightNode: T, val operation: String, val leftNumber: IntNumberLiteralLiteral) : AbstractSyntaxTree {
    fun value(): Int = leftNumber.number // TODO ver como transformar operation en un operador valido
}
