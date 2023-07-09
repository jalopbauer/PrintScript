package parser.parserRespose

import parser.parserState.ParserState

data class RegularSendToken(val parserState: ParserState) : SendToken
