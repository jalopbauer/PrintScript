package lexer.previousImplementation

import lexer.Lexer
import token.Token
interface LexerCodeStructure<T : CodeStructure> : Lexer<T, List<Token>>
