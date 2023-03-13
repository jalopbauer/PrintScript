import lexer.Line
import lexer.LineLexer
import token.TokenIdentifier

fun main(args: Array<String>) {
    val list: List<TokenIdentifier> = listOf(
        TokenIdentifier.TYPE_ASSIGNATION_TOKEN
        , TokenIdentifier.VALUE_ASSIGNATION_TOKEN
        , TokenIdentifier.SUM_OPERATION_TOKEN
        , TokenIdentifier.SUB_OPERATION_TOKEN
        , TokenIdentifier.MULT_OPERATION_TOKEN
        , TokenIdentifier.DIV_OPERATION_TOKEN
        , TokenIdentifier.NUMBER_TYPE_TOKEN
        , TokenIdentifier.STRING_TYPE_TOKEN
        , TokenIdentifier.PRINTLN_TOKEN
        , TokenIdentifier.LEFT_PARENTHESIS_TOKEN
        , TokenIdentifier.RIGHT_PARENTHESIS_TOKEN
        , TokenIdentifier.SEMICOLON_TOKEN
        , TokenIdentifier.LET_TOKEN
        , TokenIdentifier.NUMBER_LITERAL_TOKEN
        , TokenIdentifier.NUMBER_LITERAL_TOKEN
        , TokenIdentifier.STRING_LITERAL_TOKEN
        , TokenIdentifier.STRING_LITERAL_TOKEN
        , TokenIdentifier.VARIABLE_TOKEN
        , TokenIdentifier.SPACE
    )
    val list2: List<TokenIdentifier> = listOf(
        TokenIdentifier.TYPE_ASSIGNATION_TOKEN
        , TokenIdentifier.VALUE_ASSIGNATION_TOKEN
        , TokenIdentifier.SEMICOLON_TOKEN
        , TokenIdentifier.SPACE
    )
    val lineLexer: LineLexer = LineLexer(list, list2)
    val buildTokenList = lineLexer.buildTokenList(Line("let name : string = \"Joe\" ;", 69))
//    buildTokenList.forEach{token -> println(token.tokenIdentifier.tokenName)}
    buildTokenList.forEach{token -> println(token.value)}
//    buildTokenList.forEach{token -> println(token.line.position)}
//    println("Token list: $buildTokenList")
}