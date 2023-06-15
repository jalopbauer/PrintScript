package lexer

interface Lexer<T, U> {
    fun tokenize(input: T): U
}
