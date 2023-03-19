package lexer

import token.Token
import token.TokenIdentifier

interface Lexer {

    fun buildTokenList(sentence: String): List<Token>

}
