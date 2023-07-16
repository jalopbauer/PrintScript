package interpret

import app.errorHandler.MyErrorHandler
import app.interpreter.Interpret
import app.interpreter.PrintScriptInterpetI
import app.literalInputter.CliLiteralInputter
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
        val s = """
            const name: string = readInput("Name:");
            println("Hello " + name + "!");
        """.trimIndent()
        extracted(s)
    }

    private fun extracted(s: String) {
        Interpret(
            PrintScriptInterpretStatesPrinter { string -> println(string) },
            PrintScriptInterpetI("1.1", MyErrorHandler(), CliLiteralInputter(listOf("world")))
        ).interpret(
            ByteArrayInputStream(
                s.trimIndent().toByteArray()
            )
        )
    }
}
