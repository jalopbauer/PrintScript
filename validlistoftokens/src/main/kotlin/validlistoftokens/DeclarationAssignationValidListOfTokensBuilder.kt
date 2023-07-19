package validlistoftokens

import token.AssignationToken
import token.BooleanLiteralToken
import token.BooleanTypeToken
import token.DeclarationToken
import token.LetToken
import token.NumberTypeToken
import token.StringTypeToken
import token.Token
import token.TypeToken
import token.VariableNameToken

class DeclarationAssignationValidListOfTokensBuilder :
    ValidListOfTokensBuilder<DeclarationAssignationValidListOfTokens> {
    override fun validate(tokens: List<Token>): DeclarationAssignationValidListOfTokens? {
        if (tokens.size >= 7 &&
            tokens.component1() is LetToken &&
            tokens.component2() is VariableNameToken &&
            tokens.component3() is DeclarationToken &&
            (tokens.component4() is TypeToken) &&
            tokens.component5() is AssignationToken
        ) {
            val parameterTokens = tokens.subList(5, (tokens.size - 1))
            return OperationValidListOfTokensBuilder().validate(parameterTokens)
                ?.let {
                    tokens.component4().takeIf { it is NumberTypeToken }
                        ?.let {
                            DeclarationAssignationValidListOfTokens(
                                tokens.component2() as VariableNameToken,
                                tokens.subList(5, (tokens.size - 1)),
                                tokens.component4() as TypeToken
                            )
                        }
                }
                ?: StringLiteralOrConcatValidListOfTokensBuilder().validate(parameterTokens)
                    ?.let {
                        tokens.component4().takeIf { it is StringTypeToken }
                            ?.let {
                                DeclarationAssignationValidListOfTokens(
                                    tokens.component2() as VariableNameToken,
                                    tokens.subList(5, (tokens.size - 1)),
                                    tokens.component4() as TypeToken
                                )
                            }
                    }
                ?: tokens.getOrNull(5).takeIf { it is BooleanLiteralToken }
                    ?.let { booleanLiteral ->
                        tokens.component4().takeIf { it is BooleanTypeToken }?.let {
                            DeclarationAssignationValidListOfTokens(
                                tokens.component2() as VariableNameToken,
                                listOf(booleanLiteral as BooleanLiteralToken),
                                it as BooleanTypeToken
                            )
                        }
                    }
        } else {
            return null
        }
    }
}
