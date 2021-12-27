fun main() {
    println(Day3Part2().power(day3Input))
}

class Day3Part2 {
    fun power(input: List<List<Char>>): Int {
        val oxygenGeneratorRating = shrink(input, 0, { this.maxByOrNull { it.value }!! }, { this.maxOrNull()!! })
        val co2ScrubberRating = shrink(input, 0, { this.minByOrNull { it.value }!! }, { this.minOrNull()!! })
        return oxygenGeneratorRating * co2ScrubberRating
    }

    private tailrec fun shrink(input: List<List<Char>>, columnIndex: Int, bitCriteria: Map<Char, Int>.() -> Map.Entry<Char, Int>, tieBreaker: List<Char>.() -> Char): Int {
        return if (input.size == 1) {
            input.first().toDecimal()
        } else {
            val column = input.map { it[columnIndex] }
            val charToCount = column.groupBy { it }.mapValues { it.value.size }
            val maxCount = charToCount.bitCriteria().value
            val mostCommonChar = charToCount.filterValues { it == maxCount }.map { it.key }.tieBreaker()
            return shrink(input.filter { it[columnIndex] == mostCommonChar }, columnIndex + 1, bitCriteria, tieBreaker)
        }
    }

    private fun List<Char>.toDecimal(): Int = this.joinToString(separator = "").toInt(2)
}
