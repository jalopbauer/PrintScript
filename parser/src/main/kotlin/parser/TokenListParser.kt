package parser

import token.Token
interface TokenListParser<T> : Parser<T, List<Token>>
