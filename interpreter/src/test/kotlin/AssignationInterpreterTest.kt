import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import state.PrintScriptInterpreterStateI
import state.PrintlnInterpreterStateI
import state.VariableInterpreterStateI

class AssignationInterpreterTest {

    private fun getState(variableInterpreterStateI: VariableInterpreterStateI) =
        PrintScriptInterpreterStateI(
            PrintlnInterpreterStateI(
                listOf(),
                variableInterpreterStateI
            )
        )

    @Test
    fun testSetIntNumberLiteralToVariable() {
        val previousValue = IntNumberLiteral(0)
        val newValue = IntNumberLiteral(1)
        val variableName = "testVariable1"

        variableInitializationTest(newValue, variableName, previousValue)
    }

    @Test
    fun testSetStringLiteralToVariable() {
        val previousValue = StringLiteral("0")
        val newValue = StringLiteral("1")
        val variableName = "testVariable2"

        variableInitializationTest(newValue, variableName, previousValue)
    }

    @Test
    fun testSetDoubleNumberLiteralToVariable() {
        val previousValue = DoubleNumberLiteral(0.0)
        val newValue = DoubleNumberLiteral(1.0)
        val variableName = "testVariable3"

        variableInitializationTest(newValue, variableName, previousValue)
    }

    private fun variableInitializationTest(newValue: Literal, variableName: String, previousValue: Literal) {
        val type = newValue.type()
        val variableNameNode = VariableNameNode(variableName)

        val assignationAst = AssignationAst(
            variableNameNode,
            newValue
        )

        val interpreter = PrintScriptInterpreter()

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                listOf(),
                mapOf(
                    variableName to type
                ),
                mapOf(
                    variableName to previousValue
                )
            )
        )
        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        if (interpreterResponse is PrintScriptInterpreterStateI) {
            interpreterResponse.get(variableNameNode)
                ?.let { assertEquals(newValue, it) }
                ?: assert(false)
        }
    }

    @Test
    fun testSetVariableNameNodeToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val variableToObtainValueFromName = "variableToObtainValue"
        val variableToObtainValueFrom = VariableNameNode(variableToObtainValueFromName)
        val variableToObtainValueFromValue = IntNumberLiteral(1)

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                listOf(),
                mapOf(
                    variableToBeSetName to variableToObtainValueFromValue.type(),
                    variableToObtainValueFromName to variableToObtainValueFromValue.type()
                ),
                mapOf(
                    variableToObtainValueFromName to variableToObtainValueFromValue
                )

            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            variableToObtainValueFrom
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        if (interpreterResponse is PrintScriptInterpreterStateI) {
            interpreterResponse.get(variableToBeSet)
                ?.let {
                    assertEquals(variableToObtainValueFromValue, it)
                }
                ?: {
                    assert(false)
                }
        } else {
            println(interpreterResponse)
            assert(false)
        }
    }
}
