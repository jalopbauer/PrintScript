
import token.DoubleNumberLiteralToken
import token.IntNumberLiteralToken

interface AstBuilder<T : ValidListOfTokens> {
    fun build(validListOfTokens: T): AbstractSyntaxTree
}

class PrintlnBuilder : AstBuilder<PrintlnValidListOfTokens> {
    override fun build(validListOfTokens: PrintlnValidListOfTokens): PrintlnAst {
        return when (validListOfTokens.printlnValidParameter) {
            is VariableParameter -> PrintlnAst(VariableNameNode(validListOfTokens.printlnValidParameter.variableToken.value))
            is NumberLiteralParameter ->
                when (validListOfTokens.printlnValidParameter.numberLiteralToken) {
                    is DoubleNumberLiteralToken -> PrintlnAst(DoubleNumberLiteral(validListOfTokens.printlnValidParameter.numberLiteralToken.value))
                    is IntNumberLiteralToken -> PrintlnAst(IntNumberLiteral(validListOfTokens.printlnValidParameter.numberLiteralToken.value))
                }
            is StringLiteralOrStringConcat -> PrintlnAst(StringLiteralOrStringConcatAstBuilder().build(validListOfTokens.printlnValidParameter))
        }
    }
}
class StringLiteralOrStringConcatAstBuilder : AstBuilder<StringLiteralOrStringConcat> {
    override fun build(validListOfTokens: StringLiteralOrStringConcat): PrintlnAstParameter {
        TODO("Not yet implemented")
    }
}
//
// class OperationAstBuilder : AstBuilder<OperationValidListOfConcatTokens> {
//    override fun build(validListOfTokens: OperationValidListOfConcatTokens): ValuedNode<*> {
//        TODO("Not yet implemented")
//    }
// }

// class DeclarationBuilder : AstBuilder<DeclarationValidListOfTokens> {
//    override fun build(validListOfTokens: DeclarationValidListOfTokens): DeclarationAst {
//        return DeclarationAst(VariableNameNode(validListOfTokens.variable.value), TypeNode(validListOfTokens.type.tokenName()))
//    }
// } TODO (Tiene que devolver el tipo correcto)
// class AssignationBuilder : AstBuilder<AssignationValidListOfTokens> {
//    override fun build(validListOfTokens: AssignationValidListOfTokens): AbstractSyntaxTree {
//        if (OperationValidator().validateChain(validListOfTokens.content)) {
//            return AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content))
//        }
//        return AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderString(validListOfTokens.content))
//    }
// }
// class DeclarationAssignationBuilder : AstBuilder<DeclarationAssignationValidListOfTokens> {
//    override fun build(validListOfTokens: DeclarationAssignationValidListOfTokens): AbstractSyntaxTree {
//        return AssignationDeclarationAst(AssignationAst(VariableNameNode(validListOfTokens.variable.value), ShuntingYardImpl().orderNumber(validListOfTokens.content)), DeclarationAst(VariableNameNode(validListOfTokens.variable.value), TypeNode(validListOfTokens.type.tokenName().name)))
//    }
// }
// TODO (Tiene que devolver el tipo correcto)
