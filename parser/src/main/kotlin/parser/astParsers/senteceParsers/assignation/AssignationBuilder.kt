package parser.astParsers.senteceParsers.assignation

import ast.AssignationAst
import ast.FalseLiteral
import ast.ReadInputAst
import ast.StringLiteral
import ast.TrueLiteral
import ast.VariableNameNode
import parser.astParsers.AstBuilder
import parser.shuntingYard.ShuntingYardImpl
import token.BooleanLiteralToken
import token.FalseLiteralToken
import token.TrueLiteralToken
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.OperationValidListOfTokensBuilder
import validlistoftokens.ReadInputValidListOfTokensBuilder

class AssignationBuilder : AstBuilder<AssignationValidListOfTokens, AssignationAst> {
    override fun build(validListOfTokens: AssignationValidListOfTokens): AssignationAst {
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
            ReadInputValidListOfTokensBuilder().validate(content)
                ?.let {
                    AssignationAst(
                        VariableNameNode(validListOfTokens.variable.value),
                        ReadInputAst(StringLiteral(it.stringLiteralToken.value))
                    )
                }
                ?: AssignationAst(
                    VariableNameNode(validListOfTokens.variable.value),
                    ShuntingYardImpl().orderString(
                        content
                    )
                )
        }
    }
}
