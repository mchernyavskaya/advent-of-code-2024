package org.example

import org.checkerframework.common.returnsreceiver.qual.This
import java.io.File
import kotlin.math.absoluteValue

class DayThree(private val input: String) {
    private val rx1 = "mul\\((\\d+),(\\d+)\\)".toRegex()
    private val rx2 = "mul\\((\\d+),(\\d+)\\)|don't\\(\\)|do\\(\\)".toRegex()
    private val ACTION_DO = "do()"
    private val ACTION_DONT = "don't()"

    private fun parse1(): List<Pair<Int, Int>> {
        return rx1.findAll(input).map {
            val groupValues = it.groupValues
            Pair(groupValues[1].toInt(), groupValues[2].toInt())
        }.toList()
    }

    private fun parse2(): List<Pair<Int, Int>> {
        var doAdd = true
        val result = mutableListOf<Pair<Int, Int>>()
        rx2.findAll(input).forEach { match ->
            val groupValues = match.groupValues
            if (groupValues[0] == ACTION_DO) {
                doAdd = true
            } else if (groupValues[0] == ACTION_DONT) {
                doAdd = false
            } else if (doAdd) {
                result.add(Pair(groupValues[1].toInt(), groupValues[2].toInt()))
            }
        }
        return result
    }

    fun solution(): Int {
        return parse2().sumOf { it.first * it.second }
    }
}

fun main() {

    val input = File("src/main/resources/day-three.txt")
        .readLines().joinToString().trim()
    val solution = DayThree(input)
    println("Solution for day 3: ${solution.solution()}")
}