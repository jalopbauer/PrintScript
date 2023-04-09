interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse?
}

class PrintlnInterpreter : Interpreter<AbstractSyntaxTree, PrintlnInterpreterState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: PrintlnInterpreterState): InterpreterResponse? =
        if (abstractSyntaxTree is PrintlnAst) {
            PrintlnParameterInterpreter().interpret(abstractSyntaxTree.value(), interpreterState)
        } else {
            null
        }
}

class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter, PrintlnInterpreterState> {
    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: PrintlnInterpreterState): InterpreterResponse =
        when (abstractSyntaxTree) {
            is VariableNameNode -> interpreterState.println(abstractSyntaxTree)
            is NumberLiteralStringNode -> interpreterState.println(abstractSyntaxTree)
            is StringNode -> interpreterState.println(abstractSyntaxTree)
        }
}

class DeclarationInterpreter : Interpreter<AbstractSyntaxTree, PrintScriptInterpreterState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: PrintScriptInterpreterState): InterpreterResponse? =
        if (abstractSyntaxTree is DeclarationAst) {
            interpreterState.initializeVariable(abstractSyntaxTree.leftValue().value(), abstractSyntaxTree.rightValue().value())
        } else {
            null
        }
}

class AssignationInterpreter : Interpreter<AbstractSyntaxTree, AssignationInterpreterState> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: AssignationInterpreterState): InterpreterResponse? =
        if (abstractSyntaxTree is AssignationAst<*>) {
            AssignationParameterInterpreter().interpret(abstractSyntaxTree, interpreterState)
        } else {
            null
        }
}

class AssignationParameterInterpreter : Interpreter<AssignationAst<*>, AssignationInterpreterState> {
    override fun interpret(abstractSyntaxTree: AssignationAst<*>, interpreterState: AssignationInterpreterState): InterpreterResponse =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is NumberNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is StringNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is VariableNameNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            else -> AssignationParameterNotValidError()
        }
}
