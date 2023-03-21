import token.Token

sealed interface Ast

class Node(val token: Token, val leftNode: Ast, val rightNode: Ast): Ast

class Leaf(val token: Token): Ast