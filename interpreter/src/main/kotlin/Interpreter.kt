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

class DeclarationInterpreter : Interpreter<AbstractSyntaxTree> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: InterpreterState): InterpreterResponse? =
        if (abstractSyntaxTree is DeclarationAst) {
            interpreterState.initializeVariable(abstractSyntaxTree.leftValue().value(), abstractSyntaxTree.rightValue().value())
        } else {
            null
        }
}

class AssignationInterpreter : Interpreter<AbstractSyntaxTree> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: InterpreterState): InterpreterResponse? =
        if (abstractSyntaxTree is AssignationAst<*>) {
            AssignationParameterInterpreter().interpret(abstractSyntaxTree, interpreterState)
        } else {
            null
        }
}

class AssignationParameterInterpreter : Interpreter<AssignationAst<*>> {
    override fun interpret(abstractSyntaxTree: AssignationAst<*>, interpreterState: InterpreterState): InterpreterResponse =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is NumberNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is StringNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is VariableNameNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            else -> AssignationParameterNotValidError()
        }
}
