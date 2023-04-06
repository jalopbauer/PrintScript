import token.NumberLiteralToken
import token.Token
import token.VariableLiteralToken

interface ValidListOfTokens
class PrintlnValidListOfTokens(val printlnValidParameter: PrintlnValidParameter) : ValidListOfTokens
sealed interface PrintlnValidParameter : ValidListOfTokens
class VariableParameter(val variableToken: VariableLiteralToken) : ValidListOfTokens, PrintlnValidParameter
class NumberLiteralParameter(val numberLiteralToken: NumberLiteralToken) : ValidListOfTokens, PrintlnValidParameter
class StringLiteralOrStringConcat(val stringOrConcat: List<Token>) : ValidListOfTokens, PrintlnValidParameter
