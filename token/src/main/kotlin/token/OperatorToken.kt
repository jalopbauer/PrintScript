package token

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
