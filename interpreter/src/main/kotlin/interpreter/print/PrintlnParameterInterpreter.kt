package interpreter.print

import ast.BooleanLiteral
import ast.FalseLiteral
import ast.NumberLiteral
import ast.Operation
import ast.PrintlnAstParameter
import ast.ReadInputAst
import ast.StringConcatenation
import ast.StringLiteral
import ast.TrueLiteral
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
import interpreter.assignation.ReadInputInterpreter
import interpreter.other.Helper
import interpreter.state.PrintScriptInterpreterState
class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter, PrintScriptInterpreterState> {
    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: PrintScriptInterpreterState): InterpreterResponse =
        when (abstractSyntaxTree) {
            is VariableNameNode ->
                interpreterState.get(abstractSyntaxTree)
                    ?.let { this.interpret(it as PrintlnAstParameter, interpreterState) }
                    ?: InterpreterError()
            is NumberLiteral -> interpreterState.println(abstractSyntaxTree.value().toString())
            is BooleanLiteral -> interpreterState.println(
                when (abstractSyntaxTree) {
                    FalseLiteral -> "false"
                    TrueLiteral -> "true"
                }
            )
            is StringLiteral -> interpreterState.println(abstractSyntaxTree.value)
            is StringConcatenation ->
                when (val solve = ConcatenationSolver().solve(abstractSyntaxTree, interpreterState)) {
                    is ConcatErrorResponse -> solve.concatError
                    is StringLiteralResponse -> interpreterState.println(solve.literal.value)
                }
            is ReadInputAst -> ReadInputInterpreter(this) { literal -> literal }.interpret(abstractSyntaxTree, interpreterState)
            is Operation ->
                when (val literalOrError = FullSolver().solve(abstractSyntaxTree, interpreterState)) {
                    is InterpreterErrorResponse -> {
                        Helper().fromOperationToStringConcat(abstractSyntaxTree)?.let { this.interpret(it, interpreterState) }
                            ?: literalOrError.interpreterError
                    }
                    is NumberLiteralResponse -> this.interpret(literalOrError.literal, interpreterState)
                }
            else -> InterpreterError()
        }
}
