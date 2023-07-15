package validlistoftokens

import token.AssignationToken
import token.BooleanLiteralToken
import token.BooleanTypeToken
import token.ConstToken
import token.DeclarationToken
import token.NumberTypeToken
import token.StringTypeToken
import token.Token
import token.TypeToken
import token.VariableNameToken

class ConstDeclarationAssignationValidListOfTokensBuilder :
    ValidListOfTokensBuilder<ConstDeclarationAssignationValidListOfTokens> {
    override fun validate(tokens: List<Token>): ConstDeclarationAssignationValidListOfTokens? {
        if (tokens.size >= 7 &&
            tokens.component1() is ConstToken &&
            tokens.component2() is VariableNameToken &&
            tokens.component3() is DeclarationToken &&
            (tokens.component4() is TypeToken) &&
            tokens.component5() is AssignationToken
        ) {
            val parameterTokens = tokens.subList(5, (tokens.size - 1))
            return tokens.getOrNull(5).takeIf { it is BooleanLiteralToken }
                ?.let { booleanLiteral ->
                    tokens.component4().takeIf { it is BooleanTypeToken }?.let {
                        ConstDeclarationAssignationValidListOfTokens(
                            tokens.component2() as VariableNameToken,
                            listOf(booleanLiteral as BooleanLiteralToken),
                            it as BooleanTypeToken
                        )
                    }
                }
                ?: OperationValidListOfTokensBuilder().validate(parameterTokens)
                    ?.let {
                        tokens.component4().takeIf { it is NumberTypeToken }
                            ?.let {
                                ConstDeclarationAssignationValidListOfTokens(
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
                                ConstDeclarationAssignationValidListOfTokens(
                                    tokens.component2() as VariableNameToken,
                                    tokens.subList(5, (tokens.size - 1)),
                                    tokens.component4() as TypeToken
                                )
                            }
                    }
        }
        return null
    }
}
