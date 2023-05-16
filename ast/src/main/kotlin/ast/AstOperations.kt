package ast

// String Concatenation
data class StringConcatenation(val concatenationParameterValues: List<ConcatenationParameter>) :
    AbstractSyntaxTree,
    PrintlnAstParameter,
    AssignationParameterNode
interface ConcatenationParameter : AbstractSyntaxTree

// ast.Operation
sealed interface OperationParameter : AbstractSyntaxTree, AssignationParameterNode
sealed interface FinalOperationParameter : OperationParameter

data class Operation(val left: OperationParameter, val operation: OperationType, val right: OperationParameter) :
    OperationParameter,
    AssignationParameterNode
interface OperationType

class Sum : OperationType
class Sub : OperationType
class Div : OperationType
class Mult : OperationType
