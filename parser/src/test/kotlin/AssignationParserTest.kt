
import ast.AssignationAst
import org.junit.jupiter.api.Test
import parser.parserRespose.AstFound
import parser.parserState.RegularParserState
import parser.parserState.RegularParserStateParser
import token.AssignationToken
import token.FalseLiteralToken
import token.LeftParenthesisToken
import token.ReadInputToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
import token.TrueLiteralToken
import token.VariableNameToken
class AssignationParserTest {

    @Test
    fun assignationFalse() {
        val declaration = RegularParserState(
            listOf(
                VariableNameToken("test", 0, 0),
                AssignationToken(0, 0),
                FalseLiteralToken(0, 0),
                SemicolonToken(0, 0)
            )
        )
        val declarationParser = RegularParserStateParser()
        val declarationAst = declarationParser.parse(declaration)
        when (declarationAst) {
            is AstFound -> assert(declarationAst.abstractSyntaxTree is AssignationAst)
            else -> assert(false)
        }
    }

    @Test
    fun assignationReadInput() {
        val declaration = RegularParserState(
            listOf(
                VariableNameToken("test", 0, 0),
                AssignationToken(0, 0),
                ReadInputToken(0, 0),
                LeftParenthesisToken(0, 0),
                StringLiteralToken("", 0, 0),
                RightParenthesisToken(0, 0),
                SemicolonToken(0, 0)
            )
        )
        val declarationParser = RegularParserStateParser()
        val declarationAst = declarationParser.parse(declaration)
        when (declarationAst) {
            is AstFound -> assert(declarationAst.abstractSyntaxTree is AssignationAst)
            else -> assert(false)
        }
    }

    @Test
    fun assignationTrue() {
        val declaration = RegularParserState(
            listOf(
                VariableNameToken("test", 0, 0),
                AssignationToken(0, 0),
                TrueLiteralToken(0, 0),
                SemicolonToken(0, 0)
            )
        )
        val declarationParser = RegularParserStateParser()
        val declarationAst = declarationParser.parse(declaration)
        when (declarationAst) {
            is AstFound -> assert(declarationAst.abstractSyntaxTree is AssignationAst)
            else -> assert(false)
        }
    }
}
