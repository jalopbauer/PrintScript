package parser.astParsers.senteceParsers

import ast.SentenceAbstractSyntaxTree
import parser.astParsers.AstBuilder
import parser.astParsers.senteceParsers.println.PrintlnBuilder
import validlistoftokens.PrintlnValidListOfTokens
import validlistoftokens.SentenceValidListOfTokens

class SentenceBuilder : AstBuilder<SentenceValidListOfTokens, SentenceAbstractSyntaxTree> {
    override fun build(validListOfTokens: SentenceValidListOfTokens): SentenceAbstractSyntaxTree =
        when (validListOfTokens) {
            is PrintlnValidListOfTokens -> PrintlnBuilder().build(validListOfTokens)
            else -> TODO()
        }
}
