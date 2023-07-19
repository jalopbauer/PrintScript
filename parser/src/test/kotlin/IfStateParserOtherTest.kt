
import ast.IfStatement
import ast.TrueLiteral
import org.junit.jupiter.api.Test
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserRespose.SendToken
import parser.parserState.IfParserState
import parser.parserState.RegularParserState
import token.ElseToken
import token.LeftCurlyBracketsToken
import token.LeftParenthesisToken
import token.LetToken
import token.PrintlnToken
import token.RightCurlyBracketsToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
class IfStateParserOtherTest {

    @Test
    fun testInvalidSentence() {
        val declaration = IfParserState(
            IfStatement(TrueLiteral, listOf()),
            RegularParserState(
                listOf(
                    ElseToken(0, 0),
                    LeftCurlyBracketsToken(0, 0)
                )
            )
        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        assert(declarationAst is SendToken)
    }

    @Test
    fun testIfElseAstFound() {
        val declaration = IfParserState(
            IfStatement(
                TrueLiteral,
                listOf()
            ),
            RegularParserState(
                listOf(
                    ElseToken(0, 0),
                    LeftCurlyBracketsToken(0, 0),
                    PrintlnToken(0, 0),
                    LeftParenthesisToken(0, 0),
                    StringLiteralToken("", 0, 0),
                    RightParenthesisToken(0, 0),
                    SemicolonToken(0, 0),
                    RightCurlyBracketsToken(0, 0)
                )
            )
        )
        assert(declaration.hasEndedProperly())
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        assert(declarationAst is AstFound)
    }

    @Test
    fun testIfAstFound() {
        val declaration = IfParserState(
            IfStatement(TrueLiteral, listOf())
        ).addToken(LetToken(0, 0))
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        assert(declarationAst is AstFound)
    }

    @Test
    fun testSendToken() {
        val declaration = IfParserState(
            IfStatement(TrueLiteral, listOf()),
            RegularParserState(
                listOf()
            )
        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        assert(declarationAst is SendToken)
    }
}
