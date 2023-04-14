interface AbstractSyntaxTree
data class VariableNameNode(val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode<String>, OperationParameter {
    fun value(): String =
        variableName
}
interface Type : AbstractSyntaxTree
object StringType : Type
object IntType : Type
object DoubleType : Type
