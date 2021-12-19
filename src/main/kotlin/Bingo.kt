import java.io.File

data class Board(
    val id: Int,
    val board: MutableList<MutableList<Int>> = MutableList(5) { MutableList(5) { 0 } },
    var won: Boolean = false
) {
    // Iterate through board and mark value using -1
    fun markValue(value: Int) {
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (board[i][j] == value) {
                    board[i][j] = -1
                    return
                }
            }
        }
    }

    fun isWinner(): Boolean {
        // Check rows
        if (board.any { row -> row.all { it == -1 } }) {
            println("Board $id won with row")
            println(board)
            return true
        }

        // Check columns
        for (i in 0 until 5) {
            if (board.all { row -> row[i] == -1 }) {
                println("Board $id won with column")
                println(board)
                return true
            }
        }

        return false
    }

    fun getUnmarked(): List<Int> = board
        .flatten()
        .filter { it != -1 }
}

data class BingoInput(
    val draws: List<Int>,
    val boards: MutableList<Board> = mutableListOf()
)

fun parseInput(fileName: String): BingoInput {
    val lines = File(fileName).readLines()
    val draws = lines.first().split(",").map { it.toInt() }
    val boards = lines.drop(1)

    // Boards are 5x5 and a new line before each board (6 lines total)
    val parsedBoards = (0 until (boards.size / 6)).map { i ->
        // Get board sublist
        Board(
            i,
            boards.subList(i * 6 + 1, i * 6 + 6)
                .map { line ->
                    line
                        .trim()
                        .split(regex = Regex("\\s+"))
                        .map { it.toInt() }
                        .toMutableList()
                }
                .toMutableList())
    }
        .toMutableList()

    return BingoInput(draws, parsedBoards)
}

fun runGame(input: BingoInput): Int {
    var last = -1
    var winner: Board? = null

    for (d in input.draws) {
        for (board in input.boards) {
            if (!board.won) {
                board.markValue(d)

                if (board.isWinner()) {
                    last = d
                    winner = board
                    board.won = true
                }
            }
        }
    }

    if (winner == null) return -1

    return winner.getUnmarked().sum() * last
}

fun main() {
    println(runGame(parseInput("bingo.txt")))
}