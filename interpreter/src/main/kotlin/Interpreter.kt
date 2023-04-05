interface Interpreter<T : InterpreterState> {
    fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: T): T?
}

class PrintlnInterpreter : Interpreter<PrintlnState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: PrintlnState): PrintlnState? {
        return if (abstractSyntaxTree is PrintlnAst<*>) {
            interpreterState.add(abstractSyntaxTree.value())
        } else {
            null
        }
    }
}
