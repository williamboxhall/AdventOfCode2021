import java.lang.IllegalArgumentException

fun main() {
    println(Day8Part2().sumOutputs(day8SampleInput))
}

class Day8Part2 {
    fun sumOutputs(entries: List<Entry>): Int {
        val outputs = entries.map {
            val inputsAsSegments = it.inputs.map { it.toSet() }
            val obviousDigitPatterns = inputsAsSegments.fold(emptyMap<Int, Set<Char>>()) { map, segments ->
                when (segments.size) {
                    2 -> map + (1 to segments)
                    3 -> map + (7 to segments)
                    4 -> map + (4 to segments)
                    7 -> map + (8 to segments)
                    else -> map
                }
            }
            val sevenPattern = obviousDigitPatterns.getValue(7)
            val fourPattern = obviousDigitPatterns.getValue(4)
            val nonObviousDigitPatterns = (inputsAsSegments - obviousDigitPatterns.values).fold(emptyMap<Int, Set<Char>>()) { map, segments ->
                when {
                    segments.containsAll(fourPattern) && segments.containsAll(sevenPattern) -> map + (9 to segments)
                    segments.containsAll(sevenPattern) && ((segments - sevenPattern).size == 3) -> map + (0 to segments)
                    segments.containsAll(sevenPattern) && ((segments - sevenPattern).size == 2) -> map + (3 to segments)
                    ((segments - sevenPattern - fourPattern).size == 1) -> map + (5 to segments)
                    segments.size == 6 -> map + (6 to segments)
                    segments.size == 5 -> map + (2 to segments)
                    else -> throw IllegalArgumentException("sucks")
                }
            }
            val digitPatterns = obviousDigitPatterns + nonObviousDigitPatterns
            val patternToDigit = digitPatterns.entries.associateBy({ it.value }) { it.key }
            val outputValues = it.inputs.map { it.toSet() }.map { patternToDigit.getValue(it) }
            val output = outputValues[0] * 1000 + outputValues[1] * 100 + outputValues[2] * 10 + outputValues[3]
            output
        }
        entries.map { it.inputs }.zip(outputs).forEach { println(it) }
        return outputs.sum()
    }
}

val day8SampleInput = """be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce""".split("\n").map {
    val (inputs, outputs) = it.split(" | ")
    Entry(inputs.split(" "), outputs.split(" "))
}
