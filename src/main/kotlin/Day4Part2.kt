fun main() {
    println(Day4Part2().loserScore(day4Input))
}

class Day4Part2 {
    fun loserScore(input: Game): Int {
        val boards = input.boards.toSet()
        val progressiveDraws = input.draw.scan(emptySet<Int>()) { acc, num -> acc + num }
        val boardToWinningDraw = boards.map { board ->
            board to progressiveDraws.first { draw ->
                board.map { it.toSet() }.any { draw.containsAll(it) } || board.transpose().map { it.toSet() }.any { draw.containsAll(it) }
            }
        }
        val (deadLastBoard, draw) = boardToWinningDraw.maxByOrNull { it.second.size }!!

        return deadLastBoard.flatten().toSet().subtract(draw).sum() * draw.last()
    }
}
