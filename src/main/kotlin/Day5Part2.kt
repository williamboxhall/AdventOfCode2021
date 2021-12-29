import kotlin.math.abs

fun main() {
    println(Day5Part2().numLineOverlaps(day5Input))
}

class Day5Part2 {
    fun numLineOverlaps(lines: Set<Line>): Int {
        val onlyHorizontalOrVerticalOr45Diagonal = lines.filter { it.start.x == it.end.x || it.start.y == it.end.y || abs(it.start.y - it.end.y) == abs(it.start.x - it.end.x) }
        val linesAsMatrixes = onlyHorizontalOrVerticalOr45Diagonal.map { it.toMatrix() }
        val combinedMatrix = linesAsMatrixes.fold(emptyMatrix(rows, cols)) { acc, matrix -> acc add matrix }
        return combinedMatrix.flatten().filter { it >= 2 }.count()
    }
}
