import java.io.File

fun convertBinary(str: String): Int {
    var num = 0
    var power = 1

    var idx = str.length - 1

    while (idx >= 0) {
        if (str[idx] == '1') {
            num += power
        }

        power *= 2
        idx--
    }

    return num
}

fun diagnostic(fileName: String): Int {
    val input = File(fileName).readLines()
    val size = input.size
    val len = input.first().length

    val counts = MutableList(len) { 0 }

    input.forEach { line ->
        line.withIndex()
            .forEach {
                if (it.value == '1') {
                    counts[it.index] = counts[it.index] + 1
                }
            }
    }

    val gamma = counts.map {
        if (it > size / 2) {
            '1'
        } else {
            '0'
        }
    }.joinToString("")

    val epsilon = gamma.map {
        if (it == '1') '0'
        else '1'
    }.joinToString("")

    return convertBinary(gamma) * convertBinary(epsilon)
}

fun oxygenRating(lines: List<String>): Int {
    val len = lines.first().length

    var remain = lines

    for (i in 0 until len) {
        val counts = remain
            .map { it[i] }
            .groupBy { it }
            .mapValues { it.value.size }
            .withDefault { 0 }

        var mostCommon = '1'

        if (counts['0']!! > counts['1']!!) {
            mostCommon = '0'
        }

        remain = remain.filter { it[i] == mostCommon }
    }

    if (remain.size > 1) {
        return convertBinary(remain.first { it[len-1] == '1' })
    } else {
        return convertBinary(remain.first())
    }
}

fun co2Rating(lines: List<String>): Int {
    val len = lines.first().length
    var remain = lines

    for (i in 0 until len) {
        val counts = remain
            .map { it[i] }
            .groupBy { it }
            .mapValues { it.value.size }
            .withDefault { 0 }

        var leastCommon = '0'

        if (counts['1']!! < counts['0']!!) {
            leastCommon = '1'
        }

        remain = remain.filter { it[i] == leastCommon }

        if (remain.size == 1) break
    }

    if (remain.size > 1) {
        return convertBinary(remain.first { it[len-1] == '0' })
    } else {
        return convertBinary(remain.first())
    }
}

fun main() {
    val lines = File("input_diag.txt").readLines()
    val oxygen = oxygenRating(lines)
    val co2 = co2Rating(lines)
    println(oxygen)
    println(co2)
    println(oxygen * co2)
}