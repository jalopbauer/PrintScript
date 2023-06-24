package parser

import ast.AbstractSyntaxTree
import parser.astParsers.senteceParsers.SentenceParsers
import token.Token
interface Parser<T> {
    fun parse(tokensInCodeBlock: List<Token>): T?
}
class PrintScriptParser : Parser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        SentenceParsers().parse(tokensInCodeBlock)
}
