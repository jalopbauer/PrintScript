package lexer

import token.PrintScript
import token.Token
import kotlin.text.split

class LexerImp : Lexer {
    override fun buildTokenList(sentence: String): List<Token> {
        val splitSentence: List<String> = sentence.split(Regex(" |(?<=[:;()])|(?=[:;()])")).dropLast(1)
        val result = splitSentence.foldIndexed(Pair(0, listOf<Token>())) { position, (line, tokens), item ->
            val possibleToken = PrintScript().build(item, position, line)
            Pair(if (item == ";") line + 1 else line, tokens + possibleToken)
        }
        return result.second
    }
}
