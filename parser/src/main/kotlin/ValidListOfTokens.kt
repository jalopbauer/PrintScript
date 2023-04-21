import token.NumberLiteralToken
import token.Token
import token.VariableLiteralToken

interface ValidListOfTokens
class PrintlnValidListOfTokens(val printLnParameterValidListOfTokens: PrintlnParameterValidListOfTokens) : ValidListOfTokens
class DeclarationValidListOfTokens(val type: Token, val variable: VariableLiteralToken) : ValidListOfTokens
class AssignationValidListOfTokens(val variable: VariableLiteralToken, val content: List<Token>) : ValidListOfTokens
class DeclarationAssignationValidListOfTokens(val variable: VariableLiteralToken, val content: List<Token>, val type: Token) : ValidListOfTokens

sealed interface PrintlnParameterValidListOfTokens : ValidListOfTokens
class VariableParameter(val variableToken: VariableLiteralToken) : ValidListOfTokens, PrintlnParameterValidListOfTokens
class NumberLiteralParameter(val numberLiteralToken: NumberLiteralToken) : ValidListOfTokens, PrintlnParameterValidListOfTokens, OperationValidListOfTokens
class StringLiteralOrStringConcatValidListOfTokens(val stringOrConcat: List<Token>) : ValidListOfTokens, PrintlnParameterValidListOfTokens
interface OperationValidListOfTokens : ValidListOfTokens
class OperationValidListOfConcatTokens(val operationConcat: List<Token>) : OperationValidListOfTokens
