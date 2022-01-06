fun main() {
    println(Day11Part2().octopus(day11RealInput))
}

class Day11Part2 {
    fun octopus(matrix: List<List<Int>>): Int {
        println("Before any steps:")
        println(matrix.toOctopusString())
        println()
        return step(matrix, 0, 0, 100)
    }

    private tailrec fun step(matrix: List<List<Int>>, flashes: Int, step: Int, lastStep: Int): Int {
        val stepped = matrix.increment()
        println("Increment step ${step + 1}:")
        println(stepped.toOctopusString())
        println()
        val (flashed, newFlashes) = flash(stepped, emptySet(), 0)
        return if (newFlashes == matrix.toPoints().size) {
            step + 1
        } else {
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
