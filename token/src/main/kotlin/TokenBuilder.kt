package token

interface TokenBuilder {

    fun build(string: String, position: Int, lineNumber: Int): Token?
}

class ListBuilder(private val tokenBuilders: List<TokenBuilder>) : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return try {
            tokenBuilders.first { tokenIdentifier -> tokenIdentifier.build(string, position, lineNumber) != null }.build(string, position, lineNumber)
        } catch (_: Exception) {
            null
        }
    }
}
class PrintScript : TokenBuilder {
    private val tokenIdentifiers = listOf(
        StringEqualsTokenName(),
        SingleQuoteStringLiteral(),
        DoubleQuoteStringLiteral(),
        IntNumberLiteralBuilder(),
        VariableBuilder()
    )
    override fun build(string: String, position: Int, lineNumber: Int): Token {
        return ListBuilder(tokenIdentifiers).build(string, position, lineNumber) ?: ErrorToken(lineNumber, position)
    }
}

class VariableBuilder : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return if (string.matches(Regex("[a-z][a-zA-Z]+"))) {
            VariableLiteralToken(string, lineNumber, position)
        } else {
            null
        }
    }
}

class SingleQuoteStringLiteral : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return if (string.first() == '\'' && string.last() == '\'') {
            StringLiteralToken(string, lineNumber, position)
        } else {
            null
        }
    }
}

class DoubleQuoteStringLiteral : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return if (string.first() == '\"' && string.last() == '\"') {
            StringLiteralToken(string, lineNumber, position)
        } else {
            null
        }
    }
}

class IntNumberLiteralBuilder : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): IntNumberLiteralToken? =
        string.toIntOrNull()?.let { number -> IntNumberLiteralToken(number, lineNumber, position) }
}
class DoubleNumberLiteralBuilder : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): DoubleNumberLiteralToken? {
        return string.toDoubleOrNull()?.let { number -> DoubleNumberLiteralToken(number, lineNumber, position) }
    }
}
class StringEqualsTokenName : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return stringTokenNameMap[string]?.let { tokenName -> TokenWithoutValue(tokenName, lineNumber, position) }
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
