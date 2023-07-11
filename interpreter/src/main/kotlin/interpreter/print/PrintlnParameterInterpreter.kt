package interpreter.print

import ast.BooleanLiteral
import ast.NumberLiteral
import ast.PrintlnAstParameter
import ast.StringConcatenation
import ast.StringLiteral
import ast.VariableNameNode
import interpreter.ConcatErrorResponse
import interpreter.ConcatenationSolver
import interpreter.Interpreter
import interpreter.InterpreterError
import interpreter.InterpreterResponse
import interpreter.StringLiteralResponse
import interpreter.state.PrintlnInterpreterState

class PrintlnParameterInterpreter : Interpreter<PrintlnAstParameter, PrintlnInterpreterState> {
    override fun interpret(abstractSyntaxTree: PrintlnAstParameter, interpreterState: PrintlnInterpreterState): InterpreterResponse =
        when (abstractSyntaxTree) {
            is VariableNameNode ->
                interpreterState.get(abstractSyntaxTree)
                    ?.let { this.interpret(it as PrintlnAstParameter, interpreterState) }
                    ?: InterpreterError()
            is NumberLiteral -> interpreterState.println(abstractSyntaxTree.value().toString())
            is BooleanLiteral -> interpreterState.println(abstractSyntaxTree.toString())
            is StringLiteral -> interpreterState.println(abstractSyntaxTree.value)
            is StringConcatenation ->
                when (val solve = ConcatenationSolver().solve(abstractSyntaxTree, interpreterState)) {
                    is ConcatErrorResponse -> solve.concatError
                    is StringLiteralResponse -> interpreterState.println(solve.literal.value)
                }
            else -> InterpreterError()
        }
}
