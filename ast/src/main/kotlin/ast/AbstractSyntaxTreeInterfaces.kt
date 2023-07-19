package ast

interface AbstractSyntaxTree
data class VariableNameNode(val variableName: String) :
    AbstractSyntaxTree,
    PrintlnAstParameter,
    AssignationParameterNode,
    FinalOperationParameter,
    ConcatenationParameter,
    ConditionBlockParameter {
    fun value(): String =
        variableName
}
data class VariableInstance(val variableNameNode: VariableNameNode, val type: Type) : AbstractSyntaxTree
sealed interface Type : AbstractSyntaxTree
object StringType : Type
object NumberType : Type

object BooleanType : Type
class ReadInputAst(val message: StringLiteral) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode
