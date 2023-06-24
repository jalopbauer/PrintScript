package parser

import ast.AbstractSyntaxTree
import parser.astParsers.senteceParsers.SentenceParsers
import token.Token

class PrintScriptAstParser : TokenListParser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        SentenceParsers().parse(tokensInCodeBlock)
}
