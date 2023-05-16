import token.BooleanLiteralToken
import token.NumberLiteralToken
import token.ReadInputToken
import token.Token
import token.TypeToken
import token.VariableNameToken
interface ValidListOfTokens
class PrintlnValidListOfTokens(val printLnParameterValidListOfTokens: PrintlnParameterValidListOfTokens) :
    ValidListOfTokens
class DeclarationValidListOfTokens(val type: TypeToken, val variable: VariableNameToken) : ValidListOfTokens
class AssignationValidListOfTokens(val variable: VariableNameToken, val content: List<Token>) : ValidListOfTokens
class DeclarationAssignationValidListOfTokens(val variable: VariableNameToken, val content: List<Token>, val type: TypeToken) :
    ValidListOfTokens
class ConstDeclarationAssignationValidListOfTokens(val variable: VariableNameToken, val content: List<Token>, val type: TypeToken) :
    ValidListOfTokens
sealed interface PrintlnParameterValidListOfTokens : ValidListOfTokens
class VariableParameter(val variableToken: VariableNameToken) : ValidListOfTokens, PrintlnParameterValidListOfTokens
class NumberLiteralParameter(val numberLiteralToken: NumberLiteralToken) :
    ValidListOfTokens,
    PrintlnParameterValidListOfTokens,
    OperationValidListOfTokens
class StringLiteralOrStringConcatValidListOfTokens(val stringOrConcat: List<Token>) :
    ValidListOfTokens,
    PrintlnParameterValidListOfTokens
interface OperationValidListOfTokens : ValidListOfTokens
class OperationValidListOfConcatTokens(val operationConcat: List<Token>) : OperationValidListOfTokens

class BooleanLiteralParameter(val booleanLiteralToken: BooleanLiteralToken) :
    PrintlnParameterValidListOfTokens

class ReadInputParameter(val readInputToken: ReadInputToken) :
    PrintlnParameterValidListOfTokens
