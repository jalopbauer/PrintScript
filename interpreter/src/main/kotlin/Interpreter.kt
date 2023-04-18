import state.InterpreterState
import state.VariableInterpreterState

interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse
}

//
// class PrintScriptInterpreter : Interpreter<AbstractSyntaxTree> {
//    override fun interpret(
//        abstractSyntaxTree: AbstractSyntaxTree,
//        interpreterState: PrintScriptInterpreterState
//    ): PrintScriptInterpreterState =
//        when (abstractSyntaxTree) {
//            is PrintlnAst -> PrintlnParameterInterpreter().interpret(abstractSyntaxTree.value(), interpreterState)
//            is DeclarationAst -> interpreterState.initializeVariable(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
//            is AssignationAst<*> -> AssignationParameterInterpreter().interpret(abstractSyntaxTree, interpreterState)
//            is AssignationDeclarationAst<*> -> AssignationDeclarationInterpreter().interpret(abstractSyntaxTree, interpreterState)
//            else -> interpreterState.addError(AstStructureNotDefinedError())
//        }
// }
//
// class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter> {
//    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: PrintScriptInterpreterState): PrintScriptInterpreterState =
//        when (abstractSyntaxTree) {
//            is VariableNameNode -> interpreterState.println(abstractSyntaxTree)
//            is NumberLiteral<*> -> interpreterState.println(abstractSyntaxTree)
//            is StringLiteral -> interpreterState.println(abstractSyntaxTree)
//            else -> interpreterState.addError(PrintlnAstParameterNotAccepted())
//        }
// }
class AssignationParameterInterpreter : Interpreter<AssignationAst<*>, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: AssignationAst<*>, interpreterState: VariableInterpreterState): InterpreterResponse =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is IntNumberLiteral,
            is StringLiteral,
            is DoubleNumberLiteral -> interpreterState.setLiteralToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode as Literal)
            is VariableNameNode -> interpreterState.setVariableValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            else -> AstStructureNotDefinedError()
        }
}

class DeclarationInterpreter : Interpreter<DeclarationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: DeclarationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        interpreterState.initializeVariable(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
}

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst<*>, VariableInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst<*>,
        interpreterState: VariableInterpreterState
    ): InterpreterResponse {
        val stateOrError = AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), interpreterState)
        return if (stateOrError !is InterpreterError) {
            DeclarationInterpreter().interpret(abstractSyntaxTree.rightValue(), stateOrError as VariableInterpreterState)
        } else {
            stateOrError
        }
    }
}
