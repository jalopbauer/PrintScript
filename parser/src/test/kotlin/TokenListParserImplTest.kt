import ast.AssignationAst
import ast.AssignationDeclarationAst
import ast.DeclarationAst
import ast.IfStatement
import ast.NumberType
import ast.Operation
import ast.PrintlnAst
import ast.StringConcatenation
import ast.StringLiteral
import ast.StringType
import org.junit.jupiter.api.Test
import parser.PrintScriptAstParser
import parser.parserState.RegularParserState
import parser.parserState.RegularParserStateParser
import token.AssignationToken
import token.BooleanTypeToken
import token.ConstToken
import token.DeclarationToken
import token.DivToken
import token.DoubleNumberLiteralToken
import token.ElseToken
import token.FalseLiteralToken
import token.IfToken
import token.IntNumberLiteralToken
import token.LeftCurlyBracketsToken
import token.LeftParenthesisToken
import token.LetToken
import token.NumberTypeToken
import token.PrintlnToken
import token.RightCurlyBracketsToken
import token.RightParenthesisToken
import token.SemicolonToken
import token.StringLiteralToken
import token.StringTypeToken
import token.SumToken
import token.Token
import token.TrueLiteralToken
import token.VariableNameToken

class TokenListParserImplTest {

    @Test
    fun declarationParserWorksWithNumber() {
        val declaration: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declarationParser = PrintScriptAstParser()
        val declarationAst = declarationParser.parse(declaration)

        when (declarationAst) {
            is DeclarationAst -> assert(
                declarationAst.rightValue() is NumberType &&
                    declarationAst.leftValue().value() == "test"
            )
            else -> assert(false)
        }
    }

    @Test
    fun declarationParserDoesntWork() {
        val declaration: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declarationParser = PrintScriptAstParser()
        val declarationAst = declarationParser.parse(declaration)

        assert(declarationAst !is DeclarationAst)
    }

    @Test
    fun declarationParserWorksWithString() {
        val declaration: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declarationParser = PrintScriptAstParser()
        val declarationAst = declarationParser.parse(declaration)

        when (declarationAst) {
            is DeclarationAst -> assert(
                declarationAst.rightValue() is StringType &&
                    declarationAst.leftValue().value() == "test"
            )
            else -> assert(false)
        }
    }

