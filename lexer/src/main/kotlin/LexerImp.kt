package lexer

import lexer.Exceptions.IllegalStringException
import token.PrintScript
import token.Token
import token.TokenName
import kotlin.text.*

class LexerImp: Lexer  {
    override fun buildTokenList(sentence: String): List<Token> {
        val splitSentence: List<String> = sentence.split(Regex(" |(?<=[:;()])|(?=[:;()])")).dropLast(1)
        val result = splitSentence.fold(Pair(0, listOf<Token>()) ) { acc, item ->
            val tokenName: TokenName = findIdentifier(item)
            val position: Int = sentence.indexOf(item)
            val line = acc.first
            Pair(if (item == ";") line + 1 else line, acc.second + Token(tokenName, item, line, position))
        }
        return result.second
    }

    private fun findIdentifier(item: String): TokenName {
        val possibleToken = PrintScript().identify(item)
        return possibleToken ?: throw IllegalStringException("The string $item doesn't match any Token")
    }

}