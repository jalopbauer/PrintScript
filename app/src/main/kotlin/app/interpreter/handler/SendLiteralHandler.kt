package app.interpreter.handler

import app.interpreter.PrintScriptInterpretStates
import app.literalInputter.LiteralInputter
import ast.DoubleNumberLiteral
import ast.IntNumberLiteral
import ast.Literal
import ast.StringLiteral
import lexer.tokenLexer.DoubleNumberLiteralLexer
import lexer.tokenLexer.IntNumberLiteralLexer
import lexer.tokenLexer.StringLiteralDoubleQuoteTokenLexer
import lexer.tokenLexer.TokenLexerInput

class SendLiteralHandler(private val literalInputter: LiteralInputter) {
    fun handle(printScriptInterpreterStates: PrintScriptInterpretStates): PrintScriptInterpretStates =
        literalInputter.input()
            ?.let {
                literal(it)
                    ?.let { literal ->
                        val readInput = printScriptInterpreterStates.printScriptInterpreterState.setReadInput(literal)
                        printScriptInterpreterStates.copy(printScriptInterpreterState = readInput)
                    }
            } ?: printScriptInterpreterStates

    private fun literal(it: String): Literal? =
        IntNumberLiteralLexer().tokenize(TokenLexerInput(it, 0, 0))
            ?.let { number -> IntNumberLiteral(number.value) }
            ?: DoubleNumberLiteralLexer().tokenize(TokenLexerInput(it, 0, 0))
                ?.let { number -> DoubleNumberLiteral(number.value) }
            ?: StringLiteralDoubleQuoteTokenLexer().tokenize(TokenLexerInput("\"$it\"", 0, 0))
                ?.let { string -> StringLiteral(string.value) }
}
