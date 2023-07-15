
import ast.ConcatenationParameter
import ast.IntNumberLiteral
import ast.PrintlnAst
import ast.StringConcatenation
import ast.StringLiteral
import ast.StringType
import ast.VariableNameNode
import interpreter.InterpreterError
import interpreter.InterpreterResponse
import interpreter.PrintScriptInterpreter
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.PrintScriptInterpreterStateI
import interpreter.state.PrintlnInterpreterStateI
import interpreter.state.VariableInterpreterStateI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PrintlnInterpreterTest {

    private val interpreter = PrintScriptInterpreter()

    private fun getState(variableInterpreterStateI: VariableInterpreterStateI) =
        PrintScriptInterpreterStateI(
            PrintlnInterpreterStateI(
                listOf(),
                variableInterpreterStateI
            )
        )

    private fun testExpectedValue(interpreterResponse: InterpreterResponse, valueToTest: String) =
        if (interpreterResponse is PrintScriptInterpreterState) {
            val print = interpreterResponse.print()
            assertEquals(valueToTest, print.first.firstOrNull())
            assert(print.second.print().first.isEmpty())
        } else {
            assert(false)
        }

    @Test
    fun testVariableNameNodeAsPrintlnParameterExists() {
        val printlnAstParameter = VariableNameNode("testVariable")
        val printlnAst = PrintlnAst(printlnAstParameter)
        val printlnInterpreterState = getState(
            VariableInterpreterStateI(
                listOf(),
                mapOf(
                    "testVariable" to StringType
                ),
                mapOf(
                    "testVariable" to StringLiteral("valueToTest")
                )
            )
        )
        val interpreterResponse = interpreter.interpret(printlnAst, printlnInterpreterState)

        testExpectedValue(interpreterResponse, "valueToTest")
    }

    @Test
    fun testVariableNameNodeWithVariableUndefined() {
        val printlnAstParameter = VariableNameNode("testVariable")
        val printlnAst = PrintlnAst(printlnAstParameter)
        val printlnInterpreterState = getState(
            VariableInterpreterStateI()
        )
        val interpreterResponse = interpreter.interpret(printlnAst, printlnInterpreterState)

        assert(interpreterResponse is InterpreterError)
    }

    @Test
    fun testNumberLiteralExists() {
        val printlnAstParameter = IntNumberLiteral(10)
        val printlnAst = PrintlnAst(printlnAstParameter)
        val printlnInterpreterState = getState(VariableInterpreterStateI())
        val interpreterResponse = interpreter.interpret(printlnAst, printlnInterpreterState)

        testExpectedValue(interpreterResponse, "10")
    }

    @Test
    fun testStringLiteralExists() {
        val printlnAstParameter = StringLiteral("valueToTest")
        val printlnAst = PrintlnAst(printlnAstParameter)
        val printlnInterpreterState = getState(VariableInterpreterStateI())
        val interpreterResponse = interpreter.interpret(printlnAst, printlnInterpreterState)

        testExpectedValue(interpreterResponse, "valueToTest")
    }

    @Test
    fun testStringConcatenation() {
        val concatenation = listOf<ConcatenationParameter>(
            StringLiteral("valueToTest"),
            StringLiteral("valueToTest")
        )
        val stringConcatenation = StringConcatenation(concatenation)
        val printlnAst = PrintlnAst(stringConcatenation)

        val printlnInterpreterState = getState(VariableInterpreterStateI())
        val interpreterResponse = interpreter.interpret(printlnAst, printlnInterpreterState)

        testExpectedValue(interpreterResponse, "valueToTestvalueToTest")
    }
}
