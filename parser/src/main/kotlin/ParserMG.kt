import token.Token

interface ParserMG<T> {
    fun parse(tokens: List<Token>): AbstractSyntaxTree<Token>
}

class ParserImplMG(private val sentenceValidator: SentenceValidator) : ParserMG<AbstractSyntaxTree<Token>> {

    override fun parse(tokens: List<Token>): AbstractSyntaxTree<Token> {
        val checker = SentenceCheckerImpl()
        // fijarse de que esta compuesta la lista de tokens y conseguir el tipo
        val type = checker.check(tokens)
        // armar el arbol con ese tipo
        val maker = TreeMakerImpl()
        return maker.build(type, tokens)
    }
}
