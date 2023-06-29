package lexer.tokenLexer

import lexer.Lexer
import token.Token

interface TokenLexer : Lexer<TokenLexerInput, Token?>
