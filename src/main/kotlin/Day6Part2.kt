fun main() {
    println(Day6Part2().numFish(day6Input, 256))
}

class Day6Part2 {
    fun numFish(fish: List<Int>, days: Int): Long {
        val inputDayToNumBirths = fish.groupBy { it }.mapValues { it.value.size.toLong() }
        val resultDayToNumBirths = iterate(inputDayToNumBirths, 0, days)
        return resultDayToNumBirths.filterKeys { it >= days }.map { it.value }.sum()
    }

    private tailrec fun iterate(dayToNumBirths: Map<Int, Long>, day: Int, lastDay: Int): Map<Int, Long> {
        println("$day - ${dayToNumBirths.mapKeys { it.key - day }.filterKeys { it >= 0 }.filterValues { it > 0 }} - births=${dayToNumBirths[day] ?: 0}")
        return if (day == lastDay) dayToNumBirths else {
            val numBirths = dayToNumBirths[day] ?: 0L
            val parentBirthDay = day + 7
            val childBirthDay = day + 9
            val existingParentBirthdays = dayToNumBirths[parentBirthDay] ?: 0L
            val withExistingParents = dayToNumBirths + (parentBirthDay to (numBirths + existingParentBirthdays))
            val withChildren = withExistingParents + (childBirthDay to numBirths)
            return iterate(withChildren, day + 1, lastDay)
        }
    }
}

fun List<Int>.toPrintableString() = this.joinToString(",")
