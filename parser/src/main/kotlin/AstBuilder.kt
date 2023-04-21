import token.DoubleNumberLiteralToken
import token.IntNumberLiteralToken
import token.NumberLiteralToken
import token.StringLiteralToken
import token.Token

interface AstBuilder<T : ValidListOfTokens> {
    fun build(validListOfTokens: T): AbstractSyntaxTree

    fun findType(token: Token): Type {
        when (token) {
            is StringLiteralToken -> return StringType
            is IntNumberLiteralToken -> return IntType
            is DoubleNumberLiteralToken -> return DoubleType
        }
        return StringType
    }
}

class PrintlnBuilder : AstBuilder<PrintlnValidListOfTokens> {
    override fun build(validListOfTokens: PrintlnValidListOfTokens): PrintlnAst {
        return when (val parameter = validListOfTokens.printLnParameterValidListOfTokens) {
            is VariableParameter -> PrintlnAst(VariableNameNode(parameter.variableToken.value))
            is StringLiteralOrStringConcatValidListOfTokens -> PrintlnAst(getConcat(parameter.stringOrConcat))
            is NumberLiteralParameter -> PrintlnAst(getNumberNode(parameter.numberLiteralToken))
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

class DeclarationBuilder : AstBuilder<DeclarationValidListOfTokens> {
    override fun build(validListOfTokens: DeclarationValidListOfTokens): DeclarationAst {
        return DeclarationAst(VariableNameNode(validListOfTokens.variable.value), findType(validListOfTokens.type))
    }
}
class AssignationBuilder : AstBuilder<AssignationValidListOfTokens> {
    override fun build(validListOfTokens: AssignationValidListOfTokens): AbstractSyntaxTree {
        if (OperationValidListOfTokensBuilder().validateChain(validListOfTokens.content)) {
            return AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content))
        }
        return AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderString(validListOfTokens.content))
    }
}
class DeclarationAssignationBuilder : AstBuilder<DeclarationAssignationValidListOfTokens> {
    override fun build(validListOfTokens: DeclarationAssignationValidListOfTokens): AbstractSyntaxTree {
        return AssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), findType(validListOfTokens.type)))
    }
}
