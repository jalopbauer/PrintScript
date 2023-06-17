package lexer.printScriptLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import lexer.tokenLexer.DoubleNumberLiteralLexer
import lexer.tokenLexer.FirstVersionReservedKeysLexer
import lexer.tokenLexer.IntNumberLiteralLexer
import lexer.tokenLexer.SecondVersionReservedKeysLexer
import lexer.tokenLexer.StringLiteralDoubleQuoteTokenLexer
import lexer.tokenLexer.StringLiteralSingleQuoteTokenLexer
import lexer.tokenLexer.VariableTokenLexer
import token.ErrorToken
import token.Token

class FirstVersionPrintScriptLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token =
        ListTokenLexer(
            listOf(
                FirstVersionReservedKeysLexer(),
                IntNumberLiteralLexer(),
                DoubleNumberLiteralLexer(),
                StringLiteralSingleQuoteTokenLexer(),
                StringLiteralDoubleQuoteTokenLexer(),
                VariableTokenLexer()
            )
        ).tokenize(input)
}

class SecondVersionPrintScriptLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token =
        ListTokenLexer(
            listOf(
                SecondVersionReservedKeysLexer(),
                IntNumberLiteralLexer(),
                DoubleNumberLiteralLexer(),
                StringLiteralSingleQuoteTokenLexer(),
                StringLiteralDoubleQuoteTokenLexer(),
                VariableTokenLexer()
            )
        ).tokenize(input)
}
class ListTokenLexer(private val tokenLexers: List<TokenLexer>) : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token =
        tokenLexers.firstOrNull { it.tokenize(input) != null }?.tokenize(input) ?: ErrorToken(input.lineNumber, input.position)
}
