package lexer
interface SentenceSplitterInterface {
    fun split(input: Sentence): List<String>
}

class SentenceSplitter : SentenceSplitterInterface {
    override fun split(input: Sentence): List<String> =
        input.getString().split(Regex(" |(?<=[:()])|(?=[:();])"))
}
