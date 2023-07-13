package parser.parserRespose

import parser.parserState.RegularParserState

data class SentenceInvalid(val tokens: String, val parserState: RegularParserState) : ParserResponse
