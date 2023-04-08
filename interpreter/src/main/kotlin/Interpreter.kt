interface Interpreter<T : AbstractSyntaxTree> {
    fun interpret(abstractSyntaxTree: T, interpreterState: InterpreterState): InterpreterResponse?
}

class PrintlnInterpreter : Interpreter<AbstractSyntaxTree> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: InterpreterState): InterpreterResponse? =
        if (abstractSyntaxTree is PrintlnAst) {
            PrintlnParameterInterpreter().interpret(abstractSyntaxTree.value(), interpreterState)
        } else {
            null
        }
}

class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter> {
    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: InterpreterState): InterpreterResponse =
        when (abstractSyntaxTree) {
            is VariableNameNode -> interpreterState.addVariableValueToPrintln(abstractSyntaxTree.value())
            is NumberLiteralStringNode, is StringNode -> interpreterState.addValueToPrintln(abstractSyntaxTree.value())
        }
}
