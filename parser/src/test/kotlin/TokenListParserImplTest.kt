import ast.AssignationAst
import ast.AssignationDeclarationAst
import ast.DeclarationAst
import ast.NumberType
import ast.PrintlnAst
import ast.StringConcatenation
import ast.StringLiteral
import ast.StringType
import org.junit.jupiter.api.Test
import parser.PrintScriptAstParser
import token.AssignationToken
import token.DeclarationToken
import token.DivToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.LetToken
import token.NumberTypeToken
import token.PrintlnToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
import token.StringTypeToken
import token.SumToken
import token.Token
import token.VariableNameToken
class TokenListParserImplTest {

    @Test
    fun declarationParserWorksWithNumber() {
        val declaration: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declarationParser = PrintScriptAstParser()
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
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declarationParser = PrintScriptAstParser()
        val declarationAst = declarationParser.parse(declaration)

        assert(declarationAst !is DeclarationAst)
    }

    @Test
    fun declarationParserWorksWithString() {
        val declaration: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declarationParser = PrintScriptAstParser()
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
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
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
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("World", 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        when (assignationAst) {
            is AssignationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun assignationWorksWithOperation() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun assignationWorksWithComplexOperation() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            DivToken(0, 0),
            IntNumberLiteralToken(3, 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun declAssiParserWorks() {
        val declAssi: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiParser = PrintScriptAstParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun declAssiWorksWithOperation() {
        val declAssi: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            DivToken(0, 0),
            IntNumberLiteralToken(3, 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiParser = PrintScriptAstParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun declAssiWithWrongAssignation() {
        val declAssi: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            DivToken(0, 0),
            IntNumberLiteralToken(3, 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiParser = PrintScriptAstParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun printParserWorks() {
        val print: List<Token> = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val printParser = PrintScriptAstParser()
        val printAst = printParser.parse(print)

        when (printAst) {
            is PrintlnAst -> assert((((printAst.value())as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value == "Hello World")
            else -> assert(false)
        }
    }

    @Test
    fun printWorksWithConcat() {
        val print: List<Token> = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("World", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val printParser = PrintScriptAstParser()
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
