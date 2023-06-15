package lexer.tokenLexer

import token.IntNumberLiteralToken
import token.Token

class IntNumberLiteralLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        input.string.toIntOrNull()?.let { number -> IntNumberLiteralToken(number, input.lineNumber, input.position) }
}
