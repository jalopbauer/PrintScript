package lexer

import token.Token

interface Lexer<T, U> {

    fun tokenize(input: T): U
}
interface LexerCodeStructure<T : CodeStructure> : Lexer<T, List<Token>>
