import token.AssignationToken
import token.BooleanLiteralToken
import token.DeclarationToken
import token.LetToken
import token.NumberLiteralToken
import token.ReadInputToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.TypeToken
import token.VariableNameToken

interface ValidListOfTokensBuilder<T : ValidListOfTokens> {
    fun validate(tokens: List<Token>): T?
}

class PrintlnValidListOfTokensBuilder : ValidListOfTokensBuilder<PrintlnValidListOfTokens> {
    override fun validate(tokens: List<Token>): PrintlnValidListOfTokens? =
        if (tokens.size < 5 ||
            tokens.component1().tokenName() != TokenName.PRINT ||
            tokens.component2().tokenName() != TokenName.LEFT_PARENTHESIS ||
            tokens[tokens.size - 2].tokenName() != TokenName.RIGHT_PARENTHESIS
        ) {
            null
        } else {
            PrintlnParameterValidListOfTokensBuilder().validate(tokens.subList(2, tokens.size - 2))?.let {
                PrintlnValidListOfTokens(
                    it
                )
            }
        }
}

class PrintlnParameterValidListOfTokensBuilder : ValidListOfTokensBuilder<PrintlnParameterValidListOfTokens> {
    override fun validate(tokens: List<Token>): PrintlnParameterValidListOfTokens? =
        when (tokens.size) {
            1 -> {
                when (val singleToken = tokens.component1()) {
                    is ReadInputToken -> ReadInputParameter(singleToken)
                    is VariableNameToken -> VariableParameter(singleToken)
                    is StringLiteralToken -> StringLiteralOrStringConcatValidListOfTokens(listOf(singleToken))
                    is NumberLiteralToken -> NumberLiteralParameter(singleToken)
                    is BooleanLiteralToken -> BooleanLiteralParameter(singleToken)
                    else -> null
                }
            }
            else -> StringLiteralOrConcatValidListOfTokensBuilder().validate(tokens)
        }
}

class StringLiteralOrConcatValidListOfTokensBuilder :
    ValidListOfTokensBuilder<StringLiteralOrStringConcatValidListOfTokens> {
    override fun validate(tokens: List<Token>): StringLiteralOrStringConcatValidListOfTokens? {
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
        return StringLiteralOrStringConcatValidListOfTokens(tokens)
    }
    fun validateChain(tokens: List<Token>): Boolean {
        var tokenCounter = 1
        for (token in tokens) {
            if (tokenCounter % 2 == 0) {
                if (token.tokenName() != TokenName.SUM) {
                    return false
                }
            } else if (token.tokenName() != TokenName.STRING_LITERAL) {
                return false
            }
            tokenCounter++
        }
        return true
    }
}

class DeclarationValidListOfTokensBuilder : ValidListOfTokensBuilder<DeclarationValidListOfTokens> {
    override fun validate(tokens: List<Token>): DeclarationValidListOfTokens? {
        if (tokens.size == 5 &&
            tokens.component1() is LetToken &&
            tokens.component2() is VariableNameToken &&
            tokens.component3() is DeclarationToken &&
            (tokens.component4() is TypeToken)
        ) {
            return DeclarationValidListOfTokens(tokens.component4() as TypeToken, tokens.component2() as VariableNameToken)
        }
        return null
    }
}

