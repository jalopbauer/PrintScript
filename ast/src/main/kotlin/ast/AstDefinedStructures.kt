package ast

interface SentenceAbstractSyntaxTree : AbstractSyntaxTree

// Println
data class PrintlnAst(private val value: PrintlnAstParameter) : SentenceAbstractSyntaxTree {
    fun value(): PrintlnAstParameter =
        value
}
interface PrintlnAstParameter : AbstractSyntaxTree

// ast.DeclarationAst
data class DeclarationAst(private val variableNameNode: VariableNameNode, private val type: Type) : SentenceAbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): Type =
        type
}

// Assignation
data class AssignationAst(private val variableNameNode: VariableNameNode, private val assignationParameter: AssignationParameterNode) :
    SentenceAbstractSyntaxTree {
    fun leftValue(): VariableNameNode =
        variableNameNode

    fun rightValue(): AssignationParameterNode =
        assignationParameter
}
interface AssignationParameterNode : AbstractSyntaxTree

// Assignation Declaration
sealed interface AssignationDeclarationAst : SentenceAbstractSyntaxTree {
    fun leftValue(): AssignationAst
    fun rightValue(): DeclarationAst
}

data class LetAssignationDeclarationAst(private val assignation: AssignationAst, private val declaration: DeclarationAst) :
    AssignationDeclarationAst {
    override fun leftValue(): AssignationAst =
        assignation

    override fun rightValue(): DeclarationAst =
        declaration
}

data class ConstAssignationDeclarationAst(private val assignation: AssignationAst, private val declaration: DeclarationAst) :
    AssignationDeclarationAst {
    override fun leftValue(): AssignationAst =
        assignation

    override fun rightValue(): DeclarationAst =
        declaration
}
