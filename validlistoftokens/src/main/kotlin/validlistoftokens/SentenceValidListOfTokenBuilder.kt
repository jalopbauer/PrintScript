package validlistoftokens

import token.ElseToken
import token.IfToken
import token.SemicolonToken
import token.Token

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
