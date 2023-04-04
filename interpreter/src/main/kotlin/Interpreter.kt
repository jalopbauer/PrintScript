interface Interpreter<T : InterpreterState> {
    fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: T): T
}
