package app.interpreter

import app.errorHandler.ErrorHandler
import ast.AbstractSyntaxTree
import interpreter.InterpreterError
import interpreter.PrintScriptInterpreter
import interpreter.SendLiteral
import interpreter.state.PrintScriptInterpreterState
import lexer.IntermediateLexerStateResponse
import lexer.LexerInput
import lexer.NewTokenListLexer
import lexer.TokenFoundLexerStateResponse
import lexer.lexerState.IntermediateLexerState
import lexer.lexerState.PreviousTokenDefinedLexerState
import lexer.lexerState.TokenFoundLexerState
import lexer.tokenLexer.FirstVersionPrintScriptLexer
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserRespose.SendToken
import parser.parserRespose.SentenceInvalid
import token.Token

class PrintScriptInterpetI(private val tokenListLexer: NewTokenListLexer, private val errorHandler: ErrorHandler<PrintScriptInterpretStates>) : PrintScriptInterpret {
    constructor(version: String, errorHandler: ErrorHandler<PrintScriptInterpretStates>) : this(NewTokenListLexer(FirstVersionPrintScriptLexer()), errorHandler)
    override fun interpret(
        nextChar: Char,
        states: PrintScriptInterpretStates
    ): PrintScriptInterpretStates {
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
    ): PrintScriptInterpretStates =
        states.parserState.addToken(nextToken)
            .let { addedTokenParserState ->
                when (val parse = PrintScriptParser().parse(addedTokenParserState)) {
                    is SendToken -> states.copy(parserState = addedTokenParserState)
                    is AstFound -> interpretStates(parse.abstractSyntaxTree, states.copy(parserState = parse.parserState))
                    is SentenceInvalid -> {
                        val state = states.copy(parserState = parse.parserState)
                        errorHandler.handle(parse.tokens.toString(), state)
                        state
                    }
                }
            }

    private fun interpretStates(
        abstractSyntaxTree: AbstractSyntaxTree,
        states: PrintScriptInterpretStates
    ): PrintScriptInterpretStates {
        return when (val interpret = PrintScriptInterpreter().interpret(abstractSyntaxTree, states.printScriptInterpreterState)) {
            is PrintScriptInterpreterState -> states.copy(printScriptInterpreterState = interpret)
            is InterpreterError -> errorHandler.handle(interpret.message, states)
            is SendLiteral -> states.copy(printScriptInterpreterState = interpret.state)
            else -> TODO()
        }
    }
    override fun handleLastState(states: PrintScriptInterpretStates): PrintScriptInterpretStates? =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> parseStates(lexerState.previousToken, states)
            is IntermediateLexerState -> errorHandler.handle("interpret.message", states)
            is TokenFoundLexerState -> null
        }
}
