package parser.parserState

import ast.IfElseStatement
import parser.Parser
import parser.astParsers.ifStatement.ElseTokenListParser
import parser.parserRespose.AstFound
import parser.parserRespose.ParserResponse
import parser.parserRespose.SendToken
import token.ElseToken

class IfParserStateParser : Parser<ParserResponse, IfParserState> {
    override fun parse(tokensInCodeBlock: IfParserState): ParserResponse =
        tokensInCodeBlock.firstToken()
            ?.let { firstToken ->
                when (firstToken) {
                    is ElseToken -> {
                        val parse = ElseTokenListParser().parse(tokensInCodeBlock.tokens())
                        parse
                            ?.let { elseStatement -> AstFound(RegularParserState(), IfElseStatement(tokensInCodeBlock.ifStatement, elseStatement)) }
                            ?: SendToken(tokensInCodeBlock)
                    }
                    else -> AstFound(tokensInCodeBlock.regularParserState, tokensInCodeBlock.ifStatement)
                }
            }
            ?: SendToken(tokensInCodeBlock)
}
