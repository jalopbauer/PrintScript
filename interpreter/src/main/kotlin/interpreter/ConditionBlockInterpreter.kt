package interpreter

import ast.BooleanLiteral
import ast.ConditionBlock
import ast.VariableNameNode
import interpreter.state.PrintScriptInterpreterState

class ConditionBlockInterpreter(private val sentencesInterpreter: SentencesInterpreter = SentencesInterpreter()) : Interpreter<ConditionBlock, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: ConditionBlock,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        when (val conditionBlockParameter = abstractSyntaxTree.getConditionBlockParameter()) {
            is BooleanLiteral -> conditionBlockParameter
            is VariableNameNode -> interpreterState.get(conditionBlockParameter)
        }.let { booleanLiteral ->
            if (booleanLiteral is BooleanLiteral) {
                abstractSyntaxTree.getSentences(booleanLiteral)
                    ?.let { sentences ->
                        val initial: InterpreterResponse = interpreterState
                        sentences.fold(initial) { acc, abstractSyntaxTree ->
                            when (acc) {
                                is PrintScriptInterpreterState -> sentencesInterpreter.interpret(
                                    abstractSyntaxTree,
                                    acc
                                )

                                else -> acc
                            }
                        }
                    } ?: interpreterState
            } else {
                InterpreterError("If: Not boolean")
            }
        }
}
