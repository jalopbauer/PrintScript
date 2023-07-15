package app

import app.errorHandler.MyErrorHandler
import app.interpreter.Interpret
import app.interpreter.PrintScriptInterpetI
import app.printer.interpret.PrintScriptInterpretStatesPrinter
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileNotFoundException

/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */

fun main() {
    val interpreter = Interpret(
        PrintScriptInterpretStatesPrinter { string -> println(string) },
        PrintScriptInterpetI("", MyErrorHandler())
    )
    // val formatterStatesPrinter: Printer<PrintScriptFormatterStates>
    // val scaStatesPrinter: Printer<PrintScriptStaticCodeAnalyserStates>
    // val printScriptFormatter = PrintScriptFormatterI()
    // val printScriptSCA = PrintScriptStaticCodeAnalyserI()
    // val printScript = PrintScriptApp(interpreter,formatterStatesPrinter,scaStatesPrinter,printScriptFormatter, printScriptSCA)
    println("Welcome to Printscript 2023")
    selector(interpreter)
}

private fun selector(interpret: Interpret) {
    println("Please select what you want to do:")
    println("1. Run a snippet")
    println("2. Format a snippet")
    println("3. Lint a snippet")
    println("4. Exit")
    val selection = readlnOrNull()
    println("----------------------------------")
    executeSelection(selection, interpret)
}

private fun executeSelection(selection: String?, interpret: Interpret) {
    when (selection) {
        "1", "2", "3" -> selectSnippetLocation(selection, interpret)
        "4" -> greet()
        else -> selector(interpret)
    }
}

private fun selectSnippetLocation(selection: String?, interpret: Interpret) {
    println("The snippet is:")
    println("1. From a file")
    println("2. Inserted manually")
    val file = readlnOrNull()
    println("----------------------------------")
    when (file) {
        "1" -> runFromFile(selection, interpret)
        "2" -> runFromString(selection, interpret)
        else -> selectSnippetLocation(selection, interpret)
    }
}

private fun greet() {
    println("Adios!")
}

private fun runFromFile(selection: String?, interpret: Interpret) {
    println("Please copy the path to your file")
    val location = readlnOrNull()
    val file = File(location)
    try {
        val inputStream = file.inputStream()
        when (selection) {
            "1" -> interpret.interpret(inputStream)
            "2" -> println("printScript.format(inputStream)")
            "3" -> println("printScript.lint(inputStream)")
            else -> selector(interpret)
        }
    } catch (e: FileNotFoundException) {
        println("No existe un archivo en esa posicion, por favor intente de nuevo")
        println("----------------------------------")
        selector(interpret)
    }
    selector(interpret)
}

private fun runFromString(selection: String?, interpret: Interpret) {
    println("Please insert your snippet")
    val snippet = readlnOrNull()
    val inputStream = ByteArrayInputStream(snippet?.toByteArray())
    when (selection) {
        "1" -> interpret.interpret(inputStream)
        "2" -> println("printScript.format(inputStream)")
        "3" -> println("printScript.lint(inputStream)")
        else -> selector(interpret)
    }
    selector(interpret)
}
