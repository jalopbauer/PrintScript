package interpreter

import ast.AbstractSyntaxTree
import ast.ConditionBlock
import interpreter.state.PrintScriptInterpreterState

class PrintScriptInterpreter : Interpreter<AbstractSyntaxTree, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AbstractSyntaxTree,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        when (abstractSyntaxTree) {
            is ConditionBlock -> TODO()
            else -> SentencesInterpreter().interpret(abstractSyntaxTree, interpreterState)
        }
}
