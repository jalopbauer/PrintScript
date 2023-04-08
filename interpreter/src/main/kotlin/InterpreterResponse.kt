sealed interface InterpreterResponse
interface Error : InterpreterResponse {
    fun message(): String
}

interface InterpreterState : InterpreterResponse {
    fun addVariableValueToPrintln(value: String): InterpreterResponse
    fun addValueToPrintln(value: String): InterpreterResponse
    fun initializeVariable(value: String, value1: String): InterpreterResponse
}
