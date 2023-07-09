package parser.astParsers

import ast.AbstractSyntaxTree
import ast.BooleanType
import ast.NumberType
import ast.StringType
import ast.Type
import token.BooleanTypeToken
import token.NumberTypeToken
import token.StringTypeToken
import token.TypeToken
import validlistoftokens.ValidListOfTokens
interface AstBuilder<T : ValidListOfTokens, U : AbstractSyntaxTree> {
    fun build(validListOfTokens: T): U

    fun findType(token: TypeToken): Type =
        when (token) {
            is BooleanTypeToken -> BooleanType
            is NumberTypeToken -> NumberType
            is StringTypeToken -> StringType
        }
}
