package interpreter.assignation

import ast.AssignationAst
import ast.Literal
import ast.Operation
import ast.StringConcatenation
import ast.VariableNameNode
import interpreter.ConcatErrorResponse
import interpreter.ConcatenationSolver
import interpreter.FullSolver
import interpreter.Interpreter
import interpreter.InterpreterError
import interpreter.InterpreterErrorResponse
import interpreter.InterpreterResponse
import interpreter.NumberLiteralResponse
import interpreter.StringLiteralResponse
import interpreter.state.VariableInterpreterState

class AssignationParameterInterpreter : Interpreter<AssignationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: AssignationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is Literal -> interpreterState.setLiteralToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode)
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
            else -> InterpreterError()
        }
}
