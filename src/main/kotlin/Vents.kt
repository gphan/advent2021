import java.io.File
import kotlin.math.abs
import kotlin.math.max

data class Point(
    val x: Int,
    val y: Int
)

data class Line(
    val src: Point,
    val dest: Point
)

fun main() {
    val lineDefs = File("vents.txt").readLines()
        .map { line ->
            val parts = line
                .split(" -> ")

            val src = parts.first()
                .split(",")
                .let { Point(it[0].toInt(), it[1].toInt()) }

            val dest = parts.last()
                .split(",")
                .let { Point(it[0].toInt(), it[1].toInt()) }

            Line(src, dest)
        }

    // Determine max x and y
    val maxX = lineDefs.maxOf { max(it.src.x, it.dest.x) } + 1
    val maxY = lineDefs.maxOf { max(it.src.y, it.dest.y) } + 1

    val grid = MutableList(maxX) { MutableList(maxY) { 0 } }

    // Draw the lines on the grid by increment each cell as we go
    lineDefs.forEach { line ->

        // Figure out direction
        val dx: Int = if (line.src.x != line.dest.x)
            (line.dest.x - line.src.x) / abs(line.dest.x - line.src.x)
        else 0

        val dy: Int = if (line.src.y != line.dest.y)
            (line.dest.y - line.src.y) / abs(line.dest.y - line.src.y)
        else 0

        var x = line.src.x
        var y = line.src.y

        while (true) {
            grid[x][y] = grid[x][y] + 1
            x += dx
            y += dy

            if (x == line.dest.x && y == line.dest.y) {
                grid[x][y] = grid[x][y] + 1
                break
            }
        }
    }

    val overlaps = grid.flatten().count { it > 1 }

    println(overlaps)
}