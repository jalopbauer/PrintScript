package parser

import parser.parserRespose.AstFound
import parser.parserRespose.ParserResponse
import parser.parserRespose.SendToken
import parser.parserState.ParserState

class PrintScriptParser : Parser<ParserResponse, ParserState> {
    override fun parse(tokensInCodeBlock: ParserState): ParserResponse =
        tokensInCodeBlock.tokens()
            .let { tokens ->
                PrintScriptAstParser().parse(tokens)
                    ?.let { ast ->
                        AstFound(listOf(), ast)
                    }
                    ?: SendToken(tokensInCodeBlock)
            }
}
