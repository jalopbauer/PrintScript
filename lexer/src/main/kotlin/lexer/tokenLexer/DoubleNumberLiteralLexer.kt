package lexer.tokenLexer

import token.DoubleNumberLiteralToken

class DoubleNumberLiteralLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): DoubleNumberLiteralToken? =
        doesntHavePlusOrMinus(input)?.toDoubleOrNull()?.let { number -> DoubleNumberLiteralToken(number, input.lineNumber, input.position) }

    private fun doesntHavePlusOrMinus(input: TokenLexerInput): String? =
        input.string.takeIf { it.firstOrNull()?.let { c -> (c != '+') && (c != '-') } ?: false }
}
