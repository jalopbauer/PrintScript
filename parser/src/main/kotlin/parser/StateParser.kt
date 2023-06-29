package parser

import ast.AbstractSyntaxTree
import ast.IfStatement
import token.ElseToken
import token.IfToken
import token.Token

fun whichAstAmICreating(nextToken: Token, abstractSyntaxTree: AbstractSyntaxTree?): ParserState? =
    when {
        nextToken is IfToken -> CreatingIfStatement(nextToken)
        nextToken is ElseToken && abstractSyntaxTree is IfStatement -> CreatingElseStatement(nextToken, abstractSyntaxTree)
        nextToken !is IfToken && nextToken !is ElseToken -> Sentence(nextToken)
        else -> null
    }

interface ParserState

data class CreatingIfStatement(val tokens: List<Token>) : ParserState {
    constructor(nextToken: IfToken) : this(listOf(nextToken))
}

data class CreatingElseStatement(val tokens: List<Token>, val ifStatement: IfStatement) : ParserState {
    constructor(nextToken: ElseToken, ifStatement: IfStatement) : this(listOf(nextToken), ifStatement)
}

class Sentence(val tokens: List<Token>) : ParserState {
    constructor(nextToken: Token) : this(listOf(nextToken))
}
