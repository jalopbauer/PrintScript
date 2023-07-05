package validlistoftokens

import token.AssignationToken
import token.BooleanLiteralToken
import token.DeclarationToken
import token.ElseToken
import token.IfToken
import token.LetToken
import token.NumberLiteralToken
import token.ReadInputToken
import token.SemicolonToken
import token.StringLiteralToken
import token.Token
import token.TokenName
import token.TypeToken
import token.VariableNameToken

interface ValidListOfTokensBuilder<T : ValidListOfTokens> {
    fun validate(tokens: List<Token>): T?
}

class SentenceValidListOfTokenBuilder<T : SentenceValidListOfTokens>(private val validListOfTokensBuilder: ValidListOfTokensBuilder<T>) :
    ValidListOfTokensBuilder<T> {
    override fun validate(tokens: List<Token>): T? =
        getListIfFirstValueIsDifferentThanIfOrElse(tokens)
            ?.let { getListIfLastValueIsASemicolon(it) }
            ?.let { validListOfTokensBuilder.validate(it) }

    private fun getListIfFirstValueIsDifferentThanIfOrElse(tokensInCodeBlock: List<Token>) =
        tokensInCodeBlock.firstOrNull()
            ?.takeIf { it !is IfToken }
            ?.takeIf { it !is ElseToken }
            ?.let { tokensInCodeBlock }

    private fun getListIfLastValueIsASemicolon(tokensInCodeBlock: List<Token>) =
        tokensInCodeBlock.lastOrNull()
            ?.takeIf { it is SemicolonToken }
            ?.let { tokensInCodeBlock }
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
