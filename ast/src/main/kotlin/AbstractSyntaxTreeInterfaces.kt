interface AbstractSyntaxTree
data class VariableNameNode(val variableName: String) : AbstractSyntaxTree, PrintlnAstParameter, AssignationParameterNode<String> {
    fun value(): String =
        variableName
}
data class TypeNode(private val type: Type) : AbstractSyntaxTree {
    fun value(): Type =
        type
}
enum class Type {
    STRING,
    INT
}
