package interpret

import app.errorHandler.MyErrorHandler
import app.interpreter.Interpret
import app.interpreter.PrintScriptInterpetI
import app.printer.interpret.PrintScriptInterpretStatesPrinter
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class P {

    @Test
    fun test() {
//        val s = """
//            let pi: number;
//            pi = 3.14;
//            println(pi / 2);
//                """
        val s = """let numberResult: number = 5 * 5 - 8;
println(numberResult);"""

        Interpret(
            PrintScriptInterpretStatesPrinter(),
            PrintScriptInterpetI("", MyErrorHandler())
        ).interpret(
            ByteArrayInputStream(
                s.trimIndent().toByteArray()
            )
        )
    }
}
