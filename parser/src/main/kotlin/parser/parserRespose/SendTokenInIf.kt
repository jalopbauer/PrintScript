package parser.parserRespose

import parser.parserState.IfParserState

data class SendTokenInIf(val parserState: IfParserState) : SendToken
