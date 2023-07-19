package parser.astParsers.senteceParsers.declarationAssignation

import ast.AssignationAst
import ast.AssignationDeclarationAst
import ast.BooleanType
import ast.ConstAssignationDeclarationAst
import ast.DeclarationAst
import ast.FalseLiteral
import ast.NumberType
import ast.ReadInputAst
import ast.StringLiteral
import ast.StringType
import ast.TrueLiteral
import ast.VariableNameNode
import parser.astParsers.AstBuilder
import parser.shuntingYard.ShuntingYardImpl
import token.BooleanLiteralToken
import token.FalseLiteralToken
import token.TrueLiteralToken
import validlistoftokens.ConstDeclarationAssignationListValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationParameterValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens

class ConstDeclarationAssignationBuilder : AstBuilder<ConstDeclarationAssignationValidListOfTokens, AssignationDeclarationAst> {
    override fun build(validListOfTokens: ConstDeclarationAssignationValidListOfTokens): AssignationDeclarationAst =
        when (validListOfTokens) {
            is ConstDeclarationAssignationListValidListOfTokens ->
                when (val type = findType(validListOfTokens.type)) {
                    is NumberType -> ConstAssignationDeclarationAst(
                        AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)),
                        DeclarationAst(
                            VariableNameNode(validListOfTokens.variable.value),
                            type
                        )
                    )
                    is StringType -> ConstAssignationDeclarationAst(
                        AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderString(validListOfTokens.content)),
                        DeclarationAst(
                            VariableNameNode(validListOfTokens.variable.value),
                            type
                        )
                    )
                    is BooleanType -> {
                        val parameter = when (validListOfTokens.content.component1() as BooleanLiteralToken) {
                            is FalseLiteralToken -> FalseLiteral
                            is TrueLiteralToken -> TrueLiteral
                        }
                        ConstAssignationDeclarationAst(
                            AssignationAst(VariableNameNode(validListOfTokens.variable.value), parameter),
                            DeclarationAst(
                                VariableNameNode(validListOfTokens.variable.value),
                                type
                            )
                        )
                    }
                }

            is ConstDeclarationAssignationParameterValidListOfTokens ->
                ConstAssignationDeclarationAst(
                    AssignationAst(
                        VariableNameNode(validListOfTokens.variable.value),
                        ReadInputAst(StringLiteral(validListOfTokens.content.stringLiteralToken.value))
                    ),
                    DeclarationAst(
                        VariableNameNode(validListOfTokens.variable.value),
                        findType(validListOfTokens.type)
                    )
                )
        }
}
