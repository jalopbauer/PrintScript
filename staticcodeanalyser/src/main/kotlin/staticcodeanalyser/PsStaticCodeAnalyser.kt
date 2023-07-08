package staticcodeanalyser

import token.ElseToken
import token.IfToken
import token.SemicolonToken
import token.Token

class PsStaticCodeAnalyser(private val printScriptStaticCodeAnalyser: PrintScriptStaticCodeAnalyser) : StaticCodeAnalyser<StaticCodeAnalyserResponse> {
    override fun format(listOfTokens: List<Token>): StaticCodeAnalyserResponse =
        when {
            listOfTokens.firstOrNull() !is IfToken &&
                listOfTokens.firstOrNull() !is ElseToken &&
                listOfTokens.lastOrNull() is SemicolonToken -> {
                printScriptStaticCodeAnalyser.format(listOfTokens)
                    ?.let { Error(it) }
                    ?: Valid
            }
            else -> Continue(listOfTokens)
        }
}

sealed interface StaticCodeAnalyserResponse {
    fun tokens(): List<Token>
}

object Valid : StaticCodeAnalyserResponse {
    override fun tokens(): List<Token> = listOf()
}

data class Error(val message: String) : StaticCodeAnalyserResponse {
    override fun tokens(): List<Token> = listOf()
}

data class Continue(val listOfTokens: List<Token>) : StaticCodeAnalyserResponse {
    override fun tokens(): List<Token> = listOfTokens
}
