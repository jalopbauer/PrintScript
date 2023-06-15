package lexer.tokenLexer

import lexer.Lexer
import token.Token

interface TokenLexer : Lexer<TokenLexerInput, Token?>

data class TokenLexerInput(val string: String, val position: Int, val lineNumber: Int)
