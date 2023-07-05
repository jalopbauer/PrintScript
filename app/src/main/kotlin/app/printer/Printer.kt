package app.printer

interface Printer<T> {
    fun print(t: T): T
}
