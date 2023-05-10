package token

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
sealed interface NumberLiteralToken : Token
data class IntNumberLiteralToken(val value: Int, private val lineNumber: Int, private val position: Int) : NumberLiteralToken {
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
data class DoubleNumberLiteralToken(val value: Double, private val lineNumber: Int, private val position: Int) : NumberLiteralToken {
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
sealed interface BooleanLiteralToken : Token
data class FalseLiteralToken(private val lineNumber: Int, private val position: Int) : BooleanLiteralToken {
    override fun tokenName(): TokenName {
        return TokenName.BOOLEAN_LITERAL
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class TrueLiteralToken(private val lineNumber: Int, private val position: Int) : BooleanLiteralToken {
    override fun tokenName(): TokenName {
        return TokenName.BOOLEAN_LITERAL
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
