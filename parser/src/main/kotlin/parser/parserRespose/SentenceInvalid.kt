package parser.parserRespose

import parser.parserState.RegularParserState
import token.Token

data class SentenceInvalid(val tokens: List<Token>, val parserState: RegularParserState) : ParserResponse
