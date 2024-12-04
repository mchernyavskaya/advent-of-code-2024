package org.example

import java.io.File
import kotlin.math.absoluteValue

class DayOne(private val pairs: List<Pair<Int, Int>>) {
    private val firstCol = pairs.map { it.first }.sorted()
    private val secondCol = pairs.map { it.second }.sorted()

    fun distance(): Int {
        return firstCol.zip(secondCol).sumOf {
            (it.first - it.second).absoluteValue
        }
    }

    fun similarity(): Int {
        return firstCol.sumOf { it * occurrences(it, secondCol) }
    }

    private fun occurrences(value: Int, list: List<Int>): Int {
        var count = 0
        // assumes a sorted list
        for (i in list.indices) {
            if (list[i] == value) {
                count++
            } else if (list[i] > value) {
                break
            }
        }
        return count
    }
}

fun main() {
    val pairs = File("src/main/resources/day-one.txt").readLines()
        .map {
            val split = it.split("\\s+".toRegex())
            Pair(split[0].toInt(), split[1].toInt())
        }
    val solution = DayOne(pairs)
    // println("Total distance: ${solution.distance()}")
    println("Similarity: ${solution.similarity()}")
}