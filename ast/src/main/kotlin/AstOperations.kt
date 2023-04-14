// String Concatenation
data class StringConcatenation(val concatenationParameterValues: ArrayDeque<ConcatenationParameter>) : AbstractSyntaxTree
interface ConcatenationParameter : AbstractSyntaxTree

// Operation
interface Operation<T : OperationParameter, U : OperationParameter> : AbstractSyntaxTree, OperationParameter {
    fun leftNode(): T
    fun rightNode(): U
}
data class BothOperation<leftT : OperationParameter, leftU : OperationParameter, rightT : OperationParameter, rightU : OperationParameter, T : Operation<leftT, leftU>, U : Operation<rightT, rightU>> (val leftValue: T, val rightValue: U) : Operation<T, U> {
    override fun leftNode(): T =
        leftValue
    override fun rightNode(): U =
        rightValue
}
data class SolvableOperation<T, U, V : NumberLiteral<T>, W : NumberLiteral<U>>(val leftValue: V, val rightValue: W) : Operation<V, W> {
    override fun leftNode(): V =
        leftValue

    override fun rightNode(): W =
        rightValue
}
data class LeftIsVariableOperation<U, W : NumberLiteral<U>>(val leftValue: VariableNameNode, val rightValue: W) : Operation<VariableNameNode, W> {
    override fun leftNode(): VariableNameNode =
        leftValue

    override fun rightNode(): W =
        rightValue
}

data class RightIsVariableOperation<T, V : NumberLiteral<T>>(val leftValue: V, val rightValue: VariableNameNode) : Operation<V, VariableNameNode> {
    override fun leftNode(): V =
        leftValue

    override fun rightNode(): VariableNameNode =
        rightValue
}

data class BothVariableOperation(val leftValue: VariableNameNode, val rightValue: VariableNameNode) : Operation<VariableNameNode, VariableNameNode> {
    override fun leftNode(): VariableNameNode =
        leftValue

    override fun rightNode(): VariableNameNode =
        rightValue
}
sealed interface OperationParameter
