package parser.astParsers.senteceParsers.declaration

import ast.DeclarationAst
import ast.VariableNameNode
import parser.astParsers.AstBuilder
import validlistoftokens.DeclarationValidListOfTokens

class DeclarationBuilder : AstBuilder<DeclarationValidListOfTokens, DeclarationAst> {
    override fun build(validListOfTokens: DeclarationValidListOfTokens): DeclarationAst {
        val type = findType(validListOfTokens.type)
        return DeclarationAst(VariableNameNode(validListOfTokens.variable.value), type)
    }
}
