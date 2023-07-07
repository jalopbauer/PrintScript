package interpret

import app.MyPrintScriptApp
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class Formatter {

    @Test
    fun test() {
        MyPrintScriptApp().format(
            ByteArrayInputStream(
                """
            let numberResult: number = 5 * 5 - 8;
            println(numberResult);
                """.trimIndent().toByteArray()
            )
        )
    }
}
