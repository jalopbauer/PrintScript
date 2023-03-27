package lexer

interface LexerInput
class Line(val value: String, val number: Int) : LexerInput

class Block(val lines: List<Line>) : LexerInput
