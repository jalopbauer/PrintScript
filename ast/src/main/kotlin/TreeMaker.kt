import token.Token

interface TreeMaker {
    fun build(type: SentenceType, tokens: List<Token>): AbstractSyntaxTree<Token>
}

class TreeMakerImpl(): TreeMaker {
    override fun build(type: SentenceType, tokens: List<Token>): AbstractSyntaxTree<Token> {
        TODO("Not yet implemented")
    }

}