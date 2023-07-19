package parser.astParsers.senteceParsers

import ast.SentenceAbstractSyntaxTree
import parser.astParsers.AstBuilder
import parser.astParsers.senteceParsers.assignation.AssignationBuilder
import parser.astParsers.senteceParsers.declaration.DeclarationBuilder
import parser.astParsers.senteceParsers.declarationAssignation.ConstDeclarationAssignationBuilder
import parser.astParsers.senteceParsers.declarationAssignation.DeclarationAssignationBuilder
import parser.astParsers.senteceParsers.println.PrintlnBuilder
import validlistoftokens.AssignationValidListOfTokens
import validlistoftokens.ConstDeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationAssignationValidListOfTokens
import validlistoftokens.DeclarationValidListOfTokens
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.SentenceValidListOfTokens

class SentenceBuilder : AstBuilder<SentenceValidListOfTokens, SentenceAbstractSyntaxTree> {
    override fun build(validListOfTokens: SentenceValidListOfTokens): SentenceAbstractSyntaxTree =
        when (validListOfTokens) {
            is PrintlnValidListOfTokens -> PrintlnBuilder().build(validListOfTokens)
            is AssignationValidListOfTokens -> AssignationBuilder().build(validListOfTokens)
            is ConstDeclarationAssignationValidListOfTokens -> ConstDeclarationAssignationBuilder().build(validListOfTokens)
            is DeclarationAssignationValidListOfTokens -> DeclarationAssignationBuilder().build(validListOfTokens)
            is DeclarationValidListOfTokens -> DeclarationBuilder().build(validListOfTokens)
        }
}
