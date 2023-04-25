import org.junit.jupiter.api.Test
import state.PrintScriptInterpreterStateI
import state.PrintlnInterpreterStateI
import state.VariableInterpreterStateI

class DeclarationInterpreterTest {

    @Test
    fun testInitializeVariableSuccess() {
        val declarationAst = DeclarationAst(
            VariableNameNode("testVariable1"),
            StringType
        )

        val interpreter = PrintScriptInterpreter()
        val interpreterResponse = interpreter.interpret(
            declarationAst,
            PrintScriptInterpreterStateI(
                PrintlnInterpreterStateI(
                    listOf(),
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
            )
        )

        assert(interpreterResponse is VariableAlreadyExistsError)
    }
}
