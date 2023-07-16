package interpreter.assignationDeclaration

import ast.AssignationDeclarationAst
import ast.ConstAssignationDeclarationAst
import ast.LetAssignationDeclarationAst
import ast.Literal
import ast.ReadInputAst
import interpreter.Interpreter
import interpreter.InterpreterResponse
import interpreter.SendLiteral
import interpreter.assignation.AssignationParameterInterpreter
import interpreter.declaration.DeclarationConstInterpreter
import interpreter.declaration.DeclarationInterpreter
import interpreter.state.PrintScriptInterpreterState

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse {
        val rightValue = abstractSyntaxTree.leftValue().rightValue()
        val first = interpreterState.readInput().first
        return when {
            rightValue is ReadInputAst && first !is Literal ->
                SendLiteral(interpreterState.println(rightValue.message.value))
            else -> {
                val stateOrError = when (abstractSyntaxTree) {
                    is ConstAssignationDeclarationAst -> DeclarationConstInterpreter().interpret(
                        abstractSyntaxTree.rightValue(),
                        interpreterState
                    )

                    is LetAssignationDeclarationAst -> DeclarationInterpreter().interpret(
                        abstractSyntaxTree.rightValue(),
                        interpreterState
                    )
                }
                return if (stateOrError is PrintScriptInterpreterState) {
                    AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), stateOrError)
                } else {
                    stateOrError
                }
            }
        }
    }
}
