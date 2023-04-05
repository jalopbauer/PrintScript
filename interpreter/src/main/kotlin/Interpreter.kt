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

class DeclarationInterpreter : Interpreter<DeclarationResponse, DeclarationInterpreterState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree<*>, interpreterState: DeclarationInterpreterState): DeclarationResponse? {
        return if (abstractSyntaxTree is DeclarationAst) {
            interpreterState.add(abstractSyntaxTree.value())
        } else {
            null
        }
    }
}
