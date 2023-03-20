
import lexer.Exceptions.IllegalStringException
import lexer.Lexer
import lexer.LexerImp
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import token.Token
import token.TokenName
import java.util.LinkedList


class LexetTest {

    val line = "let test: string = 'test';"
    val eqline = "let test: number = 1 + 1 / 2 - 1;"
    val printline = "println('test');"
    val lineRepeated = "let test: string = 'test';let test: string = 'test';"
    val invalidTokenOne = "let Test: 'error'"
    val invalidTokenTwo = "let err0r = jaja"


    @Test
    fun CreateEmptyTokenList() {
        val lexer: Lexer = LexerImp();
        val tokenList: List<Token> = lexer.buildTokenList("");
        assertEquals(0, tokenList.size);
    }

    @Test
    fun DivideSentenceInTokenList(){
        val lexer: Lexer = LexerImp();
        val tokenList: List<Token> = lexer.buildTokenList(line);
        for (item in tokenList){
            println(item.value)
        }
        assertEquals(7, tokenList.size);
    }

    @Test
    fun TokenListHasCorrectTokenName(){
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(line)
        assertEquals(TokenName.LET, tokenList[0].tokenIdentifier)
        assertEquals(TokenName.VARIABLE, tokenList[1].tokenIdentifier)
        assertEquals(TokenName.DECLARATION, tokenList[2].tokenIdentifier)
        assertEquals(TokenName.STRING_TYPE, tokenList[3].tokenIdentifier)
        assertEquals(TokenName.ASSIGNATION, tokenList[4].tokenIdentifier)
        assertEquals(TokenName.STRING_LITERAL, tokenList[5].tokenIdentifier)
        assertEquals(TokenName.SEMICOLON, tokenList[6].tokenIdentifier)
    }

    @Test
    fun TokenListHasCorrectTokenNameForEquation(){
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(eqline)
        assertEquals(TokenName.LET, tokenList[0].tokenIdentifier)
        assertEquals(TokenName.VARIABLE, tokenList[1].tokenIdentifier)
        assertEquals(TokenName.DECLARATION, tokenList[2].tokenIdentifier)
        assertEquals(TokenName.NUMBER_TYPE, tokenList[3].tokenIdentifier)
        assertEquals(TokenName.ASSIGNATION, tokenList[4].tokenIdentifier)
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[5].tokenIdentifier)
        assertEquals(TokenName.SUM, tokenList[6].tokenIdentifier)
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[7].tokenIdentifier)
        assertEquals(TokenName.DIV, tokenList[8].tokenIdentifier)
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[9].tokenIdentifier)
        assertEquals(TokenName.SUB, tokenList[10].tokenIdentifier)
        assertEquals(TokenName.NUMBER_LITERAL, tokenList[11].tokenIdentifier)
        assertEquals(TokenName.SEMICOLON, tokenList[12].tokenIdentifier)
    }

    @Test
    fun TokenListHasCorrectNameForPrint(){
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(printline)
        assertEquals(5, tokenList.size)
        assertEquals(TokenName.PRINT, tokenList[0].tokenIdentifier)
        assertEquals(TokenName.LEFT_PARENTHESIS, tokenList[1].tokenIdentifier)
        assertEquals(TokenName.STRING_LITERAL, tokenList[2].tokenIdentifier)
        assertEquals(TokenName.RIGHT_PARENTHESIS, tokenList[3].tokenIdentifier)
        assertEquals(TokenName.SEMICOLON, tokenList[4].tokenIdentifier)
    }

    @Test
    fun TokensAreInCorrectPosition(){
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(line)
        assertEquals(0, tokenList[0].position)
        assertEquals(4, tokenList[1].position)
        assertEquals(8, tokenList[2].position)
        assertEquals(10, tokenList[3].position)
        assertEquals(17, tokenList[4].position)
        assertEquals(19, tokenList[5].position)
        assertEquals(25, tokenList[6].position)
    }

    @Test
    fun TokensAreInCorrectLine(){
        val lexer: Lexer = LexerImp()
        val tokenList: List<Token> = lexer.buildTokenList(lineRepeated)
        assertEquals(0, tokenList[0].lineNumber)
        assertEquals(0, tokenList[0].lineNumber)
        assertEquals(1, tokenList[7].lineNumber)
    }

    @Test
    fun InvalidTokenShouldThrowException(){
        val lexer: Lexer = LexerImp();
        org.junit.jupiter.api.assertThrows<IllegalStringException>{
            val firstInvalid = lexer.buildTokenList(invalidTokenOne)
        }
        org.junit.jupiter.api.assertThrows<IllegalStringException>{
            val secondInvalid: List<Token> = lexer.buildTokenList(invalidTokenTwo)
        }
    }
}