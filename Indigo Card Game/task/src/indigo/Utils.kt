package indigo

fun ask(question: String, filter: (String) -> Boolean = { true }, wrongAnswerPrompt: String = question): String {
    print(question)
    var input = readLine()
    while ((input == null) || !filter(input)) {
        print(wrongAnswerPrompt)
        input = readLine()
    }
    return input
}