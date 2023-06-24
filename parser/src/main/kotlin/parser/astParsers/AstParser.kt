package parser.astParsers

import ast.AbstractSyntaxTree
import parser.Parser
import token.Token
import validlistoftokens.ValidListOfTokens
import validlistoftokens.ValidListOfTokensBuilder

interface AstParser<T : ValidListOfTokens> : Parser<AbstractSyntaxTree> {
    override fun parse(tokensInCodeBlock: List<Token>): AbstractSyntaxTree? =
        validListOfTokensBuilder().validate(tokensInCodeBlock)?.let { parserAstBuilder().build(it) }
    fun parserAstBuilder(): AstBuilder<T>
    fun validListOfTokensBuilder(): ValidListOfTokensBuilder<T>
}
