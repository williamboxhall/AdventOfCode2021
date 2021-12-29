fun main() {
    println(Day6Part2().numFish(day6Input, 256))
}

class Day6Part2 {
    fun numFish(fish: List<Int>, days: Int): Long {
        val inputDayToNumber = fish.groupBy { it }.mapValues { it.value.size.toLong() }
        val resultDayToNumber = iterate(inputDayToNumber, 0, days)
        return resultDayToNumber.filterKeys { it >= days }.map { it.value }.sum()
    }

    private tailrec fun iterate(dayToNumber: Map<Int, Long>, day: Int, lastDay: Int): Map<Int, Long> {
        println("$day - ${dayToNumber.mapKeys { it.key - day }.filterKeys { it >= 0 }.filterValues { it > 0 }} - births=${dayToNumber[day] ?: 0}")
        return if (day == lastDay) dayToNumber else {
            val numBirths = dayToNumber[day] ?: 0L
            val parentBirthDay = day + 7
            val childBirthDay = day + 9
            val existingParentBirthdays = dayToNumber[parentBirthDay] ?: 0L
            val withExistingParents = dayToNumber + (parentBirthDay to (numBirths + existingParentBirthdays))
            val withChildren = withExistingParents + (childBirthDay to numBirths)
            return iterate(withChildren, day + 1, lastDay)
        }
    }
}

fun List<Int>.toPrintableString() = this.joinToString(",")
