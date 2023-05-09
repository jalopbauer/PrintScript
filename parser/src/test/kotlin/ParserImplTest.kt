import org.junit.jupiter.api.Test
import token.ClosedBracketToken
import token.IntNumberLiteralToken
import token.OpenBracketToken
import token.OperatorLowToken
import token.StringLiteralToken
import token.SumToken
import token.Token
import token.TokenName
import token.TokenWithoutValue
import token.VariableLiteralToken
class ParserImplTest {

    @Test
    fun declarationParserWorksWithNumber() {
        val declaration: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.DECLARATION, 0, 0),
            TokenWithoutValue(TokenName.NUMBER_TYPE, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)

        when (declarationAst) {
            is DeclarationAst -> assert(
                declarationAst.rightValue() is NumberType &&
                    declarationAst.leftValue().value() == "test"
            )
            else -> assert(false)
        }
    }

    @Test
    fun declarationParserDoesntWork() {
        val declaration: List<Token> = listOf(
            TokenWithoutValue(TokenName.VARIABLE, 0, 0),
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.DECLARATION, 0, 0),
            TokenWithoutValue(TokenName.NUMBER_TYPE, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)

        assert(declarationAst !is DeclarationAst)
    }

    @Test
    fun declarationParserWorksWithString() {
        val declaration: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.DECLARATION, 0, 0),
            TokenWithoutValue(TokenName.STRING_TYPE, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)

        when (declarationAst) {
            is DeclarationAst -> assert(
                declarationAst.rightValue() is StringType &&
                    declarationAst.leftValue().value() == "test"
            )
            else -> assert(false)
        }
    }

    @Test
    fun assignationParserWorks() {
        val assignation: List<Token> = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            StringLiteralToken("Hello World", 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val assignationParser = PrintScriptParser()
        val assignationAst = assignationParser.parse(assignation)

        when (assignationAst) {
            is AssignationAst -> assert(
                assignationAst.leftValue().value() == "test" &&
                    ((assignationAst.rightValue() as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value.equals("Hello World")
            )
            else -> assert(false)
        }
    }

    @Test
    fun assignationWorksWithConcat() {
        val assignation: List<Token> = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            StringLiteralToken("Hello", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("World", 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val assignationParser = PrintScriptParser()
        val assignationAst = assignationParser.parse(assignation)

        when (assignationAst) {
            is AssignationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun assignationWorksWithOperation() {
        val assignation: List<Token> = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val assignationParser = PrintScriptParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun assignationWorksWithComplexOperation() {
        val assignation: List<Token> = listOf(
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            OperatorLowToken(TokenName.DIV, 0, 0),
            IntNumberLiteralToken(3, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val assignationParser = PrintScriptParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun declAssiParserWorks() {
        val declAssi: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.DECLARATION, 0, 0),
            TokenWithoutValue(TokenName.STRING_TYPE, 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            StringLiteralToken("Hello World", 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val declAssiParser = PrintScriptParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun declAssiWorksWithOperation() {
        val declAssi: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.DECLARATION, 0, 0),
            TokenWithoutValue(TokenName.NUMBER_TYPE, 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            OperatorLowToken(TokenName.DIV, 0, 0),
            IntNumberLiteralToken(3, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val declAssiParser = PrintScriptParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun declAssiWithWrongAssignation() {
        val declAssi: List<Token> = listOf(
            TokenWithoutValue(TokenName.LET, 0, 0),
            VariableLiteralToken("test", 0, 0),
            TokenWithoutValue(TokenName.DECLARATION, 0, 0),
            TokenWithoutValue(TokenName.STRING_TYPE, 0, 0),
            TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            OperatorLowToken(TokenName.DIV, 0, 0),
            IntNumberLiteralToken(3, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val declAssiParser = PrintScriptParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun printParserWorks() {
        val print: List<Token> = listOf(
            TokenWithoutValue(TokenName.PRINT, 0, 0),
            OpenBracketToken(TokenName.LEFT_PARENTHESIS, 0, 0),
            StringLiteralToken("Hello World", 0, 0),
            ClosedBracketToken(TokenName.RIGHT_PARENTHESIS, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val printParser = PrintlnParser()
        val printAst = printParser.parse(print)

        when (printAst) {
            is PrintlnAst -> assert((((printAst.value())as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value == "Hello World")
            else -> assert(false)
        }
    }

    @Test
    fun printWorksWithConcat() {
        val print: List<Token> = listOf(
            TokenWithoutValue(TokenName.PRINT, 0, 0),
            OpenBracketToken(TokenName.LEFT_PARENTHESIS, 0, 0),
            StringLiteralToken("Hello", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("World", 0, 0),
            ClosedBracketToken(TokenName.RIGHT_PARENTHESIS, 0, 0),
            TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
        )
        val printParser = PrintScriptParser()
        val printAst = printParser.parse(print)

        when (printAst) {
            is PrintlnAst -> assert(
                (((printAst.value())as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value == "Hello" &&
                    (((printAst.value())as StringConcatenation).concatenationParameterValues[1] as StringLiteral).value == "World"
            )
            else -> assert(false)
        }
    }
}
