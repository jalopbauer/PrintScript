package ast

// String Concatenation
data class StringConcatenation(val concatenationParameterValues: List<ConcatenationParameter>) :
    AbstractSyntaxTree,
    PrintlnAstParameter,
    AssignationParameterNode
sealed interface ConcatenationParameter : AbstractSyntaxTree

// ast.Operation
sealed interface OperationParameter : AbstractSyntaxTree, AssignationParameterNode, PrintlnAstParameter
sealed interface FinalOperationParameter : OperationParameter

data class Operation(val left: OperationParameter, val operation: OperationType, val right: OperationParameter) :
    OperationParameter,
    AssignationParameterNode
sealed interface OperationType

class Sum : OperationType
class Sub : OperationType
class Div : OperationType
class Mult : OperationType
