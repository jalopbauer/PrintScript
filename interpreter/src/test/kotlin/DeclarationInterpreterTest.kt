import org.junit.jupiter.api.Test
import state.PrintScriptInterpreterStateI
import state.PrintlnInterpreterStateI
import state.VariableInterpreterStateI

class DeclarationInterpreterTest {

    private fun getState(variableInterpreterStateI: VariableInterpreterStateI) =
        PrintScriptInterpreterStateI(
            PrintlnInterpreterStateI(
                listOf(),
                variableInterpreterStateI
            )
        )

    @Test
    fun testInitializeVariableExists() {
        val declarationAst = DeclarationAst(
            VariableNameNode("testVariable1"),
            StringType
        )

        val interpreter = PrintScriptInterpreter()
        val declarationInterpreterState = getState(
            VariableInterpreterStateI(
                listOf(),
                mapOf(
                    "testVariable1" to StringType
                ),
                mapOf(
                    "testVariable1" to StringLiteral("valueToTest")
                )
            )
        )
        val interpreterResponse = interpreter.interpret(
            declarationAst,
            declarationInterpreterState
        )

        assert(interpreterResponse is VariableAlreadyExistsError)
    }

    @Test
    fun testInitializeVariableSuccess() {
        val declarationAst = DeclarationAst(
            VariableNameNode("testVariable1"),
            StringType
        )

        val interpreter = PrintScriptInterpreter()
        val declarationInterpreterState = getState(
            VariableInterpreterStateI()
        )
        val interpreterResponse = interpreter.interpret(
            declarationAst,
            declarationInterpreterState
        )

        if (interpreterResponse is PrintScriptInterpreterStateI) {
            assert(interpreterResponse.isVariableDefined(VariableNameNode("testVariable1")))
        } else {
            assert(false)
        }
    }
}