    @Test
    fun assignationParserWorks() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        when (assignationAst) {
            is AssignationAst -> assert(
                assignationAst.leftValue().value() == "test" &&
                    ((assignationAst.rightValue() as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value.equals("Hello World")
            )
            else -> assert(false)
        }
    }

    @Test
    fun assignationWorksWithConcat() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("World", 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        when (assignationAst) {
            is AssignationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun assignationWorksWithOperation() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun assignationWorksWithVariable() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            VariableNameToken("1", 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun assignationWorksWithComplexOperation() {
        val assignation: List<Token> = listOf(
            VariableNameToken("test", 0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            DivToken(0, 0),
            IntNumberLiteralToken(3, 0, 0),
            SemicolonToken(0, 0)
        )
        val assignationParser = PrintScriptAstParser()
        val assignationAst = assignationParser.parse(assignation)

        assert(assignationAst is AssignationAst)
    }

    @Test
    fun declAssiParserWorks() {
        val declAssi: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiParser = PrintScriptAstParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun declAssiWorksWithOperation() {
        val declAssi: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            DivToken(0, 0),
            IntNumberLiteralToken(3, 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiParser = PrintScriptAstParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun declAssiWorksWithAllTypes() {
        val declAssiParser = PrintScriptAstParser()

        val declAssiString: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiAstString = declAssiParser.parse(declAssiString)

        val declAssiNumber: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiAstNumber = declAssiParser.parse(declAssiNumber)

        val declAssiBool: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            BooleanTypeToken(0, 0),
            AssignationToken(0, 0),
            TrueLiteralToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiAstBool = declAssiParser.parse(declAssiBool)

        when (declAssiAstString) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
        when (declAssiAstNumber) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }

        println(declAssiAstBool)
    }

    @Test
    fun printlnAstAcceptsOperation() {
        val declaration: List<Token> = listOf(
            PrintlnToken(2, position = 0),
            LeftParenthesisToken(lineNumber = 2, position = 7),
            VariableNameToken(value = "pi", lineNumber = 2, position = 8),
            DivToken(lineNumber = 2, position = 11),
            IntNumberLiteralToken(value = 2, lineNumber = 2, position = 13),
            RightParenthesisToken(lineNumber = 2, position = 14),
            SemicolonToken(lineNumber = 2, position = 15)
        )
        val numberPrint: List<Token> = listOf(
            PrintlnToken(2, position = 0),
            LeftParenthesisToken(lineNumber = 2, position = 7),
            DoubleNumberLiteralToken(value = 1.0, lineNumber = 2, position = 8),
            RightParenthesisToken(lineNumber = 2, position = 14),
            SemicolonToken(lineNumber = 2, position = 15)
        )
        val declarationParser = PrintScriptAstParser()
        val printlnAst = declarationParser.parse(declaration)
        val doubleAst = declarationParser.parse(numberPrint)

        println(doubleAst)

        assert(printlnAst is PrintlnAst && printlnAst.value() is Operation)
    }

    @Test
    fun printlnWorksWithBoolean() {
        val printT: List<Token> = listOf(
            PrintlnToken(2, position = 0),
            LeftParenthesisToken(lineNumber = 2, position = 7),
            TrueLiteralToken(lineNumber = 2, position = 8),
            RightParenthesisToken(lineNumber = 2, position = 14),
            SemicolonToken(lineNumber = 2, position = 15)
        )
        val printF: List<Token> = listOf(
            PrintlnToken(2, position = 0),
            LeftParenthesisToken(lineNumber = 2, position = 7),
            TrueLiteralToken(lineNumber = 2, position = 8),
            RightParenthesisToken(lineNumber = 2, position = 14),
            SemicolonToken(lineNumber = 2, position = 15)
        )
        val parser = PrintScriptAstParser()
        val trueAst = parser.parse(printT)
        val falseAst = parser.parse(printF)
        assert(trueAst is PrintlnAst && falseAst is PrintlnAst)
    }

    @Test
    fun declAssiWithWrongAssignation() {
        val declAssi: List<Token> = listOf(
            LetToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SumToken(0, 0),
            IntNumberLiteralToken(2, 0, 0),
            DivToken(0, 0),
            IntNumberLiteralToken(3, 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiParser = PrintScriptAstParser()
        val declAssiAst = declAssiParser.parse(declAssi)

        when (declAssiAst) {
            is AssignationDeclarationAst -> assert(false)
            else -> assert(true)
        }
    }

    @Test
    fun printParserWorks() {
        val print: List<Token> = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val printParser = PrintScriptAstParser()
        val printAst = printParser.parse(print)

        when (printAst) {
            is PrintlnAst -> assert((((printAst.value())as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value == "Hello World")
            else -> assert(false)
        }
    }

    @Test
    fun printWorksWithConcat() {
        val print: List<Token> = listOf(
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            SumToken(0, 0),
            StringLiteralToken("World", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0)
        )
        val printParser = PrintScriptAstParser()
        val printAst = printParser.parse(print)

        when (printAst) {
            is PrintlnAst -> assert(
                (((printAst.value())as StringConcatenation).concatenationParameterValues[0] as StringLiteral).value == "Hello" &&
                    (((printAst.value())as StringConcatenation).concatenationParameterValues[1] as StringLiteral).value == "World"
            )
            else -> assert(false)
        }
    }

    @Test
    fun parserWorksWithIf() {
        val ifTokens: List<Token> = listOf(
            IfToken(0, 0),
            LeftParenthesisToken(0, 0),
            TrueLiteralToken(0, 0),
            RightParenthesisToken(0, 0),
            LeftCurlyBracketsToken(0, 0),
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0),
            RightCurlyBracketsToken(0, 0)
        )
        val elseTokens: List<Token> = listOf(
            ElseToken(0, 0),
            LeftCurlyBracketsToken(0, 0),
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0),
            RightCurlyBracketsToken(0, 0)
        )
        val parser = PrintScriptAstParser()
        val treeIf = parser.parse(ifTokens)
        val treeElse = parser.parse(elseTokens)
        println(treeElse)
        assert(treeIf is IfStatement)
        // assert(treeElse is ElseStatement)
    }

    @Test
    fun parserWorksWithIfFalse() {
        val ifTokens: List<Token> = listOf(
            IfToken(0, 0),
            LeftParenthesisToken(0, 0),
            FalseLiteralToken(0, 0),
            RightParenthesisToken(0, 0),
            LeftCurlyBracketsToken(0, 0),
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0),
            RightCurlyBracketsToken(0, 0)
        )
        val elseTokens: List<Token> = listOf(
            ElseToken(0, 0),
            LeftCurlyBracketsToken(0, 0),
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0),
            RightCurlyBracketsToken(0, 0)
        )
        val parser = PrintScriptAstParser()
        val treeIf = parser.parse(ifTokens)
        val treeElse = parser.parse(elseTokens)
        println(treeElse)
        assert(treeIf is IfStatement)
        // assert(treeElse is ElseStatement)
    }

    @Test
    fun parserWorksWithIfVariableNameNome() {
        val ifTokens: List<Token> = listOf(
            IfToken(0, 0),
            LeftParenthesisToken(0, 0),
            VariableNameToken("", 0, 0),
            RightParenthesisToken(0, 0),
            LeftCurlyBracketsToken(0, 0),
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0),
            RightCurlyBracketsToken(0, 0)
        )
        val elseTokens: List<Token> = listOf(
            ElseToken(0, 0),
            LeftCurlyBracketsToken(0, 0),
            PrintlnToken(0, 0),
            LeftParenthesisToken(0, 0),
            StringLiteralToken("Hello", 0, 0),
            RightParenthesisToken(0, 0),
            SemicolonToken(0, 0),
            RightCurlyBracketsToken(0, 0)
        )
        val parser = PrintScriptAstParser()
        val treeIf = parser.parse(ifTokens)
        val treeElse = parser.parse(elseTokens)
        println(treeElse)
        assert(treeIf is IfStatement)
        // assert(treeElse is ElseStatement)
    }

    @Test
    fun parserElseWorks() {
        assert(true)
        // val elseBuilder  = ElseStatementBuilder(SentenceBuilder())
        // val elseTokenParser = ElseTokenListParser()
        // val ifStatement = IfStatement()
        // val regularParserState = RegularParserState()
        // val ifParserState = IfParserState(ifStatement, regularParserState)
        // val ifParserStateParser = IfParserStateParser()
        // ifParserStateParser.parse(ifParserState)
    }

    @Test
    fun parserWorksWithConst() {
        val declAssiParser = PrintScriptAstParser()

        val declAssiString: List<Token> = listOf(
            ConstToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            StringTypeToken(0, 0),
            AssignationToken(0, 0),
            StringLiteralToken("Hello World", 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiAstString = declAssiParser.parse(declAssiString)

        val declAssiNumber: List<Token> = listOf(
            ConstToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            NumberTypeToken(0, 0),
            AssignationToken(0, 0),
            IntNumberLiteralToken(1, 0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiAstNumber = declAssiParser.parse(declAssiNumber)

        val declAssiBool: List<Token> = listOf(
            ConstToken(0, 0),
            VariableNameToken("test", 0, 0),
            DeclarationToken(0, 0),
            BooleanTypeToken(0, 0),
            AssignationToken(0, 0),
            TrueLiteralToken(0, 0),
            SemicolonToken(0, 0)
        )
        val declAssiAstBool = declAssiParser.parse(declAssiBool)

        println(declAssiAstNumber)
        println(declAssiAstBool)

        when (declAssiAstString) {
            is AssignationDeclarationAst -> assert(true)
            else -> assert(false)
        }
    }

    @Test
    fun parserStateWorks() {
        var regularParserState = RegularParserState()
        regularParserState = regularParserState.addToken(PrintlnToken(0, 0))
        regularParserState = regularParserState.addToken(LeftParenthesisToken(0, 0))
        regularParserState = regularParserState.addToken(StringLiteralToken("Hello", 0, 0))
        regularParserState = regularParserState.addToken(RightParenthesisToken(0, 0))
        regularParserState = regularParserState.addToken(SemicolonToken(0, 0))
        val tokenList = regularParserState.tokens()
        val first = regularParserState.firstToken()
        assert(tokenList.size == 5)
        assert(first == (PrintlnToken(0, 0)))
        val parserState = RegularParserStateParser()
        parserState.parse(regularParserState)
    }

    @Test
    fun parserResponseWorks() {}
}
