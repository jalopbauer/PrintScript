package validlistoftokens

import token.DeclarationToken
import token.LetToken
import token.Token
import token.TypeToken
import token.VariableNameToken

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
