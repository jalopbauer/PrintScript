import token.NumberLiteralToken
import token.Token
import token.VariableLiteralToken

interface ValidListOfTokens
class PrintlnValidListOfTokens(val printlnValidParameter: PrintlnValidParameter) : ValidListOfTokens
class DeclarationValidListOfTokens(val type: Token, val variable: VariableLiteralToken) : ValidListOfTokens
class AssignationValidListOfTokens(val variable: VariableLiteralToken, val content: List<Token>) : ValidListOfTokens
class DeclarationAssignationValidListOfTokens(val variable: VariableLiteralToken, val content: List<Token>, val type: Token) : ValidListOfTokens

sealed interface PrintlnValidParameter : ValidListOfTokens
class VariableParameter(val variableToken: VariableLiteralToken) : ValidListOfTokens, PrintlnValidParameter
class NumberLiteralParameter(val numberLiteralToken: NumberLiteralToken<*>) : ValidListOfTokens, PrintlnValidParameter, OperationValidListOfTokens
class StringLiteralOrStringConcat(val stringOrConcat: List<Token>) : ValidListOfTokens, PrintlnValidParameter
interface OperationValidListOfTokens : ValidListOfTokens
class OperationValidListOfConcatTokens(val operationConcat: List<Token>) : OperationValidListOfTokens
