package app.interpreter

import ast.AbstractSyntaxTree
import interpreter.PrintScriptInterpreter
import interpreter.state.PrintScriptInterpreterState
import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.PreviousTokenDefinedLexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserRespose.SendToken
import token.Token

class PrintScriptInterpetI(private val tokenListLexer: NewTokenListLexer) : PrintScriptInterpret {
    constructor(version: String) : this(NewTokenListLexer(FirstVersionPrintScriptLexer()))
    override fun interpret(
        nextChar: Char,
        states: PrintScriptInterpretStates
    ): PrintScriptInterpretStates? {
        return LexerInput(nextChar, states.lexerState)
            .let { input ->
                when (val stateLexerResponse = tokenListLexer.tokenize(input)) {
                    is IntermediateLexerStateResponse -> states.copy(lexerState = stateLexerResponse.intermediateLexerState)
                    is TokenFoundLexerStateResponse -> parseStates(stateLexerResponse.token, states.copy(lexerState = stateLexerResponse.intermediateLexerState))
                }
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
                    is AstFound -> interpretStates(parse.abstractSyntaxTree, states.copy(parserState = parse.parserState))
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
    override fun handleLastState(states: PrintScriptInterpretStates): PrintScriptInterpretStates? =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> parseStates(lexerState.previousToken, states)
            else -> null
        }
}
