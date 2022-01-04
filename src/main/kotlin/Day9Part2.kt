fun main() {
    println(Day9Part2().largestBasinsSize(day9SampleInput))
}

class Day9Part2 {
    fun largestBasinsSize(matrix: List<List<Int>>): Int {
        val horizontalMinPoints = horizontalMinPoints(matrix, false)
        val verticalMinPoints = horizontalMinPoints(matrix.transpose(), true)
        val intersectingMinPoints = horizontalMinPoints intersect verticalMinPoints

        val basins = intersectingMinPoints.map {
            traverse(it, emptySet(), matrix)
        }

        return basins.map { it.size }.sortedDescending().take(3).reduce { a, b -> a * b }
    }

    private fun traverse(from: Point, ignore: Set<Point>, matrix: List<List<Int>>): Set<Point> {
        val surroundingPoints = matrix.surroundingPointsOff(from)
        val newPoints = surroundingPoints - ignore
        val ninesRemoved = newPoints.filter { it.h < 9 }
        return (ninesRemoved + ninesRemoved.flatMap { traverse(it, ignore + ninesRemoved, matrix) }).toSet()
    }

    private fun List<List<Int>>.surroundingPointsOff(point: Point): Set<Point> {
        val maxY = this.size - 1
        val maxX = this.first().size - 1
        val north: Point? = if (point.y == 0) null else Point(point.x, point.y - 1, this[point.y - 1][point.x])
        val south: Point? = if (point.y == maxY) null else Point(point.x, point.y + 1, this[point.y + 1][point.x])
        val east: Point? = if (point.x == 0) null else Point(point.x - 1, point.y, this[point.y][point.x - 1])
        val west: Point? = if (point.x == maxX) null else Point(point.x + 1, point.y, this[point.y][point.x + 1])
        return setOf(north, south, east, west).filterNotNull().toSet()
    }

    private fun horizontalMinPoints(matrix: List<List<Int>>, flipXY: Boolean) = matrix.flatMapIndexed { yIndex, row ->
        (row + Int.MAX_VALUE).windowed(2).foldIndexed(Accumulator(emptyList(), Int.MAX_VALUE)) { xIndex, acc, (prev, current) ->
            if (current > prev) {
                if (prev < acc.lastMinHeight) {
                    val newPoint = if (flipXY) Point(yIndex, xIndex, prev) else Point(xIndex, yIndex, prev)
                    acc.copy(points = acc.points + newPoint, lastMinHeight = prev)
                } else {
                    acc
                }
            } else {
                acc.copy(lastMinHeight = Int.MAX_VALUE)
            }
        }.points
    }
}

val day9SampleInput = """2199943210
3987894921
9856789892
8767896789
9899965678""".split("\n").map {
    it.toList().map { it.toString().toInt() }
}
