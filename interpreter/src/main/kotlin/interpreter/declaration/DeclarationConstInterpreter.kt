package interpreter.declaration

import ast.DeclarationAst
import ast.VariableInstance
import interpreter.Interpreter
import interpreter.InterpreterResponse
import interpreter.state.VariableInterpreterState

class DeclarationConstInterpreter : Interpreter<DeclarationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: DeclarationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        interpreterState.initializeConst(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
}
