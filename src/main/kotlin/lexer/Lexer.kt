package lexer

import token.Token

interface Lexer<T :LexerInput> {

    fun buildTokenList(lexerInput: T): List<Token>

}

class LineLexer: Lexer<Line> {
    override fun buildTokenList(line: Line): List<Token> {
        TODO("Poner aca lo de partir un linea")
    }

}
