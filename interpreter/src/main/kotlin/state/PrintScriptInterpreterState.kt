package state
interface PrintScriptInterpreterState : InterpreterState, PrintlnInterpreterState, VariableInterpreterState
//
// data class StatefullPrintScriptInterpreterState(
//    val printList: List<String> = listOf(),
//    val variableInterpreterState: VariableInterpreterState
// ) : PrintScriptInterpreterState {
//    override fun addError(error: Error): PrintScriptInterpreterState =
//        this.copy(variableInterpreterState = variableInterpreterState.addError(error))
//
//    override fun initializeVariable(variableInstance: VariableInstance): PrintScriptInterpreterState =
//        this.copy(variableInterpreterState = variableInterpreterState.initializeVariable(variableInstance))
//
//    override fun println(value: PrintlnAstParameter): PrintScriptInterpreterState =
//        TODO("Do")
//
//        when (value) {
//            is NumberLiteral<*> -> this.copy(printList = printList + value.value().toString())
//            is StringLiteral -> this.copy(printList = printList + value.value)
//            is VariableNameNode ->
//                variableStringMap[value.value()]
//                    ?.let { this.copy(printList = printList + value.value()) }
//                    ?: variableIntegerMap[value.value()]
//                        ?.let { this.copy(printList = printList + value.value()) }
//                    ?: this.addError(VariableIsNotDefined())
//            is Literal -> this.addError(TypeNotSupportedInPrintError())
//            else -> this.addError(CannotPrintValueError())
//        }
//    override fun setValueToVariable(
//        key: VariableNameNode,
//        value: AssignationParameterNode<*>
//    ): PrintScriptInterpreterState =
//        when (value) {
//            is IntNumberLiteral,
//            is DoubleNumberLiteral,
//            is StringLiteral -> this.copy(variableInterpreterState = variableInterpreterState.setLiteralToVariable(key, value as Literal))
//            is VariableNameNode -> this.copy(
//                variableInterpreterState = variableInterpreterState.setVariableValueToVariable(
//                    key,
//                    value
//                )
//            )
//            else -> this.addError(NotAcceptableAssignationValueError())
//        }
// }
