sealed interface InterpreterResponse
interface Error : InterpreterResponse {
    fun message(): String
}
interface InterpreterState : InterpreterResponse
interface PrintlnResponse : InterpreterResponse

interface PrintLnInterpreterState : PrintlnResponse, InterpreterState {
    fun add(value: String): PrintLnInterpreterState
}
interface DeclarationResponse : InterpreterResponse
class DeclarationError : DeclarationResponse, Error {
    override fun message(): String {
        TODO("Not yet implemented")
    }
}
interface DeclarationInterpreterState : DeclarationResponse, InterpreterState {
    fun add(variableName: String): DeclarationResponse
}
