package parser

import ast.AbstractSyntaxTree
import ast.AssignationAst
import ast.BooleanType
import ast.ConstAssignationDeclarationAst
import ast.DeclarationAst
import ast.DoubleNumberLiteral
import ast.FalseLiteral
import ast.IntNumberLiteral
import ast.LetAssignationDeclarationAst
import ast.NumberLiteral
import ast.NumberType
import ast.PrintlnAst
import ast.PrintlnAstParameter
import ast.ReadInputAst
import ast.StringType
import ast.TrueLiteral
import ast.Type
import ast.VariableNameNode
import parser.shuntingYard.ShuntingYardImpl
import token.BooleanLiteralToken
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
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.BooleanLiteralParameter
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.NumberLiteralParameter
import validlistoftokens.OperationValidListOfTokensBuilder
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.ReadInputParameter
import validlistoftokens.StringLiteralOrStringConcatValidListOfTokens
import validlistoftokens.ValidListOfTokens
import validlistoftokens.VariableParameter
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
        val content = validListOfTokens.content
        return if (content.size == 1 && content.component1() is BooleanLiteralToken) {
            val parameter = when (content.component1() as BooleanLiteralToken) {
                is FalseLiteralToken -> FalseLiteral
                is TrueLiteralToken -> TrueLiteral
            }
            AssignationAst(VariableNameNode(validListOfTokens.variable.value), parameter)
        } else if (OperationValidListOfTokensBuilder().validateChain(content)) {
            AssignationAst(
                VariableNameNode(validListOfTokens.variable.value),
                ShuntingYardImpl().orderNumber(
                    content
                )
            )
        } else {
            AssignationAst(
                VariableNameNode(validListOfTokens.variable.value),
                ShuntingYardImpl().orderString(
                    content
                )
            )
        }
    }
}
class DeclarationAssignationBuilder : AstBuilder<DeclarationAssignationValidListOfTokens> {
    override fun build(validListOfTokens: DeclarationAssignationValidListOfTokens): AbstractSyntaxTree =
        when (val type = findType(validListOfTokens.type)) {
            is NumberType -> LetAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            is StringType -> LetAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderString(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            is BooleanType -> {
                val parameter = when (validListOfTokens.content.component1() as BooleanLiteralToken) {
                    is FalseLiteralToken -> FalseLiteral
                    is TrueLiteralToken -> TrueLiteral
                }
                LetAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), parameter), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            }
            else -> LetAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), findType(validListOfTokens.type)))
        }
}

class ConstDeclarationAssignationBuilder : AstBuilder<ConstDeclarationAssignationValidListOfTokens> {
    override fun build(validListOfTokens: ConstDeclarationAssignationValidListOfTokens): AbstractSyntaxTree =
        when (val type = findType(validListOfTokens.type)) {
            is NumberType -> ConstAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            is StringType -> ConstAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderString(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            is BooleanType -> {
                val parameter = when (validListOfTokens.content.component1() as BooleanLiteralToken) {
                    is FalseLiteralToken -> FalseLiteral
                    is TrueLiteralToken -> TrueLiteral
                }
                ConstAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), parameter), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type))
            }
            else -> ConstAssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), findType(validListOfTokens.type)))
        }
}
