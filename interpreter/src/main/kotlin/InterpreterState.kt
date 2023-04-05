interface InterpreterState
interface PrintlnState : InterpreterState {
    fun add(value: String): PrintlnState
}
