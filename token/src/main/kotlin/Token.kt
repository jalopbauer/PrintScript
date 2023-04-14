package token
interface Token {
    fun tokenName(): TokenName
    fun lineNumber(): Int
    fun position(): Int
}
data class TokenWithoutValue(val tokenName: TokenName, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return tokenName
    }
    override fun lineNumber(): Int {
        return lineNumber
    }
    override fun position(): Int {
        return position
    }
}
data class StringLiteralToken(val value: String, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.STRING_LITERAL
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class ErrorToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.ERROR
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
sealed interface NumberLiteralToken<T> : Token
data class IntNumberLiteralToken(val value: Int, private val lineNumber: Int, private val position: Int) : NumberLiteralToken<Int> {
    override fun tokenName(): TokenName {
        return TokenName.NUMBER_LITERAL
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class DoubleNumberLiteralToken(val value: Double, private val lineNumber: Int, private val position: Int) : NumberLiteralToken<Double> {
    override fun tokenName(): TokenName {
        return TokenName.NUMBER_LITERAL
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class VariableLiteralToken(val value: String, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.VARIABLE
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class OperatorHighToken(val tokenName: TokenName, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return tokenName
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class OperatorLowToken(val tokenName: TokenName, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return tokenName
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class OpenBracketToken(val tokenName: TokenName, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return tokenName
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class ClosedBracketToken(val tokenName: TokenName, private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return tokenName
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
