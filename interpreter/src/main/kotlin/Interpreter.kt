interface Interpreter<T : AbstractSyntaxTree> {
    fun interpret(abstractSyntaxTree: T, interpreterState: PrintScriptInterpreterState): PrintScriptInterpreterState
}

class PrintScriptInterpreter : Interpreter<AbstractSyntaxTree> {
    override fun interpret(
        abstractSyntaxTree: AbstractSyntaxTree,
        interpreterState: PrintScriptInterpreterState
    ): PrintScriptInterpreterState =
        when (abstractSyntaxTree) {
            is PrintlnAst -> PrintlnParameterInterpreter().interpret(abstractSyntaxTree.value(), interpreterState)
            is DeclarationAst -> interpreterState.initializeVariable(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue())
            is AssignationAst<*> -> AssignationParameterInterpreter().interpret(abstractSyntaxTree, interpreterState)
            is AssignationDeclarationAst<*> -> AssignationDeclarationInterpreter().interpret(abstractSyntaxTree, interpreterState)
            else -> interpreterState.addError(AstStructureNotDefinedError())
        }
}

class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter> {
    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: PrintScriptInterpreterState): PrintScriptInterpreterState =
        when (abstractSyntaxTree) {
            is VariableNameNode -> interpreterState.println(abstractSyntaxTree)
            is NumberLiteral<*> -> interpreterState.println(abstractSyntaxTree)
            is StringLiteral -> interpreterState.println(abstractSyntaxTree)
            else -> interpreterState.addError(PrintlnAstParameterNotAccepted())
        }
}
class AssignationParameterInterpreter : Interpreter<AssignationAst<*>> {
    override fun interpret(abstractSyntaxTree: AssignationAst<*>, interpreterState: PrintScriptInterpreterState): PrintScriptInterpreterState =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is IntNumberLiteral -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is StringLiteral -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            is VariableNameNode -> interpreterState.setValueToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
            else -> interpreterState
        }
}

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst<*>> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst<*>,
        interpreterState: PrintScriptInterpreterState
    ): PrintScriptInterpreterState =
        interpreterState.initializeVariable(abstractSyntaxTree.rightValue().leftValue(), abstractSyntaxTree.rightValue().rightValue())
            .let { AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), it) }
}
