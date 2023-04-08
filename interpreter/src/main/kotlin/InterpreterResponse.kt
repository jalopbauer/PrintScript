sealed interface InterpreterResponse
interface Error : InterpreterResponse {
    fun message(): String
}

interface InterpreterState : InterpreterResponse {
    fun add(value: String): InterpreterState
    fun addVariableValueToPrintln(value: String): InterpreterResponse
    fun addValueToPrintln(value: String): InterpreterResponse
    fun addValueToPrintln(value: Int): InterpreterResponse
}
