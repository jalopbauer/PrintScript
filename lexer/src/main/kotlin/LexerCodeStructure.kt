import token.Token

interface Lexer<T> {

    fun buildTokenList(sentence: T): List<Token>
}
interface LexerCodeStructure<T : CodeStructure> : Lexer<T>
