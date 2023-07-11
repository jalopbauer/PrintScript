package interpreter.state

import ast.Literal

interface ReadInputInterpreterState : InterpreterState {
    fun readInput(): Pair<Literal?, ReadInputInterpreterState>
}
