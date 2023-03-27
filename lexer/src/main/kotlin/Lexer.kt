package lexer

import token.Token

interface Lexer {

    fun buildTokenList(sentence: String): List<Token>
}
