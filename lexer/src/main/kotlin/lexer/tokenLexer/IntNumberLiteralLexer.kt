package lexer.tokenLexer

import token.IntNumberLiteralToken

class IntNumberLiteralLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): IntNumberLiteralToken? =
        doesntHavePlusOrMinus(input)?.toIntOrNull()?.let { number -> IntNumberLiteralToken(number, input.lineNumber, input.position) }

    private fun doesntHavePlusOrMinus(input: TokenLexerInput): String? =
        input.string.takeIf { it.firstOrNull()?.let { c -> (c != '+') && (c != '-') } ?: false }
}
