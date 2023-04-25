import org.junit.jupiter.api.Test
import token.Token

class ParserImplTest {

    @Test
    fun declarationParserWorks() {
        val declaration: List<Token> = listOf()
        val declarationParser = DeclarationParser()
        val declarationAst =  declarationParser.parse()
    }

    @Test
    fun assignationParserWorks() {
        val assignation: List<Token> = listOf()
        val assignationParser = AssignationParser()
        val assignationAst = assignationParser.parse()
    }

    @Test
    fun declAssiParserWorks() {
        val declAssi: List<Token> = listOf()
        val declAssiParser = DeclarationAssignationParser()
        val declAssiAst = declAssiParser.parse()
    }

    @Test
    fun printParserWorks() {
        val print: List<Token> = listOf()
        val printParser = PrintlnParser()
        val printAst = printParser.parse()
    }
}