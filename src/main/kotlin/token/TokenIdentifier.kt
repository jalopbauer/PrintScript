package token

class TokenIdentifier(val tokenName: TokenName, val regex: Regex)

var LET_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.LET, Regex("let"))
var TYPE_ASSIGNATION_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.TYPE_ASSIGNATION, Regex(":"))
var SEMICOLON_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.SEMICOLON, Regex(";"))
var VALUE_ASSIGNATION_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.VALUE_ASSIGNATION, Regex("="))
var SUM_OPERATION_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.SUM, Regex("\\+"))
var SUB_OPERATION_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.SUB, Regex("-"))
var MULT_OPERATION_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.MULT, Regex("\\*"))
var DIV_OPERATION_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.DIV, Regex("/"))
var NUMBER_TYPE_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.NUMBER_TYPE, Regex("number"))
var STRING_TYPE_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.STRING_TYPE, Regex("string"))
var NUMBER_LITERAL_TOKEN: TokenIdentifier =
    TokenIdentifier(TokenName.NUMBER_LITERAL, Regex("([0-9]+.[0-9]+)|[0-9]+"))
var STRING_LITERAL_TOKEN: TokenIdentifier =
    TokenIdentifier(TokenName.STRING_LITERAL, Regex("('(.)*')|(\"(.)*\")"))
var VARIABLE_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.VARIABLE, Regex(".*"))
var PRINTLN_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.PRINT, Regex("println"))
var LEFT_PARENTHESIS_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.LEFT_PARENTHESIS, Regex("\\("))
var RIGHT_PARENTHESIS_TOKEN: TokenIdentifier = TokenIdentifier(TokenName.RIGHT_PARENTHESIS, Regex("\\)"))
