import token.NumberLiteralToken
import token.Token
import token.TokenName
import token.VariableLiteralToken

interface AstValidator<T : ValidListOfTokens> {
    fun validate(tokens: List<Token>): T?
}

class PrintlnAstValidator : AstValidator<PrintlnValidListOfTokens> {
    override fun validate(tokens: List<Token>): PrintlnValidListOfTokens? =
        if (tokens.size < 5 ||
            tokens.component1().tokenName() != TokenName.PRINT ||
            tokens.component2().tokenName() != TokenName.LEFT_PARENTHESIS ||
            tokens[tokens.size - 2].tokenName() != TokenName.RIGHT_PARENTHESIS
        ) {
            null
        } else {
            PrintlnParameterAstValidator().validate(tokens.subList(2, tokens.size - 2))?.let { PrintlnValidListOfTokens(it) }
        }
}

class PrintlnParameterAstValidator : AstValidator<PrintlnValidParameter> {
    override fun validate(tokens: List<Token>): PrintlnValidParameter? =
        when {
            tokens.size == 1 && tokens.component1() is VariableLiteralToken -> VariableParameter(tokens.component1() as VariableLiteralToken)
            tokens.size == 1 && tokens.component1() is NumberLiteralToken -> NumberLiteralParameter(tokens.component1() as NumberLiteralToken)
            else -> StringLiteralOrConcatValidator().validate(tokens)
        }
}

class StringLiteralOrConcatValidator : AstValidator<StringLiteralOrStringConcat> {
    override fun validate(tokens: List<Token>): StringLiteralOrStringConcat? {
        var tokenCounter = 1
        for (token in tokens) {
            if (tokenCounter % 2 == 0) {
                if (token.tokenName() != TokenName.SUM) {
                    return null
                }
            } else if (token.tokenName() != TokenName.STRING_LITERAL) {
                return null
            }
            tokenCounter++
        }
        return StringLiteralOrStringConcat(tokens)
    }
}
