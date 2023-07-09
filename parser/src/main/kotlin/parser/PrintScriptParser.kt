package parser

import parser.parserRespose.AstFound
import parser.parserRespose.ParserResponse
import parser.parserRespose.SendToken
import parser.parserState.ParserState
import parser.parserState.RegularParserState

class PrintScriptParser : Parser<ParserResponse, ParserState> {
    override fun parse(tokensInCodeBlock: ParserState): ParserResponse =
        tokensInCodeBlock.tokens()
            .let { tokens ->
                PrintScriptAstParser().parse(tokens)
                    ?.let { ast ->
                        AstFound(RegularParserState(), ast)
                    }
                    ?: SendToken(tokensInCodeBlock)
            }
}
