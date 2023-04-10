interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse?
}

class PrintScriptInterpreter : Interpreter<AbstractSyntaxTree, PrintScriptInterpreterState> {

    private val list = listOf<Interpreter<AbstractSyntaxTree, PrintScriptInterpreterState>>(
        PrintlnInterpreter(),
        DeclarationInterpreter(),
        AssignationInterpreter(),
        AssignationDeclarationInterpreter()

    )
    override fun interpret(
        abstractSyntaxTree: AbstractSyntaxTree,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        ListInterpreter(list).interpret(abstractSyntaxTree, interpreterState)
}

class ListInterpreter<T : AbstractSyntaxTree, U : InterpreterState>(private val interpreters: List<Interpreter<T, U>>) : Interpreter<T, U> {
    override fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse {
        val out: InterpreterResponse? = null
        return interpreters.fold(out) { _, interpreter ->
            interpreter.interpret(abstractSyntaxTree, interpreterState)
        } ?: AstStructureNotDefinedError()
    }
}
class PrintlnInterpreter<U : PrintlnInterpreterState> : Interpreter<AbstractSyntaxTree, U> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: U): InterpreterResponse? =
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

class DeclarationInterpreter<U : DeclarationInterpreterState> : Interpreter<AbstractSyntaxTree, U> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: U): InterpreterResponse? =
        if (abstractSyntaxTree is DeclarationAst) {
            interpreterState.initializeVariable(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue())
        } else {
            null
        }
}

class AssignationInterpreter<U : AssignationInterpreterState> : Interpreter<AbstractSyntaxTree, U> {
    override fun interpret(abstractSyntaxTree: AbstractSyntaxTree, interpreterState: U): InterpreterResponse? =
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

class AssignationDeclarationInterpreter<U : AssignationDeclarationInterpreterState> : Interpreter<AbstractSyntaxTree, U> {
    override fun interpret(
        abstractSyntaxTree: AbstractSyntaxTree,
        interpreterState: U
    ): InterpreterResponse? =
        if (abstractSyntaxTree is AssignationDeclarationAst<*>) {
            val declarationAst = abstractSyntaxTree.rightValue()
            val declarationInterpreterResponse = interpreterState.initializeVariable(declarationAst.leftValue(), declarationAst.rightValue())
            if (declarationInterpreterResponse is InterpreterState) {
                val assignationAst = abstractSyntaxTree.leftValue()
                AssignationParameterInterpreter().interpret(assignationAst, interpreterState)
            } else {
                declarationInterpreterResponse
            }
        } else {
            null
        }
}
