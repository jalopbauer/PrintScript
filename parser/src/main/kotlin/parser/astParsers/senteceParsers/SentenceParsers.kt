package parser.astParsers.senteceParsers

import ast.AbstractSyntaxTree
import parser.ListTokenListParser
import parser.TokenListParser
import parser.astParsers.AstTokenListParser
import parser.astParsers.senteceParsers.assignation.AssignationTokenListParser
import parser.astParsers.senteceParsers.declaration.DeclarationTokenListParser
import parser.astParsers.senteceParsers.declarationAssignation.ConstDeclarationAssignationTokenListParser
import parser.astParsers.senteceParsers.declarationAssignation.DeclarationAssignationTokenListParser
import parser.astParsers.senteceParsers.println.PrintLnTokenListParser
import token.Token
import validlistoftokens.ValidListOfTokens

class SentenceParsers : TokenListParser<AbstractSyntaxTree> {

    private val parsers: List<AstTokenListParser<out ValidListOfTokens>> = listOf(
        PrintLnTokenListParser(),
        DeclarationTokenListParser(),
        AssignationTokenListParser(),
        DeclarationAssignationTokenListParser(),
        ConstDeclarationAssignationTokenListParser()
    )
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        ListTokenListParser(parsers).parse(tokensInCodeBlock)
}
