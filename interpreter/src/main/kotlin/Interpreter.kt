interface Interpreter<T : InterpreterState> {
    fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: T): T?
}

class PrintlnInterpreter : Interpreter<PrintlnState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: PrintlnState): PrintlnState? {
        return if (abstractSyntaxTree.value() != "println") {
            null
        } else if (abstractSyntaxTree !is ValuedSingleNode<*>) {
            null
        } else {
            interpreterState.add(abstractSyntaxTree.node.value().toString())
        }
    }
}
