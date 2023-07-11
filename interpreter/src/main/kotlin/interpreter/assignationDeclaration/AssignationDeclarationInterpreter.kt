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
import interpreter.state.VariableInterpreterState

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst, VariableInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst,
        interpreterState: VariableInterpreterState
    ): InterpreterResponse {
        val stateOrError = when (abstractSyntaxTree) {
            is ConstAssignationDeclarationAst -> DeclarationConstInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
            is LetAssignationDeclarationAst -> DeclarationInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
        }
        return if (stateOrError !is InterpreterError) {
            AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), stateOrError as VariableInterpreterState)
        } else {
            stateOrError
        }
    }
}
