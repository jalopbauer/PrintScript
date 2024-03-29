
import org.junit.jupiter.api.Test
import parser.PrintScriptParser
import parser.parserRespose.SendToken
import parser.parserRespose.SentenceInvalid
import parser.parserState.RegularParserState
import parser.parserState.RegularParserStateParser
import token.DeclarationToken
import token.LetToken
import token.SemicolonToken
import token.VariableNameToken

class StateParserOtherTest {

    @Test
    fun testInvalidSentence() {
        val declaration = RegularParserState(
            listOf(
                LetToken(0, 0),
                VariableNameToken("test", 0, 0),
                DeclarationToken(0, 0),
                SemicolonToken(0, 0)
            )
        )
        val declarationParser = RegularParserStateParser()
        val declarationAst = declarationParser.parse(declaration)
        assert(declarationAst is SentenceInvalid)
        assert(
            (declarationAst as SentenceInvalid).parserState.tokens.zip(declaration.tokens)
                .none { it.first != it.second }
        )
    }

    @Test
    fun testSendToken() {
        val declaration = RegularParserState(
            listOf(
                LetToken(0, 0),
                VariableNameToken("test", 0, 0),
                DeclarationToken(0, 0)
            )
        )
        assert(!declaration.hasEndedProperly())
        val declarationParser = PrintScriptParser()
        val declarationAst = declarationParser.parse(declaration)
        assert(declarationAst is SendToken)
    }
}
