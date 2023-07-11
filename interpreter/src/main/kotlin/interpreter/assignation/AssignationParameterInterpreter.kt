package interpreter.assignation

import ast.AssignationAst
import ast.Literal
import ast.Operation
import ast.ReadInputAst
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
import interpreter.SendLiteral
import interpreter.StringLiteralResponse
import interpreter.state.PrintScriptInterpreterState

class AssignationParameterInterpreter : Interpreter<AssignationAst, PrintScriptInterpreterState> {
    override fun interpret(abstractSyntaxTree: AssignationAst, interpreterState: PrintScriptInterpreterState): InterpreterResponse =
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
            is ReadInputAst ->
                interpreterState.readInput()
                    .let { (literal, state) ->
                        literal
                            ?.let { this.interpret(abstractSyntaxTree.copy(assignationParameter = literal), state) }
                            ?: SendLiteral(state)
                    }
            else -> InterpreterError()
        }
}
