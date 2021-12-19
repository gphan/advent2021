import java.io.File

fun countIncrements(measurements: List<Int>): Int {
    return measurements.zip(measurements.drop(1))
        .map { (a, b) -> b - a }
        .count { it > 0 }
}

fun countIncrements(fileName: String): Int {
    val measurements = File(fileName).readLines()
        .map { it.toInt() }

    return countIncrements(measurements)
}

fun countSums(fileName: String): Int {
    val nums = File(fileName).readLines()
        .map { it.toInt() }

    val sums = nums.dropLast(2)
        .zip(nums.drop(1).dropLast(1))
        .map { (a, b) -> a + b }
        .zip(nums.drop(2))
        .map { (a, b) -> a + b }

    return countIncrements(sums)
}

fun main() {
    println(countSums("input.txt"))
}
