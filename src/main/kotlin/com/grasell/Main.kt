package com.grasell

import java.io.File
import java.util.Locale

fun main(args: Array<String>) {
    val trie = buildDictionary("dictionary.txt")

    val s = "IYSWLASIZFNGDHNOCOEEEPDRDPAOEBBOQCJIUGEIAIDDWHEGUHYIBURRUASUZDEWEXPAVERUJUDUNZTULTTVCRMTKRMUNIIWDEZZ".toLowerCase(Locale.ROOT)

    val tiles = parseSquareBoard(s)

    solveBoard(tiles, trie)
            .sorted()
            .forEach {
                println(it)
            }
}

private fun buildDictionary(fileName: String): Trie {
    return File(fileName).bufferedReader().useLines { lines ->
        buildTrie(lines.map { it.toLowerCase(Locale.ROOT) })
    }
}

private fun buildSampleBoard(): Set<Tile> {
    val boardArray =
            arrayOf(
                    arrayOf('s', 'e', 'r', 's'),
                    arrayOf('p', 'a', 't', 'g'),
                    arrayOf('l', 'i', 'n', 'e'),
                    arrayOf('s', 'e', 'r', 's')
            )

    return buildBoard(boardArray)
}