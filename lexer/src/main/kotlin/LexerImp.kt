package lexer

//import lexer.Exceptions.IllegalStringException
import token.PrintScript
import token.Token
import token.TokenName
import kotlin.text.*

class LexerImp: Lexer  {
    override fun buildTokenList(sentence: String): List<Token> {
        var line = 0
        val list: ArrayList<Token> = ArrayList()
        val splitSentence: List<String> = sentence.split(Regex(" |(?<=[:;()])|(?=[:;()])")).dropLast(1)
        for (item in splitSentence) {
            val tokenName: TokenName = findIdentifier(item)
            val position: Int = sentence.indexOf(item)
            list.add(Token(tokenName, item,line,position))
            if (item == ";") {
                line++
            }
        }

        return list
    }

    //TODO hay una forma de hacerlo mas prolijo pero no me acuerdo
    private fun findIdentifier(item: String): TokenName {

        val possibleToken = PrintScript().identify(item)
        return possibleToken ?: (
            if  (item.toIntOrNull() != null){
                return TokenName.NUMBER_LITERAL
            } else {
                //Si no reconoce ninguna se rompe
                return TokenName.VARIABLE
                //        throw IllegalStringException("The string " + item + " doesn't match any Token")

            }
        )
    }

}