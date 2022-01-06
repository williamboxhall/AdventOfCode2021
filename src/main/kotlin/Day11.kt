fun main() {
    println(Day11().octopus(day11RealInput))
}

class Day11 {
    fun octopus(matrix: List<List<Int>>): Int {
        println("Before any steps:")
        println(matrix.toOctopusString())
        println()
        return step(matrix, 0, 0, 100)
    }

    private tailrec fun step(matrix: List<List<Int>>, flashes: Int, step: Int, lastStep: Int): Int {
        return if (step == lastStep) {
            flashes
        } else {
            val stepped = matrix.increment()
            println("Increment step ${step + 1}:")
            println(stepped.toOctopusString())
            println()
            val (flashed, newFlashes) = flash(stepped, emptySet(), 0)
            println("Finished step ${step + 1}:")
            println(flashed.toOctopusString())
            println()
            step(flashed, flashes + newFlashes, step + 1, lastStep)
        }
    }

    private tailrec fun flash(matrix: List<List<Int>>, alreadyFlashed: Set<Point>, flashes: Int): Pair<List<List<Int>>, Int> {
        val readyToFlash = matrix.toPoints().filter { it.h > 9 }
        val newFlashes = readyToFlash.size
        return if (newFlashes == 0) {
            val allFlashersReset = alreadyFlashed.fold(matrix) { acc, point -> acc.replace(point, 0) }
            allFlashersReset to flashes
        } else {
            val surroundings = readyToFlash.flatMap { matrix.surroundingInclDiagonalPointsOf(it) } - alreadyFlashed - readyToFlash
            val flashersReset = readyToFlash.fold(matrix) { acc, point -> acc.replace(point, 0) }
            println("===Flashers reset===")
            println(flashersReset.toOctopusString())
            println()
            val surroundingsIncremented = surroundings.fold(flashersReset) { acc, point -> acc.replace(point, acc[point.y][point.x] + 1) }
            println("===Surroundings incremented===")
            println(surroundingsIncremented.toOctopusString())
            println()
            flash(surroundingsIncremented, readyToFlash.toSet() + alreadyFlashed, flashes + newFlashes)
        }
    }
}

fun List<List<Int>>.increment() = this.map { it.map { it + 1 } }

fun List<List<Int>>.replace(point: Point, newValue: Int): List<List<Int>> {
    val mutable = this.toMutableList().map { it.toMutableList() }
    mutable[point.y][point.x] = newValue
    return mutable.map { it.toList() }.toList()
}

fun List<List<Int>>.surroundingInclDiagonalPointsOf(point: Point): Set<Point> {
    val maxY = this.size - 1
    val maxX = this.first().size - 1
    val north: Point? = if (point.y == 0) null else Point(point.x, point.y - 1, this[point.y - 1][point.x])
    val south: Point? = if (point.y == maxY) null else Point(point.x, point.y + 1, this[point.y + 1][point.x])
    val west: Point? = if (point.x == 0) null else Point(point.x - 1, point.y, this[point.y][point.x - 1])
    val east: Point? = if (point.x == maxX) null else Point(point.x + 1, point.y, this[point.y][point.x + 1])
    val northEast: Point? = if (point.y == maxY || point.x == 0) null else Point(point.x - 1, point.y + 1, this[point.y + 1][point.x - 1])
    val northWest: Point? = if (point.x == 0 || point.y == 0) null else Point(point.x - 1, point.y - 1, this[point.y - 1][point.x - 1])
    val southEast: Point? = if (point.y == maxY || point.x == maxX) null else Point(point.x + 1, point.y + 1, this[point.y + 1][point.x + 1])
    val southWest: Point? = if (point.x == maxX || point.y == 0) null else Point(point.x + 1, point.y - 1, this[point.y - 1][point.x + 1])
    return setOf(north, south, east, west, northEast, northWest, southEast, southWest).filterNotNull().toSet()
}

fun List<List<Int>>.toPoints(): Set<Point> = this.flatMapIndexed { yIndex, row ->
    row.mapIndexed { xIndex, value ->
        Point(xIndex, yIndex, value)
    }
}.toSet()

fun List<List<Int>>.toOctopusString() = this.joinToString("\n") { it.map { if (it == 10) "^" else if (it > 10) "?" else it.toString() }.joinToString("") }

val day11SampleInput = """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526""".split("\n").map { it.toList().map { it.toString().toInt() } }

val day11RealInput = """3265255276
1537412665
7335746422
6426325658
3854434364
8717377486
4522286326
6337772845
8824387665
6351586484""".split("\n").map { it.toList().map { it.toString().toInt() } }
