package interpreter

import ast.AbstractSyntaxTree
import ast.AssignationAst
import ast.AssignationDeclarationAst
import ast.DeclarationAst
import ast.PrintlnAst
import interpreter.assignation.AssignationParameterInterpreter
import interpreter.print.PrintlnParameterInterpreter
import interpreter.state.PrintScriptInterpreterState

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
            else -> InterpreterError()
        }
}
