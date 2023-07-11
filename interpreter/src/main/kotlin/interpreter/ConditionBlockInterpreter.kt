package interpreter

import ast.ConditionBlock
import interpreter.state.PrintScriptInterpreterState

class ConditionBlockInterpreter(private val sentencesInterpreter: SentencesInterpreter = SentencesInterpreter()) : Interpreter<ConditionBlock, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: ConditionBlock,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        abstractSyntaxTree.getSentences()
            ?.let { sentences ->
                val initial: InterpreterResponse = interpreterState
                sentences.fold(initial) { acc, abstractSyntaxTree ->
                    when (acc) {
                        is PrintScriptInterpreterState -> sentencesInterpreter.interpret(abstractSyntaxTree, acc)
                        else -> acc
                    }
                }
            } ?: interpreterState
}
