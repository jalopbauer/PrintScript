package parser

import parser.parserRespose.ParserResponse
import parser.parserState.IfParserState
import parser.parserState.IfParserStateParser
import parser.parserState.ParserState
import parser.parserState.RegularParserState
import parser.parserState.RegularParserStateParser

class PrintScriptParser : Parser<ParserResponse, ParserState> {
    override fun parse(tokensInCodeBlock: ParserState): ParserResponse =
        when (tokensInCodeBlock) {
            is IfParserState -> IfParserStateParser().parse(tokensInCodeBlock)
            is RegularParserState -> RegularParserStateParser().parse(tokensInCodeBlock)
        }
}
