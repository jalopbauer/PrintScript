package lexer.tokenLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import token.StringLiteralToken
import token.Token

class StringLiteralTokenLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        if (isStringLiteralCharacter(input.string.first()) && isStringLiteralCharacter(input.string.last())) {
            StringLiteralToken(input.string, input.lineNumber, input.position)
        } else {
            null
        }

    private fun isStringLiteralCharacter(char: Char): Boolean =
        char == '\'' || char == '\"'
}
