import token.Token
import token.TokenName
import token.VariableLiteralToken

interface SentenceValidator {
    fun build(tokenList: List<Token>): Sentence?
}
//class PrintScriptValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): Sentence? {
//        return ListValidator(
//            listOf(
//                DeclarationAssignationValidator(),
//                DeclarationValidator(),
//                AssignationValidator(),
//                PrintLnValidator()
//            )
//        ).build(tokenList)
//    }
//}
//
//class ListValidator(private val builders: List<SentenceValidator>) : SentenceValidator {
//    override fun build(tokenList: List<Token>): Sentence? {
//        return builders.fold(null) {
//                acc, sentenceBuilder ->
//            return if (acc == null) {
//                sentenceBuilder.build(tokenList)
//            } else {
//                acc
//            }
//        }
//    }
//}
//class DeclarationValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): Declaration? {
//        return if (tokenList.size < 5) {
//            null
//        } else if (tokenList.component1().tokenName() != TokenName.LET) {
//            null
//        } else if (tokenList.component2().tokenName() != TokenName.VARIABLE) {
//            null
//        } else if (tokenList.component3().tokenName() != TokenName.DECLARATION) {
//            null
//        } else if (tokenList.component4().tokenName() != TokenName.STRING_TYPE ||
//            tokenList.component4().tokenName() != TokenName.NUMBER_TYPE
//        ) {
//            null
//        } else {
//            Declaration(tokenList.component2() as VariableLiteralToken, tokenList.component4())
//        }
//    }
//}
//
//class PrintLnValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): Println? {
//        return if (tokenList.size < 5) {
//            null
//        } else if (tokenList.component1().tokenName() != TokenName.PRINT) {
//            null
//        } else if (tokenList.component2().tokenName() != TokenName.LEFT_PARENTHESIS) {
//            null
//        } else if (tokenList[tokenList.size - 2].tokenName() != TokenName.RIGHT_PARENTHESIS) {
//            null
//        } else {
//            StringSentenceValidator().build(tokenList.subList(2, tokenList.size - 2))
//                ?.let { Println(it) }
//        }
//    }
//}
//
//class AssignationValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): Assignation? {
//        return if (tokenList.size < 3) {
//            null
//        } else if (tokenList.component1().tokenName() != TokenName.VARIABLE) {
//            null
//        } else if (tokenList.component2().tokenName() != TokenName.ASSIGNATION) {
//            null
//        } else {
//            val tokensAfterEquals = tokenList.subList(2, tokenList.size - 2)
//            StringSentenceValidator().build(tokensAfterEquals)
//                ?.let { Assignation(it) }
//                ?: NumberSentenceValidator().build(tokensAfterEquals)
//                    ?.let { Assignation(it) }
//        }
//    }
//}
//
//class DeclarationAssignationValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): Sentence? {
//        DeclarationValidator().build(tokenList)
//            ?.let {
//                    declaration ->
//                AssignationValidator().build(tokenListWithoutLetVariableType(tokenList))
//                    ?.let {
//                            assignation ->
//                        return DeclarationAssignation(declaration, assignation)
//                    }
//            }
//        return null
//    }
//    private fun tokenListWithoutLetVariableType(tokenList: List<Token>): List<Token> {
//        return tokenList.drop(0).drop(2).drop(3)
//    }
//}
//
//class StringSentenceValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): StringValue? {
//        return if (!tokenList
//            .any {
//                it.tokenName() != TokenName.SUM ||
//                    it.tokenName() != TokenName.STRING_LITERAL
//            }
//        ) {
//            StringValue(tokenList)
//        } else {
//            null
//        }
//    }
//}
//
//class NumberSentenceValidator : SentenceValidator {
//    override fun build(tokenList: List<Token>): NumberValue? {
//        return if (!tokenList
//            .any {
//                it.tokenName() != TokenName.SUM ||
//                    it.tokenName() != TokenName.SUB ||
//                    it.tokenName() != TokenName.MULT ||
//                    it.tokenName() != TokenName.DIV ||
//                    it.tokenName() != TokenName.NUMBER_LITERAL
//            }
//        ) {
//            NumberValue(tokenList)
//        } else {
//            null
//        }
//    }
//}
