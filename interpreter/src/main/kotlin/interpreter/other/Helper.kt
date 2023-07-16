package interpreter.other

import ast.ConcatenationParameter
import ast.DoubleNumberLiteral
import ast.IntNumberLiteral
import ast.Operation
import ast.OperationParameter
import ast.StringConcatenation
import ast.Sum
import ast.VariableNameNode

class Helper {
    fun fromOperationToStringConcat(operation: Operation): StringConcatenation? =
        operationParamToStringConcat(operation)?.let { StringConcatenation(it) }

    fun operationParamToStringConcat(operationParameter: OperationParameter): List<ConcatenationParameter>? =
        when (operationParameter) {
            is DoubleNumberLiteral -> listOf(operationParameter)
            is IntNumberLiteral -> listOf(operationParameter)
            is VariableNameNode -> listOf(operationParameter)
            is Operation -> {
                when (operationParameter.operation) {
                    is Sum -> operationParamToStringConcat(operationParameter.left)?.let { left ->
                        operationParamToStringConcat(operationParameter.right)?.let { right ->
                            left + right
                        }
                    }
                    else -> null
                }
            }
        }
}
