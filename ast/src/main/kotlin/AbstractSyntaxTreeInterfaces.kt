interface AbstractSyntaxTree
data class VariableNameNode(val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode, FinalOperationParameter, ConcatenationParameter {
    fun value(): String =
        variableName
}
data class VariableInstance(val variableNameNode: VariableNameNode, val type: Type) : AbstractSyntaxTree
interface Type : AbstractSyntaxTree
object StringType : Type
object IntType : Type
object DoubleType : Type
