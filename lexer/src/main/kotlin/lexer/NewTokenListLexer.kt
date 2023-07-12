package lexer

import lexer.lexerState.IntermediateLexerState
import lexer.lexerState.TokenFoundLexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer
import token.Token

data class NewTokenListLexer(val firstVersionPrintScriptLexer: FirstVersionPrintScriptLexer = FirstVersionPrintScriptLexer()) : Lexer<LexerInput<*>, LexerStateLexerResponse> {
    override fun tokenize(input: LexerInput<*>): LexerStateLexerResponse =
        LexerStateLexer(firstVersionPrintScriptLexer).tokenize(input).let {
            when (it) {
                is IntermediateLexerState -> IntermediateLexerStateResponse(it)
                is TokenFoundLexerState -> {
                    val tokenize = TokenFoundLexerStateLexer(firstVersionPrintScriptLexer).tokenize(LexerInput(input.nextChar, it))
                    TokenFoundLexerStateResponse(it.token, tokenize)
                }
            }
        }
}

sealed interface LexerStateLexerResponse

data class TokenFoundLexerStateResponse(val token: Token, val intermediateLexerState: IntermediateLexerState) : LexerStateLexerResponse

data class IntermediateLexerStateResponse(val intermediateLexerState: IntermediateLexerState) :
    LexerStateLexerResponse
