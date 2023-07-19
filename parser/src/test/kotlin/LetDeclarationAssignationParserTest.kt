
import ast.LetAssignationDeclarationAst
import org.junit.jupiter.api.Test
import parser.parserRespose.AstFound
import parser.parserState.RegularParserState
import parser.parserState.RegularParserStateParser
import token.AssignationToken
import token.BooleanTypeToken
import token.DeclarationToken
import token.FalseLiteralToken
import token.LetToken
import token.SemicolonToken
import token.VariableNameToken
class LetDeclarationAssignationParserTest {

    @Test
    fun letAssignationDeclarationAstFalse() {
        val declaration = RegularParserState(
            listOf(
                LetToken(0, 0),
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
            is AstFound -> assert(declarationAst.abstractSyntaxTree is LetAssignationDeclarationAst)
            else -> assert(false)
        }
    }
}
