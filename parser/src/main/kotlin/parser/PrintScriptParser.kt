package parser

import parser.parserRespose.ParserResponse
import parser.parserRespose.SendToken
import parser.parserState.IfParserState
import parser.parserState.ParserState
import parser.parserState.RegularParserState

class PrintScriptParser : Parser<ParserResponse, ParserState> {
    override fun parse(tokensInCodeBlock: ParserState): ParserResponse =
        when (tokensInCodeBlock) {
            is IfParserState ->
                tokensInCodeBlock.lastAddedToken()
                    ?.let { TODO() }
                    ?: SendToken(tokensInCodeBlock)
            is RegularParserState -> PrintScriptIfParser().parse(tokensInCodeBlock)
        }
}
