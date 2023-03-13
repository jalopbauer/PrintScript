package lexer

interface LexerInput
class Line(val line: String, val lineNumber: Int): LexerInput

class Block(val lines: List<Line>): LexerInput