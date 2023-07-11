package interpreter.assignationDeclaration

import ast.AssignationDeclarationAst
import ast.ConstAssignationDeclarationAst
import ast.LetAssignationDeclarationAst
import interpreter.Interpreter
import interpreter.InterpreterError
import interpreter.InterpreterResponse
import interpreter.assignation.AssignationParameterInterpreter
import interpreter.declaration.DeclarationConstInterpreter
import interpreter.declaration.DeclarationInterpreter
import interpreter.state.PrintScriptInterpreterState

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse {
        val stateOrError = when (abstractSyntaxTree) {
            is ConstAssignationDeclarationAst -> DeclarationConstInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
            is LetAssignationDeclarationAst -> DeclarationInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
        }
        return if (stateOrError !is InterpreterError) {
            AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), stateOrError as PrintScriptInterpreterState)
        } else {
            stateOrError
        }
    }
}
