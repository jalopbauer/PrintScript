import token.Token
import token.TokenName
import token.VariableLiteralToken

interface SentenceValidator {
    fun build(tokenList: List<Token>): Sentence?
    fun typeCheckString(tokens: List<Token>): Boolean =
        !tokens
            .any {
                it.tokenName() != TokenName.SUM ||
                    it.tokenName() != TokenName.STRING_LITERAL
            }

    fun typeCheckNumber(tokens: List<Token>): Boolean =
        !tokens
            .any {
                it.tokenName() != TokenName.SUM ||
                    it.tokenName() != TokenName.SUB ||
                    it.tokenName() != TokenName.MULT ||
                    it.tokenName() != TokenName.DIV ||
                    it.tokenName() != TokenName.NUMBER_LITERAL
            }
}
class PrintScriptValidator : SentenceValidator {
    override fun build(tokenList: List<Token>): Sentence? {
        return ListValidator(
            listOf(
                DeclarationAssignationValidator(),
                DeclarationValidator(),
                AssignationValidator(),
                PrintLnValidator()
            )
        ).build(tokenList)
    }
}

class ListValidator(private val builders: List<SentenceValidator>) : SentenceValidator {
    override fun build(tokenList: List<Token>): Sentence? {
        return builders.fold(null) {
                acc, sentenceBuilder ->
            return if (acc == null) {
                sentenceBuilder.build(tokenList)
            } else {
                acc
            }
        }
    }
}
class DeclarationValidator : SentenceValidator {
    override fun build(tokenList: List<Token>): Declaration? {
        return if (tokenList.size < 5) {
            null
        } else if (tokenList.component1().tokenName() != TokenName.LET) {
            null
        } else if (tokenList.component2().tokenName() != TokenName.VARIABLE) {
            null
        } else if (tokenList.component3().tokenName() != TokenName.DECLARATION) {
            null
        } else if (tokenList.component4().tokenName() != TokenName.STRING_TYPE ||
            tokenList.component4().tokenName() != TokenName.NUMBER_TYPE
        ) {
            null
        } else {
            Declaration(tokenList.component2() as VariableLiteralToken, tokenList.component4())
        }
    }
}

class PrintLnValidator : SentenceValidator {
    override fun build(tokenList: List<Token>): Println? {
        return if (tokenList.size < 5) {
            null
        } else if (tokenList.component1().tokenName() != TokenName.PRINT) {
            null
        } else if (tokenList.component2().tokenName() != TokenName.LEFT_PARENTHESIS) {
            null
        } else if (tokenList[tokenList.size - 2].tokenName() != TokenName.RIGHT_PARENTHESIS) {
            null
        } else {
            Println(tokenList.subList(2, tokenList.size - 2))
        }
    }
}

class AssignationValidator : SentenceValidator {
    override fun build(tokenList: List<Token>): Assignation? {
        return if (tokenList.size < 3) {
            null
        } else if (tokenList.component1().tokenName() != TokenName.VARIABLE) {
            null
        } else if (tokenList.component2().tokenName() != TokenName.ASSIGNATION) {
            null
        } else if (!(typeCheckString(tokenList.subList(2, tokenList.size-1))||typeCheckNumber(tokenList.subList(2, tokenList.size-1)))) {
            null
        } else {
            Assignation(tokenList.subList(2, tokenList.size - 2))
        }
    }
}

class DeclarationAssignationValidator : SentenceValidator {
    override fun build(tokenList: List<Token>): Sentence? {
        DeclarationValidator().build(tokenList)
            ?.let {
                    declaration ->
                AssignationValidator().build(tokenListWithoutLetVariableType(tokenList))
                    ?.let {
                            assignation ->
                        return DeclarationAssignation(declaration, assignation)
                    }
            }
        return null
    }
    private fun tokenListWithoutLetVariableType(tokenList: List<Token>): List<Token> {
        return tokenList.drop(0).drop(2).drop(3)
    }
}
