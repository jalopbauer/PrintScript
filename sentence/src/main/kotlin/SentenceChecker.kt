import token.Token
import token.TokenName

interface SentenceChecker {
    fun check(tokens: List<Token>): SentenceType?
}

class SentenceCheckerImpl() : SentenceChecker {
    override fun check(tokens: List<Token>): SentenceType {
        // TODO Hacerlo mas prolijo
        if (tokens.size == 5 &&
            tokens.component1().tokenName() == TokenName.LET &&
            tokens.component2().tokenName() == TokenName.VARIABLE &&
            tokens.component3().tokenName() == TokenName.DECLARATION &&
            (tokens.component4().tokenName() == TokenName.STRING_TYPE || tokens.component4().tokenName() == TokenName.NUMBER_TYPE)
        ) {
            return DeclarationType
        } else if (tokens.size >= 4 &&
            tokens.component1().tokenName() == TokenName.VARIABLE &&
            tokens.component2().tokenName() == TokenName.ASSIGNATION &&
            (checkString(tokens.subList(2, (tokens.size - 1))) || checkOperation(tokens.subList(2, (tokens.size - 1))))
        ) {
            return AssignationType
        } else if (tokens.size >= 5 &&
            tokens.component1().tokenName() == TokenName.PRINT &&
            tokens.component2().tokenName() == TokenName.LEFT_PARENTHESIS &&
            tokens[tokens.size - 2].tokenName() == TokenName.RIGHT_PARENTHESIS &&
            (tokens.component3().tokenName() == TokenName.VARIABLE || checkString(tokens.subList(2, (tokens.size - 2))))
        ) {
            return PrintlnType
        } else if (tokens.size >= 7 &&
            tokens.component1().tokenName() == TokenName.LET &&
            tokens.component2().tokenName() == TokenName.VARIABLE &&
            tokens.component3().tokenName() == TokenName.DECLARATION &&
            (tokens.component4().tokenName() == TokenName.STRING_TYPE || tokens.component4().tokenName() == TokenName.NUMBER_TYPE) &&
            tokens.component5().tokenName() == TokenName.ASSIGNATION &&
            (checkString(tokens.subList(5, (tokens.size - 1))) || checkOperation(tokens.subList(5, (tokens.size - 1))))
        ) {
            return AssignationDeclarationType
        }
        return ErrorType
    }

    private fun checkString(tokens: List<Token>): Boolean {
        if (tokens.size % 2 == 0) {
            return false
        }
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

    private fun checkOperation(tokens: List<Token>): Boolean {
        // numero tiene antes un operador o un parentesis abierto
        // operador tiene atras un numero o un parentesis cerrao
        // parentesis abierto tiene atras un operador
        // perentesis cerrado tiene atras un numero
        var previousToken = tokens[0].tokenName()
        if (tokens.size == 1) {
            return previousToken == TokenName.NUMBER_LITERAL
        }
        for (token in tokens.subList(1, tokens.size)) {
            when (token.tokenName()) {
                TokenName.NUMBER_LITERAL -> if (!(
                    previousToken == TokenName.LEFT_PARENTHESIS ||
                        previousToken == TokenName.SUM ||
                        previousToken == TokenName.SUB ||
                        previousToken == TokenName.MULT ||
                        previousToken == TokenName.DIV
                    )
                ) { return false }
                TokenName.SUM, TokenName.SUB, TokenName.MULT, TokenName.DIV -> if (!(
                    previousToken == TokenName.NUMBER_LITERAL ||
                        previousToken == TokenName.RIGHT_PARENTHESIS
                    )
                ) { return false }
                TokenName.LEFT_PARENTHESIS -> if (!(
                    previousToken == TokenName.SUM ||
                        previousToken == TokenName.SUB ||
                        previousToken == TokenName.MULT ||
                        previousToken == TokenName.DIV
                    )
                ) { return false }
                TokenName.RIGHT_PARENTHESIS -> if (previousToken != TokenName.NUMBER_LITERAL) { return false }
                else -> return false
            }
            previousToken = token.tokenName()
        }
        return true
    }
}
