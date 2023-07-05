import ast.DeclarationAst
import ast.StringLiteral
import ast.StringType
import ast.VariableNameNode
import interpreter.InterpreterError
import interpreter.PrintScriptInterpreter
import interpreter.state.PrintScriptInterpreterStateI
import interpreter.state.PrintlnInterpreterStateI
import interpreter.state.VariableInterpreterStateI
import org.junit.jupiter.api.Test

class DeclarationInterpreterTest {

    private fun getState(variableInterpreterStateI: VariableInterpreterStateI) =
        PrintScriptInterpreterStateI(
            PrintlnInterpreterStateI(
                null,
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

        assert(interpreterResponse is InterpreterError)
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
