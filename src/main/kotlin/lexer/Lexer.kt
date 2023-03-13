package lexer

import token.Token
import token.TokenIdentifier

interface Lexer<T :LexerInput> {

    fun buildTokenList(lexerInput: T): List<Token>

}

class LineLexer: Lexer<Line> {
    override fun buildTokenList(line: Line): List<Token> {
        return splitLine(line.line)
    }

    // split line en tokens

    private fun splitLine(line: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var line = line
        while (line.isNotEmpty()) {
            val token = identifyToken(line)
            tokens.add(token)
            line = line.removePrefix(token.value)
        }
        return tokens
    }

    // identifica el token usando el identifier
    private fun identifyToken(line: String): Token {
        val tokenIdentifier = TokenIdentifier.values().find { it.identify(line) }
        val value = tokenIdentifier?.regex?.find(line)?.value ?: ""
        return Token(tokenIdentifier!!, value, this)
    }

}
