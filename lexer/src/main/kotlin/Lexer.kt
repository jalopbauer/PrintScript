package lexer

import CodeStructure
import token.Token

interface Lexer<T : CodeStructure> {

    fun buildTokenList(sentence: T): List<Token>
}
