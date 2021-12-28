import java.lang.IllegalStateException

fun main() {
    println(Day5().numLineOverlaps(day5Input))
}

class Day5 {
    fun numLineOverlaps(lines: Set<Line>): Int {
        val onlyHorizontalOrVertical = lines.filter { it.start.x == it.end.x || it.start.y == it.end.y }
        val linesAsMatrixes = onlyHorizontalOrVertical.map { it.discretePoints().map { it.toMatrix() }.reduce { acc, matrix -> acc add matrix } }
        val combinedMatrix = linesAsMatrixes.fold(emptyMatrix(10, 10)) { acc, matrix -> acc add matrix }
        println(combinedMatrix.toPrintableString())
        return combinedMatrix.flatten().filter { it >= 2 }.count()
    }
}

fun emptyMatrix(rows: Int, cols: Int): List<List<Int>> {
    return List(cols) { j -> List(rows) { i -> 0 } }
}

val day5Input = """
    0,9 -> 5,9
    8,0 -> 0,8
    9,4 -> 3,4
    2,2 -> 2,1
    7,0 -> 7,4
    6,4 -> 2,0
    0,9 -> 2,9
    3,4 -> 1,4
    0,0 -> 8,8
    5,5 -> 8,2
""".trimIndent().split("\n").map { it.split(" -> ").map { it.split(",") }.map { Coordinates(it[0].toInt(), it[1].toInt()) } }.map { Line(it[0], it[1]) }.toSet()

data class Line(val start: Coordinates, val end: Coordinates) {
    fun discretePoints(): List<Coordinates> {
        return if (start.x == end.x) {
            val range = if (start.y < end.y) start.y..end.y else end.y..start.y
            range.toList().map { Coordinates(x = start.x, y = it) }
        } else if (start.y == end.y) {
            val range = if (start.x < end.x) start.x..end.x else end.x..start.x
            range.toList().map { Coordinates(x = it, y = start.y) }
        } else {
            throw IllegalStateException("Should always be horizontal or vertical lines, but was start:$start, end:$end")
        }
    }
}

infix fun List<List<Int>>.add(other: List<List<Int>>): List<List<Int>> {
    val cols = 10
    val rows = 10

    return List(cols) { j ->
        List(rows) { i ->
            this[j][i] + other[j][i]
        }
    }
}

fun Coordinates.toMatrix(): List<List<Int>> {
    val cols = 10
    val rows = 10
    return List(cols) { j ->
        List(rows) { i ->
            if (j == y && i == x) 1 else 0
        }
    }
}

fun List<List<Int>>.toPrintableString() = this.joinToString("\n") { it.map { if (it == 0) "." else it.toString() }.joinToString("") }
