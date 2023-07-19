import ast.AssignationAst
import ast.ConstAssignationDeclarationAst
import ast.DeclarationAst
import ast.LetAssignationDeclarationAst
import ast.NumberType
import ast.ReadInputAst
import ast.StringLiteral
import ast.StringType
import ast.VariableNameNode
import interpreter.InterpreterError
import interpreter.PrintScriptInterpreter
import interpreter.SendLiteral
import interpreter.state.PrintScriptInterpreterState
import interpreter.state.PrintScriptInterpreterStateI
import interpreter.state.PrintlnInterpreterStateI
import interpreter.state.VariableInterpreterStateI
import org.junit.jupiter.api.Test

class DeclarationAssignationInterpreterTest {

    private fun getState(variableInterpreterStateI: VariableInterpreterStateI) =
        PrintScriptInterpreterStateI(
            PrintlnInterpreterStateI(
                listOf(),
                variableInterpreterStateI
            )
        )

    @Test
    fun readInputEmpty() {
        val variableNameNode = VariableNameNode("helloVar")
        val type = StringType
        val declaration = DeclarationAst(variableNameNode, type)
        val assignationParameter = ReadInputAst(StringLiteral("hello"))
        val assignation = AssignationAst(variableNameNode, assignationParameter)
        val constAssignationDeclarationAst = ConstAssignationDeclarationAst(assignation, declaration)
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        )
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            constAssignationDeclarationAst,
            interpreterState
        )

        assert(interpreterResponse is SendLiteral)
    }

    @Test
    fun readInputNotEmpty() {
        val variableNameNode = VariableNameNode("helloVar")
        val type = StringType
        val declaration = DeclarationAst(variableNameNode, type)
        val assignationParameter = ReadInputAst(StringLiteral("hello"))
        val assignation = AssignationAst(variableNameNode, assignationParameter)
        val constAssignationDeclarationAst = ConstAssignationDeclarationAst(assignation, declaration)
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        ).setReadInput(StringLiteral("hello"))
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            constAssignationDeclarationAst,
            interpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
    }

    @Test
    fun constAlreadySet() {
        val variableName = "helloVar"
        val variableNameNode = VariableNameNode(variableName)
        val type = StringType
        val declaration = DeclarationAst(variableNameNode, type)
        val assignationParameter = StringLiteral("hello")
        val assignation = AssignationAst(variableNameNode, assignationParameter)
        val constAssignationDeclarationAst = ConstAssignationDeclarationAst(assignation, declaration)
        val interpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableName to type
                ),
                consts = setOf(variableName),

                variableLiteralMap = mapOf(
                    variableName to assignationParameter
                )
            )
        ).setReadInput(StringLiteral("hello"))
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            constAssignationDeclarationAst,
            interpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }

    @Test
    fun setDifferentTypesOfPreviousVar() {
        val variableName = "helloVar"
        val variableNameNode = VariableNameNode(variableName)
        val type = StringType
        val declaration = DeclarationAst(VariableNameNode("newVar"), NumberType)
        val assignationParameter = StringLiteral("hello")
        val assignation = AssignationAst(VariableNameNode("newVar"), variableNameNode)
        val constAssignationDeclarationAst = ConstAssignationDeclarationAst(assignation, declaration)
        val interpreterState = getState(
            VariableInterpreterStateI(
                variableTypeMap = mapOf(
                    variableName to type
                ),
                consts = setOf(variableName),

                variableLiteralMap = mapOf(
                    variableName to assignationParameter
                )
            )
        ).setReadInput(StringLiteral("hello"))
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            constAssignationDeclarationAst,
            interpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }

    @Test
    fun letAssignationDeclaration() {
        val variableNameNode = VariableNameNode("helloVar")
        val type = StringType
        val declaration = DeclarationAst(variableNameNode, type)
        val assignationParameter = ReadInputAst(StringLiteral("hello"))
        val assignation = AssignationAst(variableNameNode, assignationParameter)
        val letAssignationDeclarationAst = LetAssignationDeclarationAst(assignation, declaration)
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        ).setReadInput(StringLiteral("hello"))
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            letAssignationDeclarationAst,
            interpreterState
        )

        assert(interpreterResponse is PrintScriptInterpreterState)
    }

    @Test
    fun letAssignationDeclarationWrongType() {
        val variableNameNode = VariableNameNode("helloVar")
        val type = NumberType
        val declaration = DeclarationAst(variableNameNode, type)
        val assignationParameter = ReadInputAst(StringLiteral("hello"))
        val assignation = AssignationAst(variableNameNode, assignationParameter)
        val letAssignationDeclarationAst = LetAssignationDeclarationAst(assignation, declaration)
        val interpreterState = getState(
            VariableInterpreterStateI(variableTypeMap = mapOf())
        ).setReadInput(StringLiteral("hello"))
        val interpreter = PrintScriptInterpreter()

        val interpreterResponse = interpreter.interpret(
            letAssignationDeclarationAst,
            interpreterState
        )

        assert(interpreterResponse is InterpreterError)
    }
}
