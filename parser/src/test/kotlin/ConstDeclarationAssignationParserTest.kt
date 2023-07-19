
import ast.ConstAssignationDeclarationAst
import org.junit.jupiter.api.Test
import parser.parserRespose.AstFound
import parser.parserState.RegularParserState
import parser.parserState.RegularParserStateParser
import token.AssignationToken
import token.BooleanTypeToken
import token.ConstToken
import token.DeclarationToken
import token.FalseLiteralToken
import token.SemicolonToken
import token.VariableNameToken
class ConstDeclarationAssignationParserTest {

    @Test
    fun constAssignationDeclarationAstFalse() {
        val declaration = RegularParserState(
            listOf(
                ConstToken(0, 0),
                VariableNameToken("test", 0, 0),
                DeclarationToken(0, 0),
                BooleanTypeToken(0, 0),
                AssignationToken(0, 0),
                FalseLiteralToken(0, 0),
                SemicolonToken(0, 0)
            )
        )
        val declarationParser = RegularParserStateParser()
        val declarationAst = declarationParser.parse(declaration)
        when (declarationAst) {
            is AstFound -> assert(declarationAst.abstractSyntaxTree is ConstAssignationDeclarationAst)
            else -> assert(false)
        }
    }
}
