package ast

interface AbstractSyntaxTree
data class VariableNameNode(val variableName: String) :
    AbstractSyntaxTree,
    PrintlnAstParameter,
    AssignationParameterNode,
    FinalOperationParameter,
    ConcatenationParameter {
    fun value(): String =
        variableName
}
data class VariableInstance(val variableNameNode: VariableNameNode, val type: Type) : AbstractSyntaxTree
interface Type : AbstractSyntaxTree
object StringType : Type
open class NumberType : Type
object IntType : NumberType()
object DoubleType : NumberType()
object ErrorType : Type

object BooleanType : Type
class ReadInputAst : AbstractSyntaxTree, PrintlnAstParameter
