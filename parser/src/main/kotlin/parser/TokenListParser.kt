package parser

import ast.AbstractSyntaxTree
import parser.astParsers.senteceParsers.SentenceParsers
import token.Token

interface Parser<T, V> {
    fun parse(tokensInCodeBlock: V): T?
}
interface TokenListParser<T> : Parser<T, List<Token>>
class PrintScriptTokenListParser : TokenListParser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        SentenceParsers().parse(tokensInCodeBlock)
}
