package lexer

import lexer.lexerState.IntermediateLexerState
import lexer.lexerState.LexerState
import lexer.lexerState.TokenFoundLexerState
import lexer.tokenLexer.VersionPrintScriptLexer

class LexerStateLexer(private val firstVersionPrintScriptLexer: VersionPrintScriptLexer) : Lexer<LexerInput<*>, LexerState> {
    override fun tokenize(input: LexerInput<*>): LexerState =
        input.lexerState.tokenLexerInput(input.nextChar)
            .let { tokenLexerInput ->
                firstVersionPrintScriptLexer.tokenize(tokenLexerInput)
                    .let {
                        input.lexerState.handleNextToken(input.nextChar, it)
                    }
            }
}

class TokenFoundLexerStateLexer(private val firstVersionPrintScriptLexer: VersionPrintScriptLexer) : Lexer<LexerInput<TokenFoundLexerState>, IntermediateLexerState> {
    override fun tokenize(input: LexerInput<TokenFoundLexerState>): IntermediateLexerState =
        input.lexerState.tokenLexerInput(input.nextChar)
            .let { tokenLexerInput ->
                firstVersionPrintScriptLexer.tokenize(tokenLexerInput)
                    .let {
                        input.lexerState.handleNextToken(input.nextChar, it)
                    }
            }
}
