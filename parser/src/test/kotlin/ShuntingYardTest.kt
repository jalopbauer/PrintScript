import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.shuntingYard.ShuntingYardImpl
import token.DivToken
import token.IntNumberLiteralToken
import token.LeftParenthesisToken
import token.MultToken
import token.RightParenthesisToken
import token.SubToken
import token.SumToken
import token.Token
import token.TokenName
import token.VariableNameToken

class ShuntingYardTest {

    private val simpleOperation: List<Token> = listOf(
        IntNumberLiteralToken(1, 0, 0),
        SumToken(0, 0),
        IntNumberLiteralToken(2, 0, 0)
    )
    private val operationSumMUl: List<Token> = listOf(
        IntNumberLiteralToken(1, 0, 0),
        SumToken(0, 0),
        IntNumberLiteralToken(2, 0, 0),
        MultToken(0, 0),
        IntNumberLiteralToken(3, 0, 0)
    )
    private val operationMulSum: List<Token> = listOf(
        IntNumberLiteralToken(5, 0, 0),
        MultToken(0, 0),
        IntNumberLiteralToken(5, 0, 0),
        SubToken(0, 0),
        IntNumberLiteralToken(8, 0, 0)
    )
    private val bracketsOperation: List<Token> = listOf(
        IntNumberLiteralToken(1, 0, 0),
        MultToken(0, 0),
        LeftParenthesisToken(0, 0),
        IntNumberLiteralToken(2, 0, 0),
        SumToken(0, 0),
        IntNumberLiteralToken(3, 0, 0),
        RightParenthesisToken(0, 0)
    )
    private val complexBracketsOperation: List<Token> = listOf(
        IntNumberLiteralToken(1, 0, 0),
        MultToken(0, 0),
        LeftParenthesisToken(0, 0),
        IntNumberLiteralToken(2, 0, 0),
        SumToken(0, 0),
        IntNumberLiteralToken(3, 0, 0),
        RightParenthesisToken(0, 0),
        DivToken(0, 0),
        IntNumberLiteralToken(4, 0, 0),
        SubToken(0, 0),
        IntNumberLiteralToken(5, 0, 0)
    )

    private val functionWithValue: List<Token> = listOf(
        VariableNameToken(value = "pi", lineNumber = 2, position = 8),
        DivToken(lineNumber = 2, position = 11),
        IntNumberLiteralToken(value = 2, lineNumber = 2, position = 13)
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
        assertEquals(TokenName.SUB, simpleTree[4].tokenName())
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

    @Test
    fun sHWWorksWithSimpleEquationVariable() {
        val shuntingYard = ShuntingYardImpl()
        val simpleTree = shuntingYard.check(functionWithValue)
        assertEquals(TokenName.VARIABLE, simpleTree[0].tokenName())
        assertEquals(TokenName.NUMBER_LITERAL, simpleTree[1].tokenName())
        assertEquals(TokenName.DIV, simpleTree[2].tokenName())
    }
}
