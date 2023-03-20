package lexer

import lexer.Exceptions.IllegalStringException
import token.Token
import token.TokenIdentifier
import token.TokenName
import java.util.Collections.emptyList
import kotlin.text.*

class LexerImp: Lexer  {
    override fun buildTokenList(sentence: String): List<Token> {
        var line: Int = 0
        var list: ArrayList<Token> = ArrayList();
        var splitSentence: List<String> = sentence.split(Regex(" |(?<=[:;()])|(?=[:;()])")).dropLast(1)
        for (item in splitSentence) {
            var tokenName: TokenName = findIdentifier(item)
            var position: Int = sentence.indexOf(item);
            list.add(Token(tokenName, item,line,position))
            if (item.equals(";")) {
                line++
            }
        }

        return list;
    }

    //TODO hay una forma de hacerlo mas prolijo pero no me acuerdo
    private fun findIdentifier(item: String): TokenName {

        //let
        if (item.equals("let")){
            return TokenName.LET
        }
        //declaration
        if (item.equals(":")){
            return TokenName.DECLARATION
        }
        //string
        if (item.equals("string")){
            return TokenName.STRING_TYPE
        }
        //number
        if (item.equals("number")){
            return TokenName.NUMBER_TYPE
        }
        //assignation
        if (item.equals("=")){
            return TokenName.ASSIGNATION
        }
        //literalString
        if (item[0].code.equals(39) && item[item.length-1].code.equals(39)){
            return TokenName.STRING_LITERAL
        }
        //literalNumber
        if (item.toIntOrNull() != null){
            return TokenName.NUMBER_LITERAL
        }
        //sum
        if (item.equals("+")){
            return TokenName.SUM
        }
        //sub
        if (item.equals("-")){
            return TokenName.SUB
        }
        //mul
        if (item.equals("*")){
            return TokenName.MULT
        }
        //div
        if (item.equals("/")){
            return TokenName.DIV
        }
        //print
        if (item.equals("println")){
            return TokenName.PRINT
        }
        //(
        if (item.equals("(")){
            return TokenName.LEFT_PARENTHESIS
        }
        //)
        if (item.equals(")")){
            return TokenName.RIGHT_PARENTHESIS
        }
        //;
        if (item.equals(";")){
            return TokenName.SEMICOLON
        }
        //variable
        if (item.matches(Regex("[a-z][a-zA-Z]+"))){
            return TokenName.VARIABLE
        }
        //Si no reconoce ninguna se rompe
        throw IllegalStringException("The string " + item + " doesn't match any Token")

    }

}