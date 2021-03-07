package ktn.jnt

// GNU gettext for Windows: https://mlocati.github.io/articles/gettext-iconv-windows.html
// C tutorial: https://www.labri.fr/perso/fleury/posts/programming/a-quick-gettext-tutorial.html
// LANG=ru_RU.UTF-8 ./JapaneseNumeralTranslator.kexe
// LANG=ru_RU.UTF-8 ./JapaneseNumeralTranslator.kexe 120
// LANG=en_US.UTF-8 ./JapaneseNumeralTranslator.kexe
// LANG=en_US.UTF-8 ./JapaneseNumeralTranslator.kexe 120

enum class State {
    EmptyInput,
    InvalidInput,
    ValidInput,
    ExitCommand,
}

data class ParseResult(val input: String?, val state: State)

fun parseInput(input: String?): ParseResult {
    if (input == null) {
        // 64-bit natural number or command input requirement string
        println(tr("please enter 64-bit natural number or command!"))
        return ParseResult(null, State.EmptyInput)
    }
    if (input.toLowerCase() == "exit" || input.toLowerCase() == "quit") {
        return ParseResult(null, State.ExitCommand)
    }
    if (!CurrentLocale.isInt(input)) {
        // invalid input (not a natural number or command) string
        println(tr("invalid input ")
                + "'$input'!")
        return ParseResult(null, State.InvalidInput)
    }

    return ParseResult(input, State.ValidInput)
}

fun printCardinal(input: String) {
    val translation = JapanNumeral.cardinal(input).joinToString("")
    // Japanese cardinal translation string
    println(tr("Japanese cardinal for ")
            + "'$input': '$translation'")
}

fun main(args: Array<String>) {
    if (!CurrentLocale.isTranslated()) {
        println("Sorry, translation for locale '${CurrentLocale.name()}' is not available yet!")
        println("Please email to author if you can help with it.")
        println("Falling back to 'en_US.UTF-8'.")
        CurrentLocale.fallback()
    }

    CurrentLocale.initLocale()
    val ts = CurrentLocale.thousandsSeparator()

    // Program hello string
    println(tr("Kotlin/Native sample program: translate given number to Japanese cardinal numeral."))
    // Current locale name string
    println(tr("Your current locale is ")
            + "'${CurrentLocale.name()}'.")
    // Current locale properties string
    println(tr("Your thousand separator is ")
            + "'${ts}'"
            // UTF-8 codes string
            + tr(", UTF-8 codes: ")
            + ts.getUtf8Codes()
            // UTF-8 bytes string
            + tr(", UTF-8 bytes: ")
            + ts.getUtf8Bytes()
    )

    // Command-line argument mode
    if (args.size == 1) {
        // NOTE: https://kotlinlang.org/docs/destructuring-declarations.html
        val (input, state) = parseInput(args[0])
        when (state) {
            State.ValidInput -> { printCardinal(input!!); return }
            else -> return
        }
    }

    // REPL mode enter string
    println(tr("No command-line argument found, so enter REPL mode."))
    // Exit command help string
    println(tr("Type exit or quit command to leave this program."))
    while (true) {
        // 64-bit natural number or command input request string
        println(tr("Please enter 64-bit integer number or command: "))

        val (input, state) = parseInput(readLine())
        when (state) {
            State.EmptyInput, State.InvalidInput -> continue
            State.ValidInput -> printCardinal(input!!)
            State.ExitCommand -> break
        }
    }
}
