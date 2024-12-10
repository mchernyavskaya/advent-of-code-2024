package org.example

import java.io.File


class DaySix(private val inputRows: List<CharArray>) {
    data class CharPoint(val x: Int, val y: Int, val c: Char) {
        fun isObstacle(): Boolean = c == '#'
    }
    enum class Direction(val char: Char) { U('^'), D('v'), L('<'), R('>') }
    private val size = inputRows.size

    private var pos: CharPoint = initPos()
    private var done: Boolean = false

    private fun initPos(): CharPoint {
        for (i in 0..<size) {
            for (j in 0..<size) {
                if ("[\\^v<>]".toRegex().matches(inputRows[i][j].toString())) {
                    return CharPoint(i, j, inputRows[i][j])
                }
            }
        }
        throw IllegalArgumentException("No current position!")
    }

    private fun peekForward(): CharPoint? {
        val p = when (pos.c) {
            Direction.U.char -> Pair(pos.x - 1, pos.y)
            Direction.D.char -> Pair(pos.x + 1, pos.y)
            Direction.R.char -> Pair(pos.x, pos.y + 1)
            Direction.L.char -> Pair(pos.x, pos.y - 1)
            else -> throw IllegalArgumentException("Position should be ^v<>!")
        }
        if (isValidPoint(p.first, p.second)) {
            return CharPoint(p.first, p.second, inputRows[p.first][p.second])
        }
        return null
    }

    private fun turnRight() {
        val nextDirection = when (pos.c) {
            Direction.U.char -> Direction.R
            Direction.D.char -> Direction.L
            Direction.R.char -> Direction.D
            Direction.L.char -> Direction.U
            else -> throw IllegalArgumentException("Position should be ^v<>!")
        }
        pos = CharPoint(pos.x, pos.y, nextDirection.char)
    }

    private fun markVisited(x: Int, y: Int) {
        inputRows[x][y] = 'X'
    }

    private fun moveTo(p: CharPoint) {
        val previousPos = pos
        pos = CharPoint(p.x, p.y, previousPos.c)
        markVisited(previousPos.x, previousPos.y)
    }

    private fun move() {
        var candidate: CharPoint? = null
        var turns = 0
        do {
            candidate = peekForward()
            if (candidate == null) {
                done = true
            } else {
                if (!candidate.isObstacle()) {
                    moveTo(candidate)
                    turns = 0
                } else {
                    // we need to turn right but no more than 3 times, or we are stuck
                    if (turns < 3) {
                        turnRight()
                        turns++
                    } else {
                        done = true
                    }
                }
            }
        } while (!done)
        markVisited(pos.x, pos.y)
    }

    private fun isValidPoint(x: Int, y: Int): Boolean {
        return (x >= 0) && (x < size) && (y >= 0) && (y < size)
    }

    fun solution(): Int {
        move()
        return inputRows.sumOf { r -> r.filter { it == 'X' }.size }
    }

}

fun main() {

    val input = File("src/main/resources/day-six.txt")
        .readLines().map { it.toCharArray() }
    val solution = DaySix(input)
    println("Solution for day 6: ${solution.solution()}")
}