interface Interpreter<T : InterpreterResponse, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: U): T?
}

class PrintlnInterpreter : Interpreter<PrintlnResponse, PrintLnInterpreterState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: PrintLnInterpreterState): PrintlnResponse? {
        return if (abstractSyntaxTree is PrintlnAst<*>) {
            interpreterState.add(abstractSyntaxTree.value())
        } else {
            null
        }
    }
}
