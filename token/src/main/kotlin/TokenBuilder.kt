package token

interface TokenBuilder {

    fun build(string: String, position: Int, lineNumber: Int): Token?
}

class ListBuilder(private val tokenBuilders: List<TokenBuilder>) : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token =
        tokenBuilders.first { it.build(string, position, lineNumber) != null }.build(string, position, lineNumber) ?: ErrorToken(lineNumber, position)
}
class PrintScript : TokenBuilder {
    private val tokenIdentifiers = listOf(
        StringEqualsTokenName(),
        SingleQuoteStringLiteral(),
        DoubleQuoteStringLiteral(),
        IntNumberLiteralBuilder(),
        DoubleNumberLiteralBuilder(),
        VariableBuilder()
    )
    override fun build(string: String, position: Int, lineNumber: Int): Token {
        return ListBuilder(tokenIdentifiers).build(string, position, lineNumber)
    }
}

class VariableBuilder : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return if (string.matches(Regex("[a-z][a-zA-Z]+"))) {
            VariableNameToken(string, lineNumber, position)
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
        return stringTokenMap[string]?.invoke(position, lineNumber)
    }

    companion object {
        val stringTokenMap: Map<String, (position: Int, lineNumber: Int) -> Token> = mapOf(
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
    }
}

class StringEqualsTokenNameV1 : TokenBuilder {
    override fun build(string: String, position: Int, lineNumber: Int): Token? {
        return stringTokenMap[string]?.invoke(position, lineNumber)
    }

    companion object {
        val stringTokenMap: Map<String, (position: Int, lineNumber: Int) -> Token> = mapOf(
            "let" to { lineNumber, position -> LetToken(position, lineNumber) },
            ":" to { lineNumber, position -> DeclarationToken(position, lineNumber) },
            "string" to { lineNumber, position -> StringTypeToken(position, lineNumber) },
            "number" to { lineNumber, position -> NumberTypeToken(position, lineNumber) },
            "boolean" to { lineNumber, position -> BooleanTypeToken(position, lineNumber) },
            "false" to { lineNumber, position -> FalseLiteralToken(position, lineNumber) },
            "true" to { lineNumber, position -> TrueLiteralToken(position, lineNumber) },
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
    }
}
class PrintScriptV1 : TokenBuilder {
    private val tokenIdentifiers = listOf(
        StringEqualsTokenNameV1(),
        SingleQuoteStringLiteral(),
        DoubleQuoteStringLiteral(),
        IntNumberLiteralBuilder(),
        DoubleNumberLiteralBuilder(),
        VariableBuilder()
    )
    override fun build(string: String, position: Int, lineNumber: Int): Token {
        return ListBuilder(tokenIdentifiers).build(string, position, lineNumber)
    }
}
