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
import parser.parserState.ParserState
import token.Token

class PrintScriptInterpetI : PrintScriptInterpret {
    override fun interpret(
        nextChar: Char,
        states: PrintScriptInterpretStates
    ): PrintScriptInterpretStates? =
        LexerInput(nextChar, states.lexerState)
            .let { input ->
                when (val stateLexerResponse = NewTokenListLexer().tokenize(input)) {
                    is IntermediateLexerStateResponse -> states.copy(lexerState = stateLexerResponse.intermediateLexerState)
                    is TokenFoundLexerStateResponse -> parseStates(stateLexerResponse.token, states.copy(lexerState = stateLexerResponse.intermediateLexerState))
                }
            }

    private fun parseStates(
        nextToken: Token,
        states: PrintScriptInterpretStates
    ): PrintScriptInterpretStates? =
        states.parserState.addToken(nextToken)
            .let { addedTokenParserState ->
                when (val parse = PrintScriptParser().parse(addedTokenParserState)) {
                    is SendToken -> states.copy(parserState = addedTokenParserState)
                    is AstFound -> interpretStates(parse.abstractSyntaxTree, states.copy(parserState = ParserState(parse.tokens)))
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
