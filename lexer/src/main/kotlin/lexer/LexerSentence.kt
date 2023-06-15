package lexer

import lexer.printScriptLexer.FirstVersionPrintScriptLexer
import token.Token

class LexerSentence : LexerCodeStructure<Sentence> {

    override fun tokenize(input: Sentence): List<Token> {
        val splitSentence: List<String> = SentenceSplitter().split(input)
        val result = splitSentence.foldIndexed(listOf<Token>()) { position, tokens, item ->
            tokens + FirstVersionPrintScriptLexer().tokenize(TokenLexerInput(item, position, input.line))
        }
        return result
    }
}
