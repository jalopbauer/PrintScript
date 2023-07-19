import ast.FalseLiteral
import ast.IfStatement
import ast.PrintlnAst
import ast.StringLiteral
import ast.TrueLiteral
import ast.VariableNameNode
import interpreter.InterpreterError
import interpreter.PrintScriptInterpreter
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.PrintScriptInterpreterStateI
import interpreter.state.PrintlnInterpreterStateI
import interpreter.state.VariableInterpreterStateI
import org.junit.jupiter.api.Test

class ConditionBlockTest {

    private fun getState(variableInterpreterStateI: VariableInterpreterStateI) =
        PrintScriptInterpreterStateI(
            PrintlnInterpreterStateI(
                listOf(),
                variableInterpreterStateI
            )
        )

    @Test
    fun ifStatementTrue() {
        val conditionBlock = IfStatement(TrueLiteral, listOf(PrintlnAst(StringLiteral("print"))))
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        )
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            conditionBlock,
            interpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
    }

    @Test
    fun ifStatementFalse() {
        val conditionBlock = IfStatement(FalseLiteral, listOf(PrintlnAst(StringLiteral("print"))))
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        )
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            conditionBlock,
            interpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
    }

    @Test
    fun ifStatementVariableNameNode() {
        val conditionBlock = IfStatement(VariableNameNode("variable"), listOf(PrintlnAst(StringLiteral("print"))))
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        )
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            conditionBlock,
            interpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }
}
