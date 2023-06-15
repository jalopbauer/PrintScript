package lexer.tokenLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import token.DoubleNumberLiteralToken

class DoubleNumberLiteralLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): DoubleNumberLiteralToken? =
        input.string.toDoubleOrNull()?.let { number -> DoubleNumberLiteralToken(number, input.lineNumber, input.position) }
}
