package lexer

import lexer.Exceptions.IllegalStringException
import token.PrintScript
import token.Token
import kotlin.text.split

class LexerImp : Lexer {
    override fun buildTokenList(sentence: String): List<Token> {
        val splitSentence: List<String> = sentence.split(Regex(" |(?<=[:;()])|(?=[:;()])")).dropLast(1)
        val result = splitSentence.fold(Pair(0, listOf<Token>())) { acc, item ->
            val line = acc.first
            val position = sentence.indexOf(item)
            val possibleToken = PrintScript().build(item, position , line)
            Pair(if (item == ";") line + 1 else line, acc.second + possibleToken)
        }
        return result.second
    }
}
