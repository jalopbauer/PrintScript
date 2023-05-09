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

interface OperatorHighToken : Token

data class SumToken(private val lineNumber: Int, private val position: Int) : OperatorHighToken {
    override fun tokenName(): TokenName {
        return TokenName.SUM
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class SubToken(private val lineNumber: Int, private val position: Int) : OperatorHighToken {
    override fun tokenName(): TokenName {
        return TokenName.SUB
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
interface OperatorLowToken : Token
data class MultToken(private val lineNumber: Int, private val position: Int) : OperatorLowToken {
    override fun tokenName(): TokenName {
        return TokenName.MULT
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class DivToken(private val lineNumber: Int, private val position: Int) : OperatorLowToken {
    override fun tokenName(): TokenName {
        return TokenName.DIV
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class LeftParenthesisToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.LEFT_PARENTHESIS
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class RightParenthesisToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.RIGHT_PARENTHESIS
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
