package interpret

import app.MyPrintScriptApp
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class Sca {

    @Test
    fun test() {
        MyPrintScriptApp().lint(
            ByteArrayInputStream(
                """
            let numberResult: number = 5 * 5 - 8;
            println("HelloWorld" + "HelloWorld");
                """.trimIndent().toByteArray()
            )
        )
    }
}
