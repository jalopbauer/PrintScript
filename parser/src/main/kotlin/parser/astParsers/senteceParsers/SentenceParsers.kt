package parser.astParsers.senteceParsers

import ast.AbstractSyntaxTree
import parser.ListParser
import parser.Parser
import parser.astParsers.AstParser
import parser.astParsers.senteceParsers.assignation.AssignationParser
import parser.astParsers.senteceParsers.declaration.DeclarationParser
import parser.astParsers.senteceParsers.declarationAssignation.ConstDeclarationAssignationParser
import parser.astParsers.senteceParsers.declarationAssignation.DeclarationAssignationParser
import parser.astParsers.senteceParsers.println.PrintlnParser
import token.Token
import validlistoftokens.ValidListOfTokens

class SentenceParsers : Parser<AbstractSyntaxTree> {

    private val parsers: List<AstParser<out ValidListOfTokens>> = listOf(
        PrintlnParser(),
        DeclarationParser(),
        AssignationParser(),
        DeclarationAssignationParser(),
        ConstDeclarationAssignationParser()
    )
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        ListParser(parsers).parse(tokensInCodeBlock)
}
