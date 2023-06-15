package lexer

import token.PrintScript
import token.Token

class LexerSentence : LexerCodeStructure<Sentence> {

    override fun tokenize(input: Sentence): List<Token> {
        val splitSentence: List<String> = SentenceSplitter().split(input)
        val result = splitSentence.foldIndexed(listOf<Token>()) { position, tokens, item ->
            val possibleToken = PrintScript().build(item, position, input.line)
            tokens + possibleToken
        }
        return result
    }
}
