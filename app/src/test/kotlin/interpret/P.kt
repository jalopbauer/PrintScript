package interpret

import app.MyPrintScriptApp
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class P {

    @Test
    fun test() {
        MyPrintScriptApp().interpret(
            ByteArrayInputStream(
                """
            let numberResult: number = 5 * 5 - 8;
            println(numberResult);
                """.trimIndent().toByteArray()
            )
        )
    }
}
