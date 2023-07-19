
import ast.PrintlnAst
import org.junit.jupiter.api.Test
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserState.RegularParserState
import token.DivToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.PrintlnToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.VariableNameToken

class PrintlnParserTest {

    @Test
    fun assignationFalse() {
        val declaration = RegularParserState(
            listOf(
                PrintlnToken(2, position = 0),
                LeftParenthesisToken(lineNumber = 2, position = 7),
                VariableNameToken(value = "pi", lineNumber = 2, position = 8),
                DivToken(lineNumber = 2, position = 11),
                IntNumberLiteralToken(value = 2, lineNumber = 2, position = 13),
                RightParenthesisToken(lineNumber = 2, position = 14),
                SemicolonToken(lineNumber = 2, position = 15)
            )

        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        when (declarationAst) {
            is AstFound -> assert(declarationAst.abstractSyntaxTree is PrintlnAst)
            else -> assert(false)
        }
    }
}
