package app.literalInputter

class CliLiteralInputter(private var list: List<String>) : LiteralInputter {
    override fun input(): String? {
        val nextString = list.firstOrNull()
        list = list.drop(1)
        return nextString
    }
}
