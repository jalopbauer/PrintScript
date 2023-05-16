import token.BooleanTypeToken
import token.DoubleNumberLiteralToken
import token.FalseLiteralToken
import token.IntNumberLiteralToken
import token.NumberLiteralToken
import token.NumberTypeToken
import token.StringTypeToken
import token.Token
import token.TrueLiteralToken
import token.TypeToken
interface AstBuilder<T : ValidListOfTokens> {
    fun build(validListOfTokens: T): AbstractSyntaxTree

    fun findType(token: TypeToken): Type =
        when (token) {
            is BooleanTypeToken -> BooleanType
            is NumberTypeToken -> NumberType()
            is StringTypeToken -> StringType
        }
}
class PrintlnBuilder : AstBuilder<PrintlnValidListOfTokens> {
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
            is ReadInputParameter -> PrintlnAst(ReadInputAst())
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
        val type = findType(validListOfTokens.type)
        return DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type)
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
        val type = findType(validListOfTokens.type)
        when (type) {
            is NumberType -> return AssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            is StringType -> return AssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderString(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
        }
        return AssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), findType(validListOfTokens.type)))
    }
}
