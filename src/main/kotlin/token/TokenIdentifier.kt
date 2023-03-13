package token

class TokenIdentifier(val tokenName: TokenName, val regex: Regex) {
    fun identify(string: String): Boolean {
        return regex.containsMatchIn(string)
    }

    companion object {
        // Assignations
        val TYPE_ASSIGNATION_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.TYPE_ASSIGNATION, Regex(":"))
        var VALUE_ASSIGNATION_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.VALUE_ASSIGNATION, Regex("="))

        // Operations
        var SUM_OPERATION_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.SUM, Regex("\\+"))
        var SUB_OPERATION_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.SUB, Regex("-"))
        var MULT_OPERATION_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.MULT, Regex("\\*"))
        var DIV_OPERATION_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.DIV, Regex("/"))

        // Types
        var NUMBER_TYPE_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.NUMBER_TYPE, Regex("number"))
        var STRING_TYPE_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.STRING_TYPE, Regex("string"))
        var UNDEFINED: TokenIdentifier =
            TokenIdentifier(TokenName.UNDEFINED, Regex(""))

        // Literals AKA possible values
        var NUMBER_LITERAL_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.NUMBER_LITERAL, Regex("([0-9]+.[0-9]+)|[0-9]+"))
        var STRING_LITERAL_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.STRING_LITERAL, Regex("('(.)*')|(\"(.)*\")"))

        // Functions
        var PRINTLN_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.PRINT, Regex("println"))

        // Other
        var LEFT_PARENTHESIS_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.LEFT_PARENTHESIS, Regex("\\("))
        var RIGHT_PARENTHESIS_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.RIGHT_PARENTHESIS, Regex("\\)"))
        var VARIABLE_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.VARIABLE, Regex(".*"))
        var SEMICOLON_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.SEMICOLON, Regex(";"))
        var LET_TOKEN: TokenIdentifier =
            TokenIdentifier(TokenName.LET, Regex("let"))
    }
}

