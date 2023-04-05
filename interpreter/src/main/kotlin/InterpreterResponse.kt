sealed interface InterpreterResponse
interface Error : InterpreterResponse {
    fun message(): String
}
interface InterpreterState : InterpreterResponse
interface PrintlnResponse : InterpreterResponse

interface PrintLnInterpreterState : PrintlnResponse, InterpreterState {
    fun add(value: String): PrintLnInterpreterState
}
