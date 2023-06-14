package interpreter

import ast.AbstractSyntaxTree
import ast.AssignationAst
import ast.AssignationDeclarationAst
import ast.BooleanLiteral
import ast.ConstAssignationDeclarationAst
import ast.DeclarationAst
import ast.DoubleNumberLiteral
import ast.IntNumberLiteral
import ast.LetAssignationDeclarationAst
import ast.Literal
import ast.NumberLiteral
import ast.Operation
import ast.PrintlnAst
import ast.PrintlnAstParameter
import ast.StringConcatenation
import ast.StringLiteral
import ast.VariableInstance
import ast.VariableNameNode
import interpreter.state.InterpreterState
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.PrintlnInterpreterState
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
class AssignationParameterInterpreter : Interpreter<AssignationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: AssignationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        when (val assignationParameterNode = abstractSyntaxTree.rightValue()) {
            is IntNumberLiteral,
            is StringLiteral,
            is DoubleNumberLiteral,
            is BooleanLiteral -> interpreterState.setLiteralToVariable(abstractSyntaxTree.leftValue(), assignationParameterNode as Literal)
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