package token
interface Token {
    fun tokenName(): TokenName
    fun lineNumber(): Int
    fun position(): Int
}

sealed interface IfStatementValidListOfTokensParameter : Token

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
data class VariableNameToken(val value: String, private val lineNumber: Int, private val position: Int) : Token, IfStatementValidListOfTokensParameter {
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
data class LetToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.LET
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class DeclarationToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.DECLARATION
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class PrintlnToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.PRINT
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class SemicolonToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.SEMICOLON
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class AssignationToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.ASSIGNATION
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class IfToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.IF
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class ElseToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.ELSE
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class LeftCurlyBracketsToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.LEFT_CURLY_BRACKETS
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class RightCurlyBracketsToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.RIGHT_CURLY_BRACKETS
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}
data class ReadInputToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.READ_INPUT
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

data class ConstToken(private val lineNumber: Int, private val position: Int) : Token {
    override fun tokenName(): TokenName {
        return TokenName.CONST
    }

    override fun lineNumber(): Int {
        return lineNumber
    }

    override fun position(): Int {
        return position
    }
}

sealed interface BooleanLiteralToken : Token, IfStatementValidListOfTokensParameter
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
