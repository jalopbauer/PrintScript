package interpreter

import ast.AbstractSyntaxTree
import ast.AssignationAst
import ast.AssignationDeclarationAst
import ast.ConstAssignationDeclarationAst
import ast.DeclarationAst
import ast.LetAssignationDeclarationAst
import ast.PrintlnAst
import ast.VariableInstance
import interpreter.assignation.AssignationParameterInterpreter
import interpreter.print.PrintlnParameterInterpreter
import interpreter.state.InterpreterState
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.VariableInterpreterState

interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse
}

class PrintScriptInterpreter : Interpreter<AbstractSyntaxTree, PrintScriptInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AbstractSyntaxTree,
        interpreterState: PrintScriptInterpreterState
    ): InterpreterResponse =
        when (abstractSyntaxTree) {
            is PrintlnAst -> PrintlnParameterInterpreter().interpret(abstractSyntaxTree.value(), interpreterState)
            is DeclarationAst -> DeclarationInterpreter().interpret(abstractSyntaxTree, interpreterState)
            is AssignationAst -> AssignationParameterInterpreter().interpret(abstractSyntaxTree, interpreterState)
            is AssignationDeclarationAst -> AssignationDeclarationInterpreter().interpret(abstractSyntaxTree, interpreterState)
            else -> InterpreterError()
        }
}

class DeclarationInterpreter : Interpreter<DeclarationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: DeclarationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        interpreterState.initializeVariable(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
}

class DeclarationConstInterpreter : Interpreter<DeclarationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: DeclarationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        interpreterState.initializeConst(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
}

class AssignationDeclarationInterpreter : Interpreter<AssignationDeclarationAst, VariableInterpreterState> {
    override fun interpret(
        abstractSyntaxTree: AssignationDeclarationAst,
        interpreterState: VariableInterpreterState
    ): InterpreterResponse {
        val stateOrError = when (abstractSyntaxTree) {
            is ConstAssignationDeclarationAst -> DeclarationConstInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
            is LetAssignationDeclarationAst -> DeclarationInterpreter().interpret(abstractSyntaxTree.rightValue(), interpreterState)
        }
        return if (stateOrError !is InterpreterError) {
            AssignationParameterInterpreter().interpret(abstractSyntaxTree.leftValue(), stateOrError as VariableInterpreterState)
        } else {
            stateOrError
        }
    }
}
