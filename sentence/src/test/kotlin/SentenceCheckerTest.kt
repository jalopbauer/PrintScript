import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import token.ClosedBracketToken
import token.NumberLiteralToken
import token.OpenBracketToken
import token.OperatorHighToken
import token.OperatorLowToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.TokenWithoutValue
import token.VariableLiteralToken

class SentenceCheckerTest {
    // let test: String;
    private val declaration = listOf<Token>(
        TokenWithoutValue(TokenName.LET, 0, 0),
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.DECLARATION, 0, 0),
        TokenWithoutValue(TokenName.STRING_TYPE, 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )

    // test = "Hello" + "World";
    private val assignationString = listOf<Token>(
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
        StringLiteralToken("Hello", 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        StringLiteralToken("World", 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )

    // test = 1 + 2
    private val assignationNumber = listOf<Token>(
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
        NumberLiteralToken(1, 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        NumberLiteralToken(2, 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )

    // test = 1*(2+3)/4-5
    private val assignationComplexNumber = listOf<Token>(
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
        NumberLiteralToken(1, 0, 0),
        OperatorLowToken(TokenName.MULT, 0, 0),
        OpenBracketToken(TokenName.LEFT_PARENTHESIS, 0, 0),
        NumberLiteralToken(2, 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        NumberLiteralToken(3, 0, 0),
        ClosedBracketToken(TokenName.RIGHT_PARENTHESIS, 0, 0),
        OperatorLowToken(TokenName.DIV, 0, 0),
        NumberLiteralToken(4, 0, 0),
        OperatorHighToken(TokenName.SUB, 0, 0),
        NumberLiteralToken(5, 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )

    // print(test);
    private val print = listOf<Token>(
        TokenWithoutValue(TokenName.PRINT, 0, 0),
        TokenWithoutValue(TokenName.LEFT_PARENTHESIS, 0, 0),
        StringLiteralToken("Hello World", 0, 0),
        TokenWithoutValue(TokenName.RIGHT_PARENTHESIS, 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )

    // let test: String = "Hello" + "World"
    private val declAssi = listOf<Token>(
        TokenWithoutValue(TokenName.LET, 0, 0),
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.DECLARATION, 0, 0),
        TokenWithoutValue(TokenName.STRING_TYPE, 0, 0),
        TokenWithoutValue(TokenName.ASSIGNATION, 0, 0),
        StringLiteralToken("Hello", 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        StringLiteralToken("World", 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )
    private val errorPosition = listOf<Token>(
        TokenWithoutValue(TokenName.LET, 0, 0),
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.DECLARATION, 0, 0),
        StringLiteralToken("Hello World", 0, 0),
        TokenWithoutValue(TokenName.SEMICOLON, 0, 0)
    )
    private val errorLength = listOf<Token>(
        TokenWithoutValue(TokenName.LET, 0, 0),
        VariableLiteralToken("test", 0, 0),
        TokenWithoutValue(TokenName.DECLARATION, 0, 0)
    )

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
        val sentenceChecker = SentenceCheckerImpl()
        val assignationTypeNumber = sentenceChecker.check(assignationNumber)
        Assertions.assertEquals(AssignationType, assignationTypeNumber)
    }

    @Test
    fun checkerWorksWithAssignationComplexNumber() {
        val sentenceChecker = SentenceCheckerImpl()
        val assignationTypeNumber = sentenceChecker.check(assignationComplexNumber)
        Assertions.assertEquals(AssignationType, assignationTypeNumber)
    }

    @Test
    fun checkerWorksWithPrint() {
        val sentenceChecker = SentenceCheckerImpl()
        val printType = sentenceChecker.check(print)
        Assertions.assertEquals(PrintlnType, printType)
    }

    @Test
    fun checkerWorksWithDeclAssi() {
        val sentenceChecker = SentenceCheckerImpl()
        val assignationDeclarationType = sentenceChecker.check(declAssi)
        Assertions.assertEquals(AssignationDeclarationType, assignationDeclarationType)
    }

    @Test
    fun checkDoesntWorkWithLengthError() {
        val sentenceChecker = SentenceCheckerImpl()
        val errorType = sentenceChecker.check(errorLength)
        Assertions.assertEquals(ErrorType, errorType)
    }

    @Test
    fun checkDoesntWorkWithWrongSentenceError() {
        val sentenceChecker = SentenceCheckerImpl()
        val errorType = sentenceChecker.check(errorPosition)
        Assertions.assertEquals(ErrorType, errorType)
    }
}
