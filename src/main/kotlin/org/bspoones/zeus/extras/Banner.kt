package org.bspoones.zeus.extras

import org.bspoones.zeus.VERSION
import org.bspoones.zeus.Zeus

private val ZEUS_TEXT = listOf(
    " +-----------------------------------------------+-------------+ ",
    " |  oooooooooooo                                 | Zeus v$VERSION   | ",
    " | d'\"\"\"\"\"\"d888'                                 |             | ",
    " |       .888P    .ooooo.  oooo  oooo   .oooo.o  | Made by:    | ",
    " |      d888'    d88' `88b `888  `888  d88(  \"8  |  BSpoones   | ",
    " |    .888P      888ooo888  888   888  `\"Y88b.   |             | ",
    " |   d888'    .P 888    .o  888   888  o.  )88b  | GitHub.com/ | ",
    " | .8888888888P  `Y8bod8P'  `V88V\"V8P' 8\"\"888P'  |  BSpoones   | ",
    " +-----------------------------------------------+-------------+ "
)
private const val START_COLOUR = "#FF0000"
private const val END_COLOUR = "#0000FF"
private const val START_INDEX = 3
private const val LENGTH = 45

object Banner {
    /**
     * Gradient generator
     *
     * ChatGPT my beloved
     */
    private fun generateColorGradient(startColor: String, endColor: String, size: Int): List<String> {
        fun interpolateComponent(start: Int, end: Int, steps: Int, step: Int): Int {
            return start + (step * (end - start) / steps)
        }

        fun interpolateColor(start: Int, end: Int, steps: Int, step: Int): Int {
            val startRed = start shr 16 and 0xFF
            val startGreen = start shr 8 and 0xFF
            val startBlue = start and 0xFF

            val endRed = end shr 16 and 0xFF
            val endGreen = end shr 8 and 0xFF
            val endBlue = end and 0xFF

            val newRed = interpolateComponent(startRed, endRed, steps, step)
            val newGreen = interpolateComponent(startGreen, endGreen, steps, step)
            val newBlue = interpolateComponent(startBlue, endBlue, steps, step)

            return (newRed shl 16) or (newGreen shl 8) or newBlue
        }

        val startInt = startColor.substring(1).toInt(16)
        val endInt = endColor.substring(1).toInt(16)

        val colors = mutableListOf<String>()
        for (step in 0 until size) {
            val interpolatedColorInt = interpolateColor(startInt, endInt, size - 1, step)
            val interpolatedColorHex = String.format("#%06X", interpolatedColorInt)
            colors.add(interpolatedColorHex)
        }

        return colors.map {
            val red = it.substring(1, 3).toInt(16)
            val green = it.substring(3, 5).toInt(16)
            val blue = it.substring(5, 7).toInt(16)

            "\u001B[38;2;$red;$green;${blue}m"
        }
    }


    /**
     * Sends log banner to console
     *
     * @see [Zeus]
     */
    fun logBanner() {
        ZEUS_TEXT.forEachIndexed { lineIndex, line ->
            val colors = generateColorGradient(START_COLOUR, END_COLOUR, START_INDEX + LENGTH)
            println(
                line.mapIndexed { index, char ->
                    if ((index < START_INDEX || (LENGTH + START_INDEX) < index) || (lineIndex == 0) || (lineIndex == 8)) {
                        "\u001B[0m$char"
                    } else {
                        "${colors[index - START_INDEX]}$char"
                    }
                }.joinToString("")
            )
        }
    }
}