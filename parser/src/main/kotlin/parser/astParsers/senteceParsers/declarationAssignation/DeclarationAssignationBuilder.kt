package parser.astParsers.senteceParsers.declarationAssignation

import ast.AbstractSyntaxTree
import ast.AssignationAst
import ast.BooleanType
import ast.DeclarationAst
import ast.FalseLiteral
import ast.LetAssignationDeclarationAst
import ast.NumberType
import ast.StringType
import ast.TrueLiteral
import ast.VariableNameNode
import parser.astParsers.AstBuilder
import parser.shuntingYard.ShuntingYardImpl
import token.BooleanLiteralToken
import token.FalseLiteralToken
import token.TrueLiteralToken
import validlistoftokens.DeclarationAssignationValidListOfTokens

class DeclarationAssignationBuilder : AstBuilder<DeclarationAssignationValidListOfTokens> {
    override fun build(validListOfTokens: DeclarationAssignationValidListOfTokens): AbstractSyntaxTree =
        when (val type = findType(validListOfTokens.type)) {
            is NumberType -> LetAssignationDeclarationAst(
                AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)),
                DeclarationAst(
                    VariableNameNode(validListOfTokens.variable.value),
                    type
                )
            )
            is StringType -> LetAssignationDeclarationAst(
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
                LetAssignationDeclarationAst(
                    AssignationAst(VariableNameNode(validListOfTokens.variable.value), parameter),
                    DeclarationAst(
                        VariableNameNode(validListOfTokens.variable.value),
                        type
                    )
                )
            }
            else -> LetAssignationDeclarationAst(
                AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)),
                DeclarationAst(
                    VariableNameNode(validListOfTokens.variable.value),
                    findType(validListOfTokens.type)
                )
            )
        }
}
