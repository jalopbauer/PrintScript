import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.ClosedBracketToken
import token.NumberLiteralToken
import token.OpenBracketToken
import token.OperatorHighToken
import token.OperatorLowToken
import token.Token
import token.TokenName

class ShuntingYardTest {

    private val simpleOperation: List<Token> = listOf(
        NumberLiteralToken(1, 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        NumberLiteralToken(2, 0, 0)
    )
    private val operationSumMUl: List<Token> = listOf(
        NumberLiteralToken(1, 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        NumberLiteralToken(2, 0, 0),
        OperatorLowToken(TokenName.MULT, 0, 0),
        NumberLiteralToken(3, 0, 0)
    )
    private val operationMulSum: List<Token> = listOf(
        NumberLiteralToken(1, 0, 0),
        OperatorLowToken(TokenName.MULT, 0, 0),
        NumberLiteralToken(2, 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        NumberLiteralToken(3, 0, 0)
    )
    private val bracketsOperation: List<Token> = listOf(
        NumberLiteralToken(1, 0, 0),
        OperatorLowToken(TokenName.MULT, 0, 0),
        OpenBracketToken(TokenName.LEFT_PARENTHESIS, 0, 0),
        NumberLiteralToken(2, 0, 0),
        OperatorHighToken(TokenName.SUM, 0, 0),
        NumberLiteralToken(3, 0, 0),
        ClosedBracketToken(TokenName.RIGHT_PARENTHESIS, 0, 0)
    )
    private val complexBracketsOperation: List<Token> = listOf(
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
        NumberLiteralToken(5, 0, 0)
    )

    @Test
    fun sHWWorksWithSimpleEquation() {
        val shuntingYard = ShuntingYardImpl()
        val simpleTree = shuntingYard.check(simpleOperation)
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[0].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[1].tokenName())
        assertEquals(TokenName.SUM, simpleTree[2].tokenName())
    }

    @Test
    fun sHWWorksWithSMEquation() {
        val shuntingYard = ShuntingYardImpl()
        val simpleTree = shuntingYard.check(operationSumMUl)
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[0].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[1].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[2].tokenName())
        assertEquals(TokenName.MULT, simpleTree[3].tokenName())
        assertEquals(TokenName.SUM, simpleTree[4].tokenName())
    }

    @Test
    fun sHWWorksWithMSEquation() {
        val shuntingYard = ShuntingYardImpl()
        val simpleTree = shuntingYard.check(operationMulSum)
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[0].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[1].tokenName())
        assertEquals(TokenName.MULT, simpleTree[2].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[3].tokenName())
        assertEquals(TokenName.SUM, simpleTree[4].tokenName())
    }

    @Test
    fun sHWWorksWithBracketsEquation() {
        val shuntingYard = ShuntingYardImpl()
        val simpleTree = shuntingYard.check(bracketsOperation)
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[0].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[1].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[2].tokenName())
        assertEquals(TokenName.SUM, simpleTree[3].tokenName())
        assertEquals(TokenName.MULT, simpleTree[4].tokenName())
    }

    @Test
    fun sHWWorksWithComplexBracketsEquation() {
        val shuntingYard = ShuntingYardImpl()
        val simpleTree = shuntingYard.check(complexBracketsOperation)
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[0].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[1].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[2].tokenName())
        assertEquals(TokenName.SUM, simpleTree[3].tokenName())
        assertEquals(TokenName.MULT, simpleTree[4].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[5].tokenName())
        assertEquals(TokenName.DIV, simpleTree[6].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[7].tokenName())
        assertEquals(TokenName.SUB, simpleTree[8].tokenName())
    }
}
