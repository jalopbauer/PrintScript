
import lexer.Lexer
import lexer.LexerImp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Token
import token.TokenName

class LexerTest {

    val line = "let test: string = 'test';"
    val eqline = "let test: number = 1 + 1 / 2 - 1;"
    val printline = "println('test');"
    val lineRepeated = "let test: string = 'test';let test: string = 'test';"
    val invalidTokenOne = "let Test: 'error'"
    val invalidTokenTwo = "let err0r = jaja"

    @Test
    fun createEmptyTokenList() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList("")
        assertEquals(0, tokenList.size)
    }

    @Test
    fun divideSentenceInTokenList() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(line)
        assertEquals(7, tokenList.size)
    }

    @Test
    fun tokenListHasCorrectTokenName() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(line)
        assertEquals(TokenName.LET, tokenList[0].tokenName())
        assertEquals(TokenName.VARIABLE, tokenList[1].tokenName())
        assertEquals(TokenName.DECLARATION, tokenList[2].tokenName())
        assertEquals(TokenName.STRING_TYPE, tokenList[3].tokenName())
        assertEquals(TokenName.ASSIGNATION, tokenList[4].tokenName())
        assertEquals(TokenName.STRING_LITERAL, tokenList[5].tokenName())
        assertEquals(TokenName.SEMICOLON, tokenList[6].tokenName())
    }

    @Test
    fun tokenListHasCorrectTokenNameForEquation() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(eqline)
        assertEquals(TokenName.LET, tokenList[0].tokenName())
        assertEquals(TokenName.VARIABLE, tokenList[1].tokenName())
        assertEquals(TokenName.DECLARATION, tokenList[2].tokenName())
        assertEquals(TokenName.NUMBER_TYPE, tokenList[3].tokenName())
        assertEquals(TokenName.ASSIGNATION, tokenList[4].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[5].tokenName())
        assertEquals(TokenName.SUM, tokenList[6].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[7].tokenName())
        assertEquals(TokenName.DIV, tokenList[8].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[9].tokenName())
        assertEquals(TokenName.SUB, tokenList[10].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[11].tokenName())
        assertEquals(TokenName.SEMICOLON, tokenList[12].tokenName())
    }

    @Test
    fun tokenListHasCorrectNameForPrint() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(printline)
        assertEquals(5, tokenList.size)
        assertEquals(TokenName.PRINT, tokenList[0].tokenName())
        assertEquals(TokenName.LEFT_PARENTHESIS, tokenList[1].tokenName())
        assertEquals(TokenName.STRING_LITERAL, tokenList[2].tokenName())
        assertEquals(TokenName.RIGHT_PARENTHESIS, tokenList[3].tokenName())
        assertEquals(TokenName.SEMICOLON, tokenList[4].tokenName())
    }

    @Test
    fun tokensAreInCorrectPosition() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(line)
        assertEquals(0, tokenList[0].position())
        assertEquals(4, tokenList[1].position())
        assertEquals(8, tokenList[2].position())
        assertEquals(10, tokenList[3].position())
        assertEquals(17, tokenList[4].position())
        assertEquals(19, tokenList[5].position())
        assertEquals(25, tokenList[6].position())
    }

    @Test
    fun tokensAreInCorrectLine() {
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(lineRepeated)
        assertEquals(0, tokenList[0].lineNumber())
        assertEquals(0, tokenList[0].lineNumber())
        assertEquals(1, tokenList[7].lineNumber())
    }
}
