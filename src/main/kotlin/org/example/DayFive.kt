package org.example

import java.io.File
import kotlin.math.absoluteValue

data class SortingRule(val before: Int, val after: Int)

class RuleComparator(private val sortingRules: List<SortingRule>) : Comparator<Int> {
    override fun compare(o1: Int?, o2: Int?): Int {
        val beforeRule = sortingRules.find { (it.before == o1 && it.after == o2) }
        if (beforeRule != null) return -100
        val afterRule = sortingRules.find { (it.after == o1 && it.before == o2) }
        if (afterRule != null) return 100
        return 0
    }

}

class DayFive(private val inputRows: List<String>) {
    private val sortingRules = mutableListOf<SortingRule>()
    private val sequences = mutableListOf<List<Int>>()

    private fun parseLines() {
        var startSequences = false
        inputRows.forEach {
            if (it.trim().isBlank()) {
                startSequences = true
            } else {
                if (!startSequences) {
                    val element = it.split("|")
                    sortingRules.add(SortingRule(element[0].toInt(), element[1].toInt()))
                } else {
                    val element = it.split(",".toRegex()).map { el -> el.toInt() }
                    sequences.add(element)
                }
            }
        }
    }

    private fun matches(el: Int, befores: List<Int>, afters: List<Int>): Boolean {
        val ruleAfters = sortingRules.filter { it.before == el }.map { r -> r.after }
        val ruleBefores = sortingRules.filter { it.after == el }.map { r -> r.before }
        return (ruleAfters.intersect(befores.toSet()).isEmpty() &&
                ruleBefores.intersect(afters.toSet()).isEmpty())
    }

    private fun sequenceOrdered(seq: List<Int>): Boolean {
        seq.forEachIndexed { index, v ->
            if (!matches(v, seq.take(index), seq.drop(index + 1))) {
                return false
            }
        }
        return true
    }

    fun solution(): Int {
        var result = 0
        parseLines()
        val comparator = RuleComparator(sortingRules)
        sequences.forEach {
//            if (sequenceOrdered(it)) {
//                println(it.joinToString(","))
//                val idx = (it.size / 2).absoluteValue
//                result += it[idx]
//            }
            if (!sequenceOrdered(it)) {
                println("== ${it.joinToString(",")}")
                val sorted = it.sortedWith(comparator)
                println("** ${sorted.joinToString(",")}")
                val idx = (it.size / 2).absoluteValue
                result += sorted[idx]
            }
        }
        return result
    }

}

fun main() {

    val input = File("src/main/resources/day-five.txt")
        .readLines()
    val solution = DayFive(input)
    println("Solution for day 5: ${solution.solution()}")
}