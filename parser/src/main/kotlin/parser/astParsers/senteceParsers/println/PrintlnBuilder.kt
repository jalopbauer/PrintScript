package parser.astParsers.senteceParsers.println

import ast.DoubleNumberLiteral
import ast.FalseLiteral
import ast.IntNumberLiteral
import ast.NumberLiteral
import ast.PrintlnAst
import ast.PrintlnAstParameter
import ast.ReadInputAst
import ast.StringLiteral
import ast.TrueLiteral
import ast.VariableNameNode
import parser.astParsers.AstBuilder
import parser.shuntingYard.ShuntingYardImpl
import token.DoubleNumberLiteralToken
import token.FalseLiteralToken
import token.IntNumberLiteralToken
import token.NumberLiteralToken
import token.Token
import token.TrueLiteralToken
import validlistoftokens.BooleanLiteralParameter
import validlistoftokens.NumberLiteralParameter
import validlistoftokens.OperationValidListOfConcatTokens
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.ReadInputValidListOfTokens
import validlistoftokens.StringLiteralOrStringConcatValidListOfTokens
import validlistoftokens.VariableParameter

class PrintlnBuilder : AstBuilder<PrintlnValidListOfTokens, PrintlnAst> {
    override fun build(validListOfTokens: PrintlnValidListOfTokens): PrintlnAst {
        return when (val parameter = validListOfTokens.printLnParameterValidListOfTokens) {
            is VariableParameter -> PrintlnAst(VariableNameNode(parameter.variableToken.value))
            is StringLiteralOrStringConcatValidListOfTokens -> PrintlnAst(getConcat(parameter.stringOrConcat))
            is NumberLiteralParameter -> PrintlnAst(getNumberNode(parameter.numberLiteralToken))
            is BooleanLiteralParameter ->
                PrintlnAst(
                    when (parameter.booleanLiteralToken) {
                        is FalseLiteralToken -> FalseLiteral
                        is TrueLiteralToken -> TrueLiteral
                    }
                )
            is ReadInputValidListOfTokens -> PrintlnAst(ReadInputAst(StringLiteral(parameter.stringLiteralToken.value)))
            is OperationValidListOfConcatTokens -> PrintlnAst(ShuntingYardImpl().orderNumber(parameter.operationConcat))
        }
    }

    private fun getConcat(stringOrConcat: List<Token>): PrintlnAstParameter {
        return ShuntingYardImpl().orderString(stringOrConcat)
    }

    private fun getNumberNode(numberLiteralToken: NumberLiteralToken): NumberLiteral {
        return when (numberLiteralToken) {
            is IntNumberLiteralToken -> IntNumberLiteral(numberLiteralToken.value)
            is DoubleNumberLiteralToken -> DoubleNumberLiteral(numberLiteralToken.value)
        }
    }
}
