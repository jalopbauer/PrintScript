package parser.astParsers

import ast.AbstractSyntaxTree
import parser.TokenListParser
import token.Token
import validlistoftokens.ValidListOfTokens
import validlistoftokens.ValidListOfTokensBuilder

interface AstTokenListParser<T : ValidListOfTokens> : TokenListParser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        validListOfTokensBuilder().validate(tokensInCodeBlock)?.let { parserAstBuilder().build(it) }
    fun parserAstBuilder(): AstBuilder<T>
    fun validListOfTokensBuilder(): ValidListOfTokensBuilder<T>
}
