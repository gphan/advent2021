import java.io.File

fun parseFile(fileName: String): List<Pair<Int, Int>> =
    File(fileName).readLines()
        .map { line ->
            val parts = line.split(" ")
            val op = parts.first()

            if (op == "forward") {
                parts[1].toInt() to 0
            } else {
                if (op == "up") {
                    0 to -parts[1].toInt()
                } else {
                    0 to parts[1].toInt()
                }
            }
        }

fun handleInput(fileName: String): Int =
    parseFile(fileName)
        .reduce { acc, pair -> acc.first + pair.first to acc.second + pair.second }
        .let { it.first * it.second }

fun handleAim(fileName: String): Int {
    var aim = 0
    var horiz = 0
    var depth = 0

    parseFile(fileName)
        .forEach { op ->
            if (op.first > 0) {
                horiz += op.first
                depth += op.first * aim
            }

            if (op.second != 0) {
                aim += op.second
            }
        }

    return horiz * depth
}

fun main() {
//    println(handleInput("input_submarine.txt"))
    println(handleAim("input_submarine.txt"))
}