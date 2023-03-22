interface SentenceIdentifierMapper {
    fun getSentenceIdentifier(sentenceType: SentenceType): SentenceIdentifier
}

class PrintScriptIdentifierMapper: SentenceIdentifierMapper {
    override fun getSentenceIdentifier(sentenceType: SentenceType): SentenceIdentifier {
        when (sentenceType) {
            AssignationDeclarationType -> TODO()
            AssignationType -> TODO()
            DeclarationType -> TODO()
            PrintlnType -> TODO()
        }
    }
}