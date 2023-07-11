package interpreter

import ast.AbstractSyntaxTree
import ast.AssignationDeclarationAst
import ast.ConstAssignationDeclarationAst
import ast.LetAssignationDeclarationAst
import interpreter.assignation.AssignationParameterInterpreter
import interpreter.declaration.DeclarationConstInterpreter
import interpreter.declaration.DeclarationInterpreter
import interpreter.state.InterpreterState
import interpreter.state.VariableInterpreterState

interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse
}

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
