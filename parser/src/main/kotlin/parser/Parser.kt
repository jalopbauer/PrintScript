package parser

interface Parser<T, V> {
    fun parse(tokensInCodeBlock: V): T?
}
