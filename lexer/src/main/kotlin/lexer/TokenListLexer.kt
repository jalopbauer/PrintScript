package lexer

import lexer.lexerState.LexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer

class TokenListLexer(private val firstVersionPrintScriptLexer: FirstVersionPrintScriptLexer) : Lexer<LexerInput, LexerState> {
    override fun tokenize(input: LexerInput): LexerState =
        input.lexerState.tokenLexerInput(input.nextChar)
            .let { tokenLexerInput ->
                firstVersionPrintScriptLexer.tokenize(tokenLexerInput)
                    .let {
                        input.lexerState.handleNextToken(input.nextChar, it)
                    }
            }
}
