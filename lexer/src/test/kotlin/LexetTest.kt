
import lexer.Lexer
import lexer.LexerImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import token.Token
import token.TokenName
import java.util.LinkedList


class LexetTest {

    val line = "let test: string = 'test';"
    val eqline = "let test: number = 1 + 1 / 2 - 1;"

    @Test
    fun CreateEmptyTokenList() {
        var lexer: Lexer = LexerImp();
        var tokenList: List<Token> = lexer.buildTokenList("");
        assertEquals(0, tokenList.size);
    }

    @Test
    fun DivideSentenceInTokenList(){
        var lexer: Lexer = LexerImp();
        var tokenList: List<Token> = lexer.buildTokenList(line);
        for (item in tokenList){
            println(item.value)
        }
        assertEquals(7, tokenList.size);
    }

    @Test
    fun TokenListHasCorrectTokenName(){
        var lexer: Lexer = LexerImp()
        var tokenList: List<Token> = lexer.buildTokenList(line)
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
        var lexer: Lexer = LexerImp()
        var tokenList: List<Token> = lexer.buildTokenList(eqline)
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
}