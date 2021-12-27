fun main() {
    println(Day2Part2().positionProduct(day2Input))
}

class Day2Part2 {
    fun positionProduct(input: List<Instruction>): Int {
        return input.fold(AimCoordinates(0, 0, 0)) { loc, instruction ->
            when (instruction.direction) {
                Direction.forward -> loc.copy(x = loc.x + instruction.amount, y = loc.y + (loc.aim * instruction.amount))
                Direction.up -> loc.copy(aim = loc.aim - instruction.amount)
                Direction.down -> loc.copy(aim = loc.aim + instruction.amount)
            }
        }.let { it.x * it.y }
    }
}

data class AimCoordinates(val x: Int, val y: Int, val aim: Int)
