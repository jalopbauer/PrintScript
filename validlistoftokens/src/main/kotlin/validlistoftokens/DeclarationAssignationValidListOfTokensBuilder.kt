package validlistoftokens

import token.AssignationToken
import token.BooleanLiteralToken
import token.DeclarationToken
import token.LetToken
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
            tokens.component5() is AssignationToken &&
            (
                tokens.component3() is VariableNameToken ||
                    tokens.component3() is BooleanLiteralToken ||
                    StringLiteralOrConcatValidListOfTokensBuilder().validateChain(tokens.subList(5, (tokens.size - 1))) ||
                    OperationValidListOfTokensBuilder().validateChain(tokens.subList(5, (tokens.size - 1)))
                )
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
