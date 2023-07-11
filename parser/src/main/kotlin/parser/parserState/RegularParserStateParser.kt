package parser.parserState

import ast.IfStatement
import parser.Parser
import parser.PrintScriptAstParser
import parser.parserRespose.AstFound
import parser.parserRespose.ParserResponse
import parser.parserRespose.SendToken

class RegularParserStateParser : Parser<ParserResponse, RegularParserState> {
    override fun parse(tokensInCodeBlock: RegularParserState): ParserResponse =
        tokensInCodeBlock.tokens()
            .let { tokens ->
                PrintScriptAstParser().parse(tokens)
                    ?.let { ast ->
                        when (ast) {
                            is IfStatement -> SendToken(IfParserState(ast))
                            else -> AstFound(RegularParserState(), ast)
                        }
                    }
                    ?: SendToken(tokensInCodeBlock)
            }
}
