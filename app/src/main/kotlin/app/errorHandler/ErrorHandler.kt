package app.errorHandler

interface ErrorHandler<T> {
    fun handle(message: String, state: T): T
}
