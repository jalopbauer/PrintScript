package lexer

import token.Token
import token.TokenIdentifier

interface Lexer<T :LexerInput> {

    fun buildTokenList(lexerInput: T): List<Token>

}

// TODO split always in certain token identifiers: ":", "=", ";", " "

class LineLexer(private val tokenIdentifiers: List<TokenIdentifier>, private val splitterTokenIdentifiers: List<TokenIdentifier>): Lexer<Line> {
    override fun buildTokenList(lexerInput: Line): List<Token> {
        val possibleTokens = splitLineIntoTokens(lexerInput.line)
        return possibleTokens.foldIndexed(listOf()) {
                index, tokens, possibleToken -> tokens + Token(identifyToken(possibleToken), possibleToken, token.Line(lexerInput.lineNumber, index))
        }
    }
    private fun identifyToken(possibleToken: String): TokenIdentifier {
        return tokenIdentifiers.find { tokenIdentifier -> tokenIdentifier.identify(possibleToken) }?: TokenIdentifier.UNDEFINED
    }

}
