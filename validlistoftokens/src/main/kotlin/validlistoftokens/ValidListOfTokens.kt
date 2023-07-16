package validlistoftokens

import token.BooleanLiteralToken
import token.NumberLiteralToken
import token.Token
import token.TypeToken
import token.VariableNameToken
interface ValidListOfTokens

sealed interface SentenceValidListOfTokens : ValidListOfTokens

class SentencesValidListOfTokens(val sentences: List<SentenceValidListOfTokens>) : ValidListOfTokens
class PrintlnValidListOfTokens(val printLnParameterValidListOfTokens: PrintlnParameterValidListOfTokens) :
    ValidListOfTokens, SentenceValidListOfTokens
class DeclarationValidListOfTokens(val type: TypeToken, val variable: VariableNameToken) : ValidListOfTokens, SentenceValidListOfTokens
class AssignationValidListOfTokens(val variable: VariableNameToken, val content: List<Token>) : ValidListOfTokens, SentenceValidListOfTokens
class DeclarationAssignationValidListOfTokens(val variable: VariableNameToken, val content: List<Token>, val type: TypeToken) :
    ValidListOfTokens, SentenceValidListOfTokens

sealed interface ConstDeclarationAssignationValidListOfTokens :
    ValidListOfTokens, SentenceValidListOfTokens
class ConstDeclarationAssignationListValidListOfTokens(val variable: VariableNameToken, val content: List<Token>, val type: TypeToken) : ConstDeclarationAssignationValidListOfTokens
sealed interface PrintlnParameterValidListOfTokens : ValidListOfTokens
class VariableParameter(val variableToken: VariableNameToken) : ValidListOfTokens, PrintlnParameterValidListOfTokens
class NumberLiteralParameter(val numberLiteralToken: NumberLiteralToken) :
    ValidListOfTokens,
    PrintlnParameterValidListOfTokens,
    OperationValidListOfTokens
class StringLiteralOrStringConcatValidListOfTokens(val stringOrConcat: List<Token>) :
    ValidListOfTokens,
    PrintlnParameterValidListOfTokens
sealed interface OperationValidListOfTokens : ValidListOfTokens, PrintlnParameterValidListOfTokens
class OperationValidListOfConcatTokens(val operationConcat: List<Token>) : OperationValidListOfTokens

class BooleanLiteralParameter(val booleanLiteralToken: BooleanLiteralToken) :
    PrintlnParameterValidListOfTokens
