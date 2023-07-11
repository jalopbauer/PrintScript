package interpreter.readInput

import ast.Literal
import ast.ReadInputAst
import interpreter.Interpreter
import interpreter.InterpreterResponse
import interpreter.SendLiteral
import interpreter.state.PrintScriptInterpreterState

class ReadInputInterpreter(val interpreter: Interpreter<in Literal, PrintScriptInterpreterState>) : Interpreter<ReadInputAst, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: ReadInputAst,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        interpreterState.readInput()
            .let { (literal, state) ->
                literal
                    ?.let { interpreter.interpret(literal, state) }
                    ?: SendLiteral(state)
            }
}
