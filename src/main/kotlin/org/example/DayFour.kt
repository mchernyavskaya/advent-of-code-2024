package org.example

import java.io.File

data class IndexedChar(val x: Int, val y: Int, val c: Char)
enum class Direction { N, S, W, E, NW, NE, SW, SE }

class DayFour(private val inputRows: List<String>) {
    private val word = "XMAS"
    private val size = inputRows.size

    private fun wordsFrom(ic: IndexedChar, w: String, acc: ArrayList<IndexedChar>, d: Direction): Int {
        if (!w.startsWith(ic.c)) {
            return 0
        } else {
            acc.add(ic)
        }
        if (w.length == 1) {
            println(acc)
            return 1
        }
        val p = when (d) {
            Direction.S -> Pair(ic.x + 1, ic.y)
            Direction.SW -> Pair(ic.x + 1, ic.y - 1)
            Direction.SE -> Pair(ic.x + 1, ic.y + 1)
            Direction.N -> Pair(ic.x - 1, ic.y)
            Direction.NE -> Pair(ic.x - 1, ic.y + 1)
            Direction.NW -> Pair(ic.x - 1, ic.y - 1)
            Direction.E -> Pair(ic.x, ic.y + 1)
            Direction.W -> Pair(ic.x, ic.y - 1)
        }
        if (!isValidPoint(p.first, p.second)) {
            return 0
        }
        return wordsFrom(
            IndexedChar(p.first, p.second, inputRows[p.first][p.second]),
            w.substring(1),
            ArrayList(acc),
            d
        )
    }

    private fun wordsFrom(x: Int, y: Int): Int {
        var result = 0
        Direction.entries.forEach {
            result += wordsFrom(
                IndexedChar(x, y, inputRows[x][y]),
                word,
                arrayListOf(),
                it
            )
        }
        return result
    }

    private fun isValidPoint(x: Int, y: Int): Boolean {
        return (x >= 0) && (x < size) && (y >= 0) && (y < size)
    }

    fun solution(): Int {
        var result = 0
        for (i in 0..<size) {
            for (j in 0..<size) {
                result += wordsFrom(i, j)
            }
        }
        return result
    }

}

fun main() {

    val input = File("src/main/resources/day-four.txt")
        .readLines()
    val solution = DayFour(input)
    println("Solution for day 4: ${solution.solution()}")
}