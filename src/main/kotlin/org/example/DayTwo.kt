package org.example

import java.io.File
import kotlin.math.absoluteValue

class DayTwo(private val levels: List<List<Int>>) {
    private fun isSafe(level: List<Int>): Pair<Boolean, Int> {
        var increasing: Boolean? = null
        for (i in 1..<level.size) {
            val current = level[i]
            val previous = level[i - 1]
            if (current == previous) {
                return Pair(false, i)
            }
            if (increasing == null) {
                increasing = current > previous
            } else {
                if (increasing != (current > previous)) {
                    return Pair(false, i)
                }
            }
            val diff = (current - previous).absoluteValue
            if (diff == 0 || diff > 3) {
                return Pair(false, i)
            }
        }
        return Pair(true, -1)
    }

    private fun isSafeDampened(level: List<Int>): Pair<Boolean, Int> {
        level.indices.map {
            level.filterIndexed({ i, _ -> i != it })
        }.forEach {
            val safe = isSafe(it)
            if (safe.first) {
                return safe
            }
        }
        return Pair(false, -1)
    }

    fun safeLevels(): Int {
        return levels.map { isSafeDampened(it) }.filter { p -> p.first }.size
    }
}

fun main() {
    val levels = File("src/main/resources/day-two.txt")
        .readLines()
        .map {
            it.split("\\s+".toRegex()).map { s -> s.toInt() }
        }
    val solution = DayTwo(levels)
    println("Safe levels: ${solution.safeLevels()}")
}