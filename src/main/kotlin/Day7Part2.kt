import java.lang.Math.abs

fun main() {
    println(Day7Part2().leastFuelConsumption(day7Input))
}

class Day7Part2 {
    fun leastFuelConsumption(crabPositions: List<Int>): Int {
        val max = crabPositions.maxByOrNull { it }!!
        val min = crabPositions.minByOrNull { it }!!
        return (min..max).map { possibleCentre ->
            crabPositions.map {
                val diff = abs(it - possibleCentre)
                (1..diff).sum()
            }.sum()
        }.minByOrNull { it }!!
    }
}

val day7SampleInput = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }
