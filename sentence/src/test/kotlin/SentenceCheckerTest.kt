import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import token.OperatorHighToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.TokenWithoutValue
import token.VariableLiteralToken

class SentenceCheckerTest {
    val declaration = listOf<Token>(
        TokenWithoutValue(TokenName.LET, 0, 0),
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.DECLARATION, 0, 0),
        TokenWithoutValue(TokenName.STRING_TYPE, 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )
    val assignationString = listOf<Token>(
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
        StringLiteralToken("Hello", 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        StringLiteralToken("World", 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )
    val assignationNumber = listOf<Token>()
    val assignationComplexNumber = listOf<Token>()
    val print = listOf<Token>()
    val declAssi = listOf<Token>()
    val errorPosition = listOf<Token>()
    val errorLength = listOf<Token>()

    @Test
    fun checkerWorksWithDeclaration() {
        val sentenceChecker = SentenceCheckerImpl()
        val declarationType = sentenceChecker.check(declaration)
        Assertions.assertEquals(DeclarationType, declarationType)
    }

    @Test
    fun checkerWorksWithAssignationString() {
        val sentenceChecker = SentenceCheckerImpl()
        val assignationTypeString = sentenceChecker.check(assignationString)
        Assertions.assertEquals(AssignationType, assignationTypeString)
    }

    @Test
    fun checkerWorksWithAssignationNumber() {
    }

    @Test
    fun checkerWorksWithPrint() {
    }

    @Test
    fun checkerWorksWithDeclAssi() {
    }

    @Test
    fun checkWorksWithLengthError() {
    }
}
