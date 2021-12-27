fun main() {
    println(Day3Part2().power(day3Input))
}

class Day3Part2 {
    fun power(input: List<List<Char>>): Int {
        val oxygenGeneratorRating = shrink(input, 0, { this.maxByOrNull { it.value }!! }, { this.maxOrNull()!! })
        val co2ScrubberRating = shrink(input, 0, { this.minByOrNull { it.value }!! }, { this.minOrNull()!! })
        return oxygenGeneratorRating * co2ScrubberRating
    }

    private tailrec fun shrink(input: List<List<Char>>, position: Int, bitCriteria: Map<Char, Int>.() -> Map.Entry<Char, Int>, tieBreaker: List<Char>.() -> Char): Int {
        return if (input.size == 1) {
            input.first().joinToString(separator = "").toInt(2)
        } else {
            val column = input.map { it[position] }
            val charToCount = column.groupBy { it }.mapValues { it.value.size }
            val maxCount = charToCount.bitCriteria().value
            val mostCommonChar = charToCount.filterValues { it == maxCount }.map { it.key }.tieBreaker()
            return shrink(input.filter { it[position] == mostCommonChar }, position + 1, bitCriteria, tieBreaker)
        }
    }
}
