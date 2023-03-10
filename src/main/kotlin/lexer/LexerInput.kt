package lexer

interface LexerInput
class Line(val line: String): LexerInput

class Block(val lines: List<Line>): LexerInput