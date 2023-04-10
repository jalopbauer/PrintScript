import org.junit.jupiter.api.Assertions.assertNull
import java.io.FileInputStream
import java.io.InputStream
interface Transformer<U : TestValue<*>> {
    fun to(from: String): U
}
class CsvReader<T : TestValue<*>, U : Transformer<T>> (private val transformer: U) {
    fun read(inputStream: InputStream): List<T> {
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { transformer.to(it) }
            .toList()
    }
}
interface TestValue<T> {
    fun hasError(comparedTo: T): String?
    fun errorMessage(expected: String, actual: String): String =
        "Expected:$expected != Actual:$actual"
}
class Tester<T, U : TestValue<T>, V : Transformer<U>> (private val csvReader: CsvReader<U, V>, private val expectedValues: List<T>) {
    fun test(path: String) {
        val inputStream = FileInputStream(path)
        val readCsv = csvReader.read(inputStream)
        inputStream.close()
        readCsv.zip(expectedValues).forEach {
                (actual, expected) ->
            assertNull(actual.hasError(expected))
        }
    }
}
