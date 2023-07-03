package app.interpreter

import interpreter.PrintScriptInterpreter
import interpreter.state.PrintScriptInterpreterState
import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserRespose.SendToken

class PrintScriptInterpetI : PrintScriptInterpret {
    override fun interpret(nextChar: Char, states: PrintScriptInterpretStates): PrintScriptInterpretStates? {
        val (
            lexerState,
            parserState,
            printScriptInterpreterState
        ) = states
        val input = LexerInput(nextChar, lexerState)
        return when (val stateLexerResponse = NewTokenListLexer().tokenize(input)) {
            is IntermediateLexerStateResponse -> states.copy(lexerState = stateLexerResponse.intermediateLexerState)
            is TokenFoundLexerStateResponse -> {
                val addedTokenParserState = parserState.addToken(stateLexerResponse.token)
                when (val parse = PrintScriptParser().parse(addedTokenParserState)) {
                    is SendToken -> states.copy(lexerState = stateLexerResponse.intermediateLexerState, parserState = addedTokenParserState)
                    is AstFound -> {
                        val interpret =
                            PrintScriptInterpreter().interpret(parse.abstractSyntaxTree, printScriptInterpreterState)
                        when (interpret) {
                            is PrintScriptInterpreterState -> states.copy(lexerState = stateLexerResponse.intermediateLexerState, parserState = addedTokenParserState, printScriptInterpreterState = interpret)
                            else -> null
                        }
                    }
                }
            }
        }
    }
}
