package parser.astParsers

import ast.AbstractSyntaxTree
import parser.TokenListParser
import token.Token
import validlistoftokens.ValidListOfTokens
import validlistoftokens.ValidListOfTokensBuilder

interface AstTokenListParser<T : ValidListOfTokens, U : AbstractSyntaxTree> : TokenListParser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): U? =
        validListOfTokensBuilder().validate(tokensInCodeBlock)?.let { parserAstBuilder().build(it) }
    fun parserAstBuilder(): AstBuilder<T, U>
    fun validListOfTokensBuilder(): ValidListOfTokensBuilder<T>
}
