package token

interface TokenIdentifier {

    fun identify(string: String): TokenName?

}

class ListIdentifier(private val tokenIdentifiers: List<TokenIdentifier>): TokenIdentifier {
    override fun identify(string: String): TokenName? {
        return try {
            tokenIdentifiers.first { tokenIdentifier -> tokenIdentifier.identify(string) != null }.identify(string)
        } catch (_: Exception) {
            null
        }
    }
}

class BetweenValueIdentifier(private val borderValue: Char, private val tokenName: TokenName): TokenIdentifier {
    override fun identify(string: String): TokenName? {
        return if (string.first() == borderValue && string.last() == borderValue) {
            tokenName
        } else {
            null

        }
    }

}

class PrintScript: TokenIdentifier {
    private val tokenIdentifiers = listOf(
            StringEqualsTokenName()
        ,   SingleQouteStringLiteral()
    )
    override fun identify(string: String): TokenName? {
        return ListIdentifier(tokenIdentifiers).identify(string)
    }
}

class SingleQouteStringLiteral: TokenIdentifier {
    override fun identify(string: String): TokenName? {
        return BetweenValueIdentifier('\'', TokenName.STRING_LITERAL).identify(string)
    }
}
class StringEqualsTokenName: TokenIdentifier {
    override fun identify(string: String): TokenName? {
        return stringTokenNameMap[string]
    }

    companion object {
        val stringTokenNameMap: Map<String, TokenName> = mapOf(
            "let" to TokenName.LET,
            ":" to TokenName.DECLARATION,
            "string" to TokenName.STRING_TYPE,
            "number" to TokenName.NUMBER_TYPE,
            "=" to TokenName.ASSIGNATION,
            "+" to TokenName.SUM,
            "-" to TokenName.SUB,
            "*" to TokenName.MULT,
            "/" to TokenName.DIV,
            "(" to TokenName.LEFT_PARENTHESIS,
            ")" to TokenName.RIGHT_PARENTHESIS,
            ";" to TokenName.SEMICOLON,
            "println" to TokenName.PRINT
        )
    }
}