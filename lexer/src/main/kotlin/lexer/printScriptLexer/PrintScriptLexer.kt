package lexer.printScriptLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import lexer.tokenLexer.DoubleNumberLiteralLexer
import lexer.tokenLexer.FirstVersionReservedKeysLexer
import lexer.tokenLexer.IntNumberLiteralLexer
import lexer.tokenLexer.StringLiteralTokenLexer
import lexer.tokenLexer.VariableTokenLexer
import token.ErrorToken
import token.Token

class FirstVersionPrintScriptLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token =
        ListTokenLexer(
            listOf(
                DoubleNumberLiteralLexer(),
                IntNumberLiteralLexer(),
                FirstVersionReservedKeysLexer(),
                StringLiteralTokenLexer(),
                VariableTokenLexer()
            )
        ).tokenize(input)
}

class SecondVersionPrintScriptLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token =
        ListTokenLexer(
            listOf(
                DoubleNumberLiteralLexer(),
                IntNumberLiteralLexer(),
                FirstVersionReservedKeysLexer(),
                StringLiteralTokenLexer(),
                VariableTokenLexer()
            )
        ).tokenize(input)
}
class ListTokenLexer(private val tokenLexers: List<TokenLexer>) : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token =
        tokenLexers.first { it.tokenize(input) != null }.tokenize(input) ?: ErrorToken(input.lineNumber, input.position)
}
