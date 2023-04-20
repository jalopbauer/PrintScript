import state.InterpreterState
import state.PrintScriptInterpreterState
import state.PrintlnInterpreterState
import state.VariableInterpreterState

interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse
}

class PrintScriptInterpreter : Interpreter<AbstractSyntaxTree, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AbstractSyntaxTree,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        when (abstractSyntaxTree) {
            is PrintlnAst -> PrintlnParameterInterpreter().interpret(abstractSyntaxTree.value(), interpreterState)
            is DeclarationAst -> DeclarationInterpreter().interpret(abstractSyntaxTree, interpreterState)
            is AssignationAst -> AssignationParameterInterpreter().interpret(abstractSyntaxTree, interpreterState)
            is AssignationDeclarationAst -> AssignationDeclarationInterpreter().interpret(abstractSyntaxTree, interpreterState)
            else -> AstStructureNotDefinedError()
        }
}
class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter, PrintlnInterpreterState> {
    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: PrintlnInterpreterState): InterpreterResponse =
        when (abstractSyntaxTree) {
            is VariableNameNode ->
                interpreterState.get(abstractSyntaxTree)
                    ?.let { this.interpret(it as PrintlnAstParameter, interpreterState) }
                    ?: VariableIsNotDefined()
            is NumberLiteral -> interpreterState.println(abstractSyntaxTree.value().toString())
            is StringLiteral -> interpreterState.println(abstractSyntaxTree.value)
            is StringConcatenation ->
                when (val solve = ConcatenationSolver().solve(abstractSyntaxTree, interpreterState)) {
                    is ConcatErrorResponse -> solve.concatError
                    is StringLiteralResponse -> interpreterState.println(solve.literal.value)
                }
            else -> PrintlnAstParameterNotAccepted()
        }
}
class AssignationParameterInterpreter : Interpreter<AssignationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: AssignationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is IntNumberLiteral,
            is StringLiteral,
            is DoubleNumberLiteral -> interpreterState.setLiteralToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode as Literal)
            is VariableNameNode -> interpreterState.setVariableValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is Operation ->
                when (val literalOrError = FullSolver().solve(assignationParameterNode, interpreterState)) {
                    is InterpreterErrorResponse -> literalOrError.interpreterError
                    is NumberLiteralResponse -> this.interpret(AssignationAst(abstractSyntaxTree.leftValue(), literalOrError.literal), interpreterState)
                }
            is StringConcatenation ->
                when (val solve = ConcatenationSolver().solve(assignationParameterNode, interpreterState)) {
                    is ConcatErrorResponse -> solve.concatError
                    is StringLiteralResponse -> this.interpret(AssignationAst(abstractSyntaxTree.leftValue(), solve.literal), interpreterState)
                }
            else -> AstStructureNotDefinedError()
        }
}

class DeclarationInterpreter : Interpreter<DeclarationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: DeclarationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        interpreterState.initializeVariable(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
}

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst, VariableInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst,
        interpreterState: VariableInterpreterState
    ): InterpreterResponse {
        val stateOrError = DeclarationInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
        return if (stateOrError !is InterpreterError) {
            AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), stateOrError as VariableInterpreterState)
        } else {
            stateOrError
        }
    }
}
