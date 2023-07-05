package validlistoftokens

import token.AssignationToken
import token.BooleanLiteralToken
import token.Token
import token.VariableNameToken

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
