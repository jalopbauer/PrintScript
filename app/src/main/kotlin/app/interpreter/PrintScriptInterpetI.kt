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
import lexer.tokenLexer.SecondVersionPrintScriptLexer
import parser.PrintScriptParser
import parser.parserRespose.AstFound
import parser.parserRespose.SendToken
import parser.parserRespose.SentenceInvalid
import parser.parserState.IfParserState
import token.Token

class PrintScriptInterpetI(private val tokenListLexer: NewTokenListLexer, private val errorHandler: ErrorHandler<PrintScriptInterpretStates>) : PrintScriptInterpret {
    constructor(version: String, errorHandler: ErrorHandler<PrintScriptInterpretStates>) :
        this(
            if (version == "1.1") {
                NewTokenListLexer(SecondVersionPrintScriptLexer())
            } else {
                NewTokenListLexer(FirstVersionPrintScriptLexer())
            },
            errorHandler
        )
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
                    is SendToken -> states.copy(parserState = parse.parserState)
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
    override fun handleLastState(states: PrintScriptInterpretStates): PrintScriptInterpretStates =
        when (val lexerState = states.lexerState) {
            is PreviousTokenDefinedLexerState -> parseStates(lexerState.previousToken, states)
            is IntermediateLexerState ->
                if (!lexerState.isEmpty()) {
                    errorHandler.handle("interpret.message", states)
                } else {
                    states
                }
            is TokenFoundLexerState -> parseStates(lexerState.token, states)
        }.let {
            if (it.parserState is IfParserState) {
                interpretStates(it.parserState.ifStatement, states)
            } else {
                it
            }
        }.let {
            if (!it.parserState.hasEndedProperly() && it.parserState.tokens().isNotEmpty()) {
                errorHandler.handle("parser error", it)
            } else {
                it
            }
        }.let {
            println(it.parserState)
            it
        }
}
