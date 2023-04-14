
interface CodeStructure {
    fun getString(): String
}

class Sentence(private val value: String, val line: Int) : CodeStructure {

    override fun getString(): String =
        value
}
