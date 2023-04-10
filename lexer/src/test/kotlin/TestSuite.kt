import org.junit.jupiter.api.Assertions.assertNull
import java.io.File
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

interface ExpectedValuesBuilder<T> {
    fun build(s: String): List<T>
}
interface TestFolderPathBuilder {
    fun build(): List<String>
}
interface TesterBuilder<T, U : Tester<T, *, *>> {
    fun build(expectedValues: List<T>): U
}
class ListTester<T, U : Tester<T, *, *>> (
    private val testerBuilder: TesterBuilder<T, U>,
    private val expectedValuesBuilder: ExpectedValuesBuilder<T>,
    private val testFolderPathBuilder: TestFolderPathBuilder,
    private val resourcesPath: String,
    private val resultFile: String,
    private val inputPath: String
) {
    fun test() {
        testFolderPathBuilder.build().forEach { number ->
            val line = File("$resourcesPath$number/$inputPath").useLines { it.firstOrNull() }
            line?.let {
                testerBuilder
                    .build(expectedValuesBuilder.build(it))
                    .test("$resourcesPath$number/$resultFile")
            }
        }
    }
}
