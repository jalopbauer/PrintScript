package interpreter.declaration

import ast.DeclarationAst
import ast.VariableInstance
import interpreter.Interpreter
import interpreter.InterpreterResponse
import interpreter.state.VariableInterpreterState

class DeclarationInterpreter : Interpreter<DeclarationAst, VariableInterpreterState> {
    override fun interpret(abstractSyntaxTree: DeclarationAst, interpreterState: VariableInterpreterState): InterpreterResponse =
        interpreterState.initializeVariable(VariableInstance(abstractSyntaxTree.leftValue(), abstractSyntaxTree.rightValue()))
}
