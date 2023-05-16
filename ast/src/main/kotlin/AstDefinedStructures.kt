// Println
data class PrintlnAst(private val value: PrintlnAstParameter) : AbstractSyntaxTree {
    fun value(): PrintlnAstParameter =
        value
}
interface PrintlnAstParameter : AbstractSyntaxTree

// DeclarationAst
data class DeclarationAst(private val variableNameNode: VariableNameNode, private val type: Type) : AbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): Type =
        type
}

// Assignation
data class AssignationAst(private val variableNameNode: VariableNameNode, private val assignationParameter: AssignationParameterNode) : AbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): AssignationParameterNode =
        assignationParameter
}
interface AssignationParameterNode : AbstractSyntaxTree

// Assignation Declaration
data class AssignationDeclarationAst(private val assignation: AssignationAst, private val declaration: DeclarationAst) : AbstractSyntaxTree {
    fun leftValue(): AssignationAst =
        assignation

    fun rightValue(): DeclarationAst =
        declaration
}

data class ConstAssignationDeclarationAst(private val assignation: AssignationAst, private val declaration: DeclarationAst) : AbstractSyntaxTree {
    fun leftValue(): AssignationAst =
        assignation

    fun rightValue(): DeclarationAst =
        declaration
}
