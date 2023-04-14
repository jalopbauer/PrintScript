package lexer

import Sentence
import token.PrintScript
import token.Token
import kotlin.text.split

class LexerSentence : Lexer<Sentence> {
    
    override fun buildTokenList(sentence: Sentence): List<Token> {
        val splitSentence: List<String> = sentence.getString().split(Regex(" |(?<=[:()])|(?=[:()])")).dropLast(1)
        val result = splitSentence.foldIndexed(listOf<Token>()) { position, tokens, item ->
            val possibleToken = PrintScript().build(item, position, sentence.line)
             tokens + possibleToken
        }
        return result
    }
}
