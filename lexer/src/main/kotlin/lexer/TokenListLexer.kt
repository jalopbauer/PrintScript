package lexer

import lexer.lexerState.LexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer

class TokenListLexer : Lexer<LexerInput, LexerState> {
    override fun tokenize(input: LexerInput): LexerState =
        input.lexerState.tokenLexerInput(input.nextChar)
            .let { tokenLexerInput ->
                FirstVersionPrintScriptLexer().tokenize(tokenLexerInput)
                    .let {
                        input.lexerState.handleNextToken(input.nextChar, it)
                    }
            }
}
