import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import java.io.FileInputStream
import java.io.InputStream
interface Transformer<U : TestValue<*>> {
    fun to(from: String): U
}
interface CsvReader<T : TestValue<*>, U : Transformer<T>> {
    fun read(inputStream: InputStream): List<T> {
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { transformer().to(it) }
            .toList()
    }

    fun transformer(): U
}
interface TestValue<T> {
    fun hasError(comparedTo: T): String?
    fun errorMessage(expected: String, actual: String): String =
        "Expected $expected != $actual"
}
class Tester<T, U : TestValue<T>, V : Transformer<U>> (private val csvReader: CsvReader<U, V>, private val expectedValues: List<T>) {
    fun test(path: String) {
        val inputStream = FileInputStream(path)
        val readCsv = csvReader.read(inputStream)
        inputStream.close()
        assertEquals(readCsv.size, expectedValues.size)
        readCsv.zip(expectedValues).forEach {
                (actual, expected) ->
            assertNotNull(actual.hasError(expected))
        }
    }
}

class ListTest(private val tester: Tester<*, *, *>, private val paths: List<String>) {
    fun test() =
        paths.forEach { tester.test(it) }
}
