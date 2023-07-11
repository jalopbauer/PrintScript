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
            is ConditionBlock -> ConditionBlockInterpreter().interpret(abstractSyntaxTree, interpreterState)
            else -> SentencesInterpreter().interpret(abstractSyntaxTree, interpreterState)
        }
}
