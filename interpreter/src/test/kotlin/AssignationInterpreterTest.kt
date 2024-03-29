import ast.AssignationAst
import ast.BooleanType
import ast.Div
import ast.DoubleNumberLiteral
import ast.FalseLiteral
import ast.IntNumberLiteral
import ast.Literal
import ast.Mult
import ast.NumberType
import ast.Operation
import ast.ReadInputAst
import ast.StringConcatenation
import ast.StringLiteral
import ast.StringType
import ast.Sub
import ast.Sum
import ast.VariableNameNode
import interpreter.InterpreterError
import interpreter.PrintScriptInterpreter
import interpreter.SendLiteral
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.PrintScriptInterpreterStateI
import interpreter.state.PrintlnInterpreterStateI
import interpreter.state.VariableInterpreterStateI
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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

    @Test
    fun testSetSumResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = 69
        val rightNumberValue = 420
        val expectedResult: Double = (leftNumberValue + rightNumberValue).toDouble()
        val operation = Operation(IntNumberLiteral(leftNumberValue), Sum(), IntNumberLiteral(rightNumberValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is DoubleNumberLiteral)
                assertEquals(expectedResult, (it as DoubleNumberLiteral).number)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetSubResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = 69
        val rightNumberValue = 420
        val expectedResult: Double = (leftNumberValue - rightNumberValue).toDouble()
        val operation = Operation(IntNumberLiteral(leftNumberValue), Sub(), IntNumberLiteral(rightNumberValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is DoubleNumberLiteral)
                assertEquals(expectedResult, (it as DoubleNumberLiteral).number)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetMultResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = 69
        val rightNumberValue = 420
        val expectedResult: Double = (leftNumberValue * rightNumberValue).toDouble()
        val operation = Operation(IntNumberLiteral(leftNumberValue), Mult(), IntNumberLiteral(rightNumberValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is DoubleNumberLiteral)
                assertEquals(expectedResult, (it as DoubleNumberLiteral).number)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetDivResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = 69
        val rightNumberValue = 69
        val expectedResult: Double = (leftNumberValue / rightNumberValue).toDouble()
        val operation = Operation(IntNumberLiteral(leftNumberValue), Div(), IntNumberLiteral(rightNumberValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is DoubleNumberLiteral)
                assertEquals(expectedResult, (it as DoubleNumberLiteral).number)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetSumVariableResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val variableWithPreviousValue = "variableWithPreviousValue"

        val leftNumberValue = 69.3
        val rightNumberValue = 420
        val expectedResult: Double = (leftNumberValue + rightNumberValue)
        val operation = Operation(DoubleNumberLiteral(leftNumberValue), Sum(), VariableNameNode(variableWithPreviousValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType,
                    variableWithPreviousValue to NumberType
                ),
                variableLiteralMap = mapOf(
                    variableWithPreviousValue to IntNumberLiteral(rightNumberValue)
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is DoubleNumberLiteral)
                assertEquals(expectedResult, (it as DoubleNumberLiteral).number)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetSumVariableNotDefinedResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val variableWithPreviousValue = "variableWithPreviousValue"

        val leftNumberValue = 69.3
        val rightNumberValue = 420
        val expectedResult: Double = (leftNumberValue + rightNumberValue)
        val operation = Operation(DoubleNumberLiteral(leftNumberValue), Sum(), VariableNameNode(variableWithPreviousValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType
                ),
                variableLiteralMap = mapOf()
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }

    @Test
    fun testSetSumStringVariableResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val variableWithPreviousValue = "variableWithPreviousValue"

        val leftNumberValue = 69
        val rightNumberValue = "420"
        val operation = Operation(IntNumberLiteral(leftNumberValue), Sum(), VariableNameNode(variableWithPreviousValue))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to NumberType,
                    variableWithPreviousValue to StringType
                ),
                variableLiteralMap = mapOf(
                    variableWithPreviousValue to StringLiteral(rightNumberValue)
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }

    @Test
    fun testSetConcatenationStringResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = "69"
        val rightNumberValue = "420"
        val expectedResult = leftNumberValue + rightNumberValue
        val operation = StringConcatenation(listOf(StringLiteral(leftNumberValue), StringLiteral(rightNumberValue)))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is StringLiteral)
                assertEquals(expectedResult, (it as StringLiteral).value)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetConcatenationStringNumberResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = "69"
        val rightNumberValue = 420
        val expectedResult = leftNumberValue + rightNumberValue
        val operation = StringConcatenation(listOf(StringLiteral(leftNumberValue), IntNumberLiteral(rightNumberValue)))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is StringLiteral)
                assertEquals(expectedResult, (it as StringLiteral).value)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetConcatenationNumberNumberResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val leftNumberValue = 69
        val rightNumberValue = 420
        val expectedResult = "$leftNumberValue$rightNumberValue"
        val operation = StringConcatenation(listOf(IntNumberLiteral(leftNumberValue), IntNumberLiteral(rightNumberValue)))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is StringLiteral)
                assertEquals(expectedResult, (it as StringLiteral).value)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetConcatenationVariableIntResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val numberValue = 420
        val expectedResult = "$numberValue"

        val variableWithPreviousValue = "variableWithPreviousValue"

        val operation = StringConcatenation(listOf(VariableNameNode(variableWithPreviousValue)))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableLiteralMap = mapOf(
                    variableWithPreviousValue to IntNumberLiteral(numberValue)
                ),
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType,
                    variableWithPreviousValue to NumberType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is StringLiteral)
                assertEquals(expectedResult, (it as StringLiteral).value)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun testSetConcatenationVariableBooleanResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val variableWithPreviousValue = "variableWithPreviousValue"

        val operation = StringConcatenation(listOf(VariableNameNode(variableWithPreviousValue)))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableLiteralMap = mapOf(
                    variableWithPreviousValue to FalseLiteral
                ),
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType,
                    variableWithPreviousValue to BooleanType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }

    @Test
    fun testSetConcatenationVariableStringResultToVariable() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)

        val numberValue = "420"

        val variableWithPreviousValue = "variableWithPreviousValue"

        val operation = StringConcatenation(listOf(VariableNameNode(variableWithPreviousValue)))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableLiteralMap = mapOf(
                    variableWithPreviousValue to StringLiteral(numberValue)
                ),
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType,
                    variableWithPreviousValue to StringType
                )
            )
        )

        val assignationAst = AssignationAst(
            variableToBeSet,
            operation
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
        (interpreterResponse as PrintScriptInterpreterState).get(variableToBeSet)
            ?.let {
                assert(it is StringLiteral)
                assertEquals(numberValue, (it as StringLiteral).value)
            }
            ?: {
                assert(false)
            }
    }

    @Test
    fun readInputSendLiteral() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)
        val valeue = ReadInputAst(StringLiteral("hello"))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableLiteralMap = mapOf(),
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType
                )
            )
        )
        val assignationAst = AssignationAst(
            variableToBeSet,
            valeue
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is SendLiteral)
    }

    @Test
    fun readInputGetVal() {
        val variableToBeSetName = "variableToBeSet"
        val variableToBeSet = VariableNameNode(variableToBeSetName)
        val valeue = ReadInputAst(StringLiteral("hello"))

        val assignationInterpreterState = getState(
            VariableInterpreterStateI(
                variableLiteralMap = mapOf(),
                variableTypeMap = mapOf(
                    variableToBeSetName to StringType
                )
            )
        ).setReadInput(StringLiteral("hello"))
        val assignationAst = AssignationAst(
            variableToBeSet,
            valeue
        )

        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            assignationAst,
            assignationInterpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
    }
}
