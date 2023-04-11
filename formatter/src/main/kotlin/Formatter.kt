interface Formatter<T : AbstractSyntaxTree, U> {
    fun format(abstractSyntaxTree: T): U
}
