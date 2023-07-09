package parser.astParsers.senteceParsers

import ast.AbstractSyntaxTree
import ast.SentenceAbstractSyntaxTree
import parser.TokenListParser
import parser.astParsers.senteceParsers.assignation.AssignationTokenListParser
import parser.astParsers.senteceParsers.declaration.DeclarationTokenListParser
import parser.astParsers.senteceParsers.declarationAssignation.ConstDeclarationAssignationTokenListParser
import parser.astParsers.senteceParsers.declarationAssignation.DeclarationAssignationTokenListParser
import parser.astParsers.senteceParsers.println.PrintLnTokenListParser
import token.Token

class SentenceParsers : TokenListParser<AbstractSyntaxTree> {

    override fun parse(tokensInCodeBlock: List<Token>): SentenceAbstractSyntaxTree? =
        PrintLnTokenListParser().parse(tokensInCodeBlock)
            ?: DeclarationTokenListParser().parse(tokensInCodeBlock)
            ?: AssignationTokenListParser().parse(tokensInCodeBlock)
            ?: DeclarationAssignationTokenListParser().parse(tokensInCodeBlock)
            ?: ConstDeclarationAssignationTokenListParser().parse(tokensInCodeBlock)
}
