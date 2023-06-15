package lexer.tokenLexer

import lexer.TokenLexer
import lexer.TokenLexerInput
import token.VariableNameToken

class VariableTokenLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): VariableNameToken? =
        if (input.string.matches(Regex("[a-z][a-zA-Z]+"))) {
            VariableNameToken(input.string, input.lineNumber, input.position)
        } else {
            null
        }
}
