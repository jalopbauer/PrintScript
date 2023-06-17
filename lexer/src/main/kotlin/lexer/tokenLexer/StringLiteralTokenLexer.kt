package lexer.tokenLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import token.StringLiteralToken
import token.Token

class StringLiteralSingleQuoteTokenLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        if (input.string.first() == '\'' && input.string.last() == '\'') {
            StringLiteralToken(input.string, input.lineNumber, input.position)
        } else {
            null
        }
}

class StringLiteralDoubleQuoteTokenLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        if (input.string.first() == '\"' && input.string.last() == '\"') {
            StringLiteralToken(input.string, input.lineNumber, input.position)
        } else {
            null
        }
}
