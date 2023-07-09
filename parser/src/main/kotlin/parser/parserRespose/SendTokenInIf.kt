package parser.parserRespose

import parser.parserState.ParserState

data class SendTokenInIf(val parserState: ParserState) : SendToken
