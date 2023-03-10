package lexer

import token.Token

interface Lexer<T :LexerInput> {

    fun buildTokenList(lexerInput: LexerInput): List<Token>

}