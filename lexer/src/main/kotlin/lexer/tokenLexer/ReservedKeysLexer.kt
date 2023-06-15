package lexer.tokenLexer

import token.AssignationToken
import token.BooleanTypeToken
import token.ConstToken
import token.DeclarationToken
import token.DivToken
import token.ElseToken
import token.FalseLiteralToken
import token.IfToken
import token.LeftCurlyBracketsToken
import token.LeftParenthesisToken
import token.LetToken
import token.MultToken
import token.NumberTypeToken
import token.PrintlnToken
import token.ReadInputToken
import token.RightCurlyBracketsToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringTypeToken
import token.SubToken
import token.SumToken
import token.Token
import token.TrueLiteralToken

class ReservedKeysLexer(private val stringTokenMap: Map<String, (lineNumber: Int, position: Int) -> Token>) : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        stringTokenMap[input.string]?.invoke(input.position, input.lineNumber)
}

class FirstVersionReservedKeysLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        ReservedKeysLexer(
            mapOf(
                "let" to { lineNumber, position -> LetToken(position, lineNumber) },
                ":" to { lineNumber, position -> DeclarationToken(position, lineNumber) },
                "string" to { lineNumber, position -> StringTypeToken(position, lineNumber) },
                "number" to { lineNumber, position -> NumberTypeToken(position, lineNumber) },
                "=" to { lineNumber, position -> AssignationToken(position, lineNumber) },
                "+" to { lineNumber, position -> SumToken(position, lineNumber) },
                "-" to { lineNumber, position -> SubToken(position, lineNumber) },
                "*" to { lineNumber, position -> MultToken(position, lineNumber) },
                "/" to { lineNumber, position -> DivToken(position, lineNumber) },
                "(" to { lineNumber, position -> LeftParenthesisToken(position, lineNumber) },
                ")" to { lineNumber, position -> RightParenthesisToken(position, lineNumber) },
                ";" to { lineNumber, position -> SemicolonToken(position, lineNumber) },
                "println" to { lineNumber, position -> PrintlnToken(position, lineNumber) }
            )
        ).tokenize(input)
}

class SecondVersionReservedKeysLexer : TokenLexer {
    override fun tokenize(input: TokenLexerInput): Token? =
        ReservedKeysLexer(
            mapOf(
                "let" to { lineNumber, position -> LetToken(position, lineNumber) },
                ":" to { lineNumber, position -> DeclarationToken(position, lineNumber) },
                "string" to { lineNumber, position -> StringTypeToken(position, lineNumber) },
                "number" to { lineNumber, position -> NumberTypeToken(position, lineNumber) },
                "=" to { lineNumber, position -> AssignationToken(position, lineNumber) },
                "+" to { lineNumber, position -> SumToken(position, lineNumber) },
                "-" to { lineNumber, position -> SubToken(position, lineNumber) },
                "*" to { lineNumber, position -> MultToken(position, lineNumber) },
                "/" to { lineNumber, position -> DivToken(position, lineNumber) },
                "(" to { lineNumber, position -> LeftParenthesisToken(position, lineNumber) },
                ")" to { lineNumber, position -> RightParenthesisToken(position, lineNumber) },
                ";" to { lineNumber, position -> SemicolonToken(position, lineNumber) },
                "println" to { lineNumber, position -> PrintlnToken(position, lineNumber) },
//              Second version exclusive
                "const" to { lineNumber, position -> ConstToken(position, lineNumber) },
                "boolean" to { lineNumber, position -> BooleanTypeToken(position, lineNumber) },
                "false" to { lineNumber, position -> FalseLiteralToken(position, lineNumber) },
                "true" to { lineNumber, position -> TrueLiteralToken(position, lineNumber) },
                "readInput" to { lineNumber, position -> ReadInputToken(position, lineNumber) },
                "if" to { lineNumber, position -> IfToken(position, lineNumber) },
                "else" to { lineNumber, position -> ElseToken(position, lineNumber) },
                "{" to { lineNumber, position -> LeftCurlyBracketsToken(position, lineNumber) },
                "}" to { lineNumber, position -> RightCurlyBracketsToken(position, lineNumber) }
            )
        ).tokenize(input)
}