class AssignationValidListOfTokensBuilder : ValidListOfTokensBuilder<AssignationValidListOfTokens> {
    override fun validate(tokens: List<Token>): AssignationValidListOfTokens? {
        if (tokens.size >= 3 &&
            tokens.component1() is VariableNameToken &&
            tokens.component2() is AssignationToken &&
            (
                tokens.component3() is VariableNameToken ||
                    tokens.component3() is BooleanLiteralToken ||
                    StringLiteralOrConcatValidListOfTokensBuilder().validateChain(tokens.subList(2, (tokens.size - 1))) ||
                    OperationValidListOfTokensBuilder().validateChain(tokens.subList(2, (tokens.size - 1)))
                )
        ) {
            return AssignationValidListOfTokens(
                tokens.component1() as VariableNameToken,
                tokens.subList(2, (tokens.size - 1))
            )
        }
        return null
    }
}
class DeclarationAssignationValidListOfTokensBuilder :
    ValidListOfTokensBuilder<DeclarationAssignationValidListOfTokens> {
    override fun validate(tokens: List<Token>): DeclarationAssignationValidListOfTokens? {
        if (tokens.size >= 7 &&
            tokens.component1().tokenName() == TokenName.LET &&
            tokens.component2().tokenName() == TokenName.VARIABLE &&
            tokens.component3().tokenName() == TokenName.DECLARATION &&
            (tokens.component4() is TypeToken) &&
            tokens.component5().tokenName() == TokenName.ASSIGNATION &&
            (StringLiteralOrConcatValidListOfTokensBuilder().validateChain(tokens.subList(5, (tokens.size - 1))) || OperationValidListOfTokensBuilder().validateChain(tokens.subList(5, (tokens.size - 1))))
        ) {
            return DeclarationAssignationValidListOfTokens(
                tokens.component2() as VariableNameToken,
                tokens.subList(5, (tokens.size - 1)),
                tokens.component4() as TypeToken
            )
        }
        return null
    }
}

class OperationValidListOfTokensBuilder : ValidListOfTokensBuilder<OperationValidListOfTokens> {
    override fun validate(tokens: List<Token>): OperationValidListOfTokens? {
        var previousToken = tokens[0]
        if (tokens.size == 1) {
            when (previousToken) {
                is NumberLiteralToken -> return NumberLiteralParameter(previousToken)
                else -> return null
            }
        }
        for (token in tokens.subList(1, tokens.size)) {
            when (token.tokenName()) {
                TokenName.NUMBER_LITERAL -> if (!(
                    previousToken.tokenName() == TokenName.LEFT_PARENTHESIS ||
                        previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return null }
                TokenName.SUM, TokenName.SUB, TokenName.MULT, TokenName.DIV -> if (!(
                    previousToken.tokenName() == TokenName.NUMBER_LITERAL ||
                        previousToken.tokenName() == TokenName.RIGHT_PARENTHESIS
                    )
                ) { return null }
                TokenName.LEFT_PARENTHESIS -> if (!(
                    previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return null }
                TokenName.RIGHT_PARENTHESIS -> if (previousToken.tokenName() != TokenName.NUMBER_LITERAL) { return null }
                else -> return null
            }
            previousToken = token
        }
        return OperationValidListOfConcatTokens(tokens)
    }
    fun validateChain(tokens: List<Token>): Boolean {
        var previousToken = tokens[0]
        if (tokens.size == 1) {
            when (previousToken) {
                is NumberLiteralToken -> return true
                else -> return false
            }
        }
        for (token in tokens.subList(1, tokens.size)) {
            when (token.tokenName()) {
                TokenName.NUMBER_LITERAL -> if (!(
                    previousToken.tokenName() == TokenName.LEFT_PARENTHESIS ||
                        previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return false }
                TokenName.SUM, TokenName.SUB, TokenName.MULT, TokenName.DIV -> if (!(
                    previousToken.tokenName() == TokenName.NUMBER_LITERAL ||
                        previousToken.tokenName() == TokenName.RIGHT_PARENTHESIS
                    )
                ) { return false }
                TokenName.LEFT_PARENTHESIS -> if (!(
                    previousToken.tokenName() == TokenName.SUM ||
                        previousToken.tokenName() == TokenName.SUB ||
                        previousToken.tokenName() == TokenName.MULT ||
                        previousToken.tokenName() == TokenName.DIV
                    )
                ) { return false }
                TokenName.RIGHT_PARENTHESIS -> if (previousToken.tokenName() != TokenName.NUMBER_LITERAL) { return false }
                else -> return false
            }
            previousToken = token
        }
        return true
    }
}
