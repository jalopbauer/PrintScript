package lexer.tokenLexer

import token.ErrorToken
import token.Token

interface VersionPrintScriptLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token
}
class FirstVersionPrintScriptLexer : VersionPrintScriptLexer {
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

class SecondVersionPrintScriptLexer : VersionPrintScriptLexer {
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
