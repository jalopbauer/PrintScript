import token.Token

sealed interface Sentence
data class Declaration(val variableName: Token, val variableType: Token): Sentence
data class Println(val tokensInsideParenthesis: List<Token>): Sentence
data class Assignation(val tokensInsideParenthesis: List<Token>): Sentence
data class DeclarationAssignation(val declaration: Declaration, val assignation: Assignation): Sentence

