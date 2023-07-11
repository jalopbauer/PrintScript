package interpreter.readInput

import ast.AbstractSyntaxTree
import ast.Literal
import ast.ReadInputAst
import interpreter.Interpreter
import interpreter.InterpreterResponse
import interpreter.SendLiteral
import interpreter.state.PrintScriptInterpreterState

class ReadInputInterpreter<T : AbstractSyntaxTree>(
    val interpreter: Interpreter<T, PrintScriptInterpreterState>,
    val func: (Literal) -> T
) : Interpreter<ReadInputAst, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: ReadInputAst,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        interpreterState.readInput()
            .let { (literal, state) ->
                literal
                    ?.let { interpreter.interpret(func(literal), state) }
                    ?: SendLiteral(state)
            }
}
