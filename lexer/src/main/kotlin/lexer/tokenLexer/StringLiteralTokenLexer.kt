package lexer.tokenLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import token.StringLiteralToken
import token.Token

class StringLiteralSingleQuoteTokenLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        if (input.string.first() == '\'' && input.string.last() == '\'' && input.string.length >= 2) {
            StringLiteralToken(input.string.substring(1, input.string.length - 1), input.lineNumber, input.position)
        } else {
            null
        }
}

class StringLiteralDoubleQuoteTokenLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        if (input.string.first() == '\"' && input.string.last() == '\"' && input.string.length >= 2) {
            StringLiteralToken(input.string.substring(1, input.string.length - 1), input.lineNumber, input.position)
        } else {
            null
        }
}
