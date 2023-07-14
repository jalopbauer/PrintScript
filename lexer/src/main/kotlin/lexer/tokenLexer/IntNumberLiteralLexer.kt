package lexer.tokenLexer

import token.IntNumberLiteralToken
import token.Token

class IntNumberLiteralLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        doesntHavePlusOrMinus(input)?.toIntOrNull()?.let { number -> IntNumberLiteralToken(number, input.lineNumber, input.position) }

    private fun doesntHavePlusOrMinus(input: TokenLexerInput): String? =
        input.string.takeIf { it.firstOrNull()?.let { c -> (c != '+') && (c != '-') } ?: false }
}
