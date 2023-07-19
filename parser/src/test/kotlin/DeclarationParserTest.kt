
import ast.DeclarationAst
import org.junit.jupiter.api.Test
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserState.RegularParserState
import token.BooleanTypeToken
import token.DeclarationToken
import token.LetToken
import token.SemicolonToken
import token.VariableNameToken

class DeclarationParserTest {

    @Test
    fun letAssignationDeclarationAstFalse() {
        val declaration = RegularParserState(
            listOf(
                LetToken(0, 0),
                VariableNameToken("test", 0, 0),
                DeclarationToken(0, 0),
                BooleanTypeToken(0, 0),
                SemicolonToken(0, 0)
            )
        )
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        when (declarationAst) {
            is AstFound -> assert(declarationAst.abstractSyntaxTree is DeclarationAst)
            else -> assert(false)
        }
    }
}
