package lexer

import Sentence
import SentenceSplitter
import token.PrintScript
import token.Token

class LexerSentence : Lexer<Sentence> {

    override fun buildTokenList(sentence: Sentence): List<Token> {
        val splitSentence: List<String> = SentenceSplitter().split(sentence)
        val result = splitSentence.foldIndexed(listOf<Token>()) { position, tokens, item ->
            val possibleToken = PrintScript().build(item, position, sentence.line)
            tokens + possibleToken
        }
        return result
    }
}
