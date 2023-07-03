package parser.parserRespose

import parser.parserState.ParserState

data class SendToken(val parserState: ParserState) : ParserResponse
