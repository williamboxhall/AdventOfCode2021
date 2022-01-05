import java.lang.IllegalArgumentException

fun main() {
    println(Day10Part2().completionScore(day10Input))
}

class Day10Part2 {
    private val closeToOpenChar = mapOf(']' to '[', ')' to '(', '>' to '<', '}' to '{')
    private val openToCloseChar = closeToOpenChar.reverse()
    private val closeCharToPoints = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    fun completionScore(instructions: List<List<Char>>): Long {
        val incompleteLines = instructions.filter { firstIllegalCharFrom(it) == null }
        val completions = incompleteLines.map { completionForIncomplete(it) }
        val completionScores = completions.map { it.fold(0L) { score, char -> (score * 5L) + closeCharToPoints.getValue(char) } }
        return completionScores.sorted()[completionScores.size / 2]
    }

    private fun completionForIncomplete(line: List<Char>): List<Char> {
        val openChars = line.foldIndexed(emptyList<Char>()) { index, openChunks, character ->
            when (character) {
                '[', '(', '<', '{' -> openChunks + character
                ']', ')', '>', '}' -> {
                    val lastIndexForOpener = openChunks.lastIndexOf(closeToOpenChar[character])
                    val mutableOpenChunks = openChunks.toMutableList()
                    mutableOpenChunks.removeAt(lastIndexForOpener)
                    mutableOpenChunks.toList()
                }
                else -> throw IllegalArgumentException(character.toString())
            }
        }
        return openChars.reversed().map { openToCloseChar.getValue(it) }
    }

    fun firstIllegalCharFrom(line: List<Char>): Char? {
        line.foldIndexed(emptyList<Char>()) { index, openChunks, character ->
            when (character) {
                '[', '(', '<', '{' -> openChunks + character
                ']', ')', '>', '}' -> {
                    if (openChunks.last() != closeToOpenChar[character]) { // if we're closing something it must be the last open chunk
                        println("Expected ${openToCloseChar[openChunks.last()]} at index $index, but found $character instead. Line: ${line.joinToString("")}")
                        return character
                    } else {
                        val lastIndexForOpener = openChunks.lastIndexOf(closeToOpenChar[character])
                        val mutableOpenChunks = openChunks.toMutableList()
                        mutableOpenChunks.removeAt(lastIndexForOpener)
                        mutableOpenChunks.toList()
                    }
                }
                else -> throw IllegalArgumentException(character.toString())
            }
        }
        return null
    }
}

val day10SampleInput = """[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
""".split("\n").map { it.toList() }
