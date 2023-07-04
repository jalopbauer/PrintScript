package app.interpreter

import ast.AbstractSyntaxTree
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
                        interpretStates(
                            parse.abstractSyntaxTree,
                            states
                                .copy(lexerState = stateLexerResponse.intermediateLexerState)
                                .copy(parserState = addedTokenParserState)
                        )
                    }
                }
            }
        }
    }

    private fun interpretStates(
        abstractSyntaxTree: AbstractSyntaxTree,
        states: PrintScriptInterpretStates
    ): PrintScriptInterpretStates? {
        return when (val interpret = PrintScriptInterpreter().interpret(abstractSyntaxTree, states.printScriptInterpreterState)) {
            is PrintScriptInterpreterState -> states.copy(printScriptInterpreterState = interpret)
            else -> null
        }
    }
}
