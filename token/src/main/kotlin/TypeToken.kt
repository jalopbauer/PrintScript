package token
interface TypeToken : Token
data class NumberTypeToken(private val lineNumber: Int, private val position: Int) : TypeToken {
    override fun tokenName(): TokenName {
        return TokenName.NUMBER_TYPE
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
