import token.Token
import token.TokenName

interface SentenceBuilder {
    fun build(tokenList: List<Token>): Sentence?
    fun typeCheckString(tokens: List<Token>): Boolean {
        if (tokens.size % 2 == 0) {
            return false
        }
        var tokenCounter = 1
        for (token in tokens) {
            if (tokenCounter % 2 == 0) {
                if (token.tokenName != TokenName.SUM) {
                    return false
                }
            } else if (token.tokenName != TokenName.STRING_LITERAL) {
                return false
            }
            tokenCounter++
        }
        return true
    }

    // No me mates jorge, hago lo que puedo
    fun typeCheckNumber(tokens: List<Token>): Boolean {
        if (tokens.size % 2 == 0) {
            return false
        }
        val tokencounter = 1
        for (token in tokens) {
            if (tokencounter % 2 == 0) {
                if (token.tokenName != TokenName.SUM ||
                    token.tokenName != TokenName.SUB ||
                    token.tokenName != TokenName.MULT ||
                    token.tokenName != TokenName.DIV
                ) {
                    return false
                } else if (token.tokenName != TokenName.NUMBER_LITERAL) {
                    return false
                }
            }
        }
        return true
    }
}

class PrintScriptBuilder : SentenceBuilder {
    override fun build(tokenList: List<Token>): Sentence? {
        return ListBuilder(
            listOf(
                DeclarationAssignationBuilder(),
                DeclarationBuilder(),
                AssignationBuilder(),
                PrintlnBuilder()
            )
        ).build(tokenList)
    }
}

class ListBuilder(private val builders: List<SentenceBuilder>) : SentenceBuilder {
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
class DeclarationBuilder : SentenceBuilder {
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
            Declaration(tokenList.component2(), tokenList.component4())
        }
    }
}

class PrintlnBuilder : SentenceBuilder {
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

class AssignationBuilder : SentenceBuilder {
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

class DeclarationAssignationBuilder : SentenceBuilder {
    override fun build(tokenList: List<Token>): Sentence? {
        DeclarationBuilder().build(tokenList)
            ?.let {
                    declaration ->
                AssignationBuilder().build(tokenListWithoutLetVariableType(tokenList))
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
