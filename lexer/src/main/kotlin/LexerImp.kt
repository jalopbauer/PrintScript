package lexer

//import lexer.Exceptions.IllegalStringException
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

        //let
        if (item == "let"){
            return TokenName.LET
        }
        //variable

        //declaration
        if (item == ":"){
            return TokenName.DECLARATION
        }
        //string
        if (item == "string"){
            return TokenName.STRING_TYPE
        }
        //number
        if (item == "number"){
            return TokenName.NUMBER_TYPE
        }
        //assignation
        if (item == "="){
            return TokenName.ASSIGNATION
        }
        //literalString
        if (item[0].code == 39 && item[item.length-1].code == 39){
            return TokenName.STRING_LITERAL
        }
        //literalNumber
        if (item.toIntOrNull() != null){
            return TokenName.NUMBER_LITERAL
        }
        //sum
        if (item == "+"){
            return TokenName.SUM
        }
        //sub
        if (item == "-"){
            return TokenName.SUB
        }
        //mul
        if (item == "*"){
            return TokenName.MULT
        }
        //div
        if (item == "/"){
            return TokenName.DIV
        }
        //print
        if (item == "println"){
            return TokenName.PRINT
        }
        //(
        if (item == "("){
            return TokenName.LEFT_PARENTHESIS
        }
        //)
        if (item == ")"){
            return TokenName.RIGHT_PARENTHESIS
        }
        //;
        if (item == ";"){
            return TokenName.SEMICOLON
        }
        //Si no reconoce ninguna se rompe
        return TokenName.VARIABLE
//        throw IllegalStringException("The string " + item + " doesn't match any Token")
    }

}