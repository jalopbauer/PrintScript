package interpreter

import ast.AbstractSyntaxTree
import interpreter.state.InterpreterState

interface Interpreter<T : AbstractSyntaxTree, U : InterpreterState> {
    fun interpret(abstractSyntaxTree: T, interpreterState: U): InterpreterResponse
}
