package com.grasell

import java.io.File

fun main(args: Array<String>) {
    val trie = buildDictionary("dictionary.txt")

    val s = "IYSWLASIZFNGDHNOCOEEEPDRDPAOEBBOQCJIUGEIAIDDWHEGUHYIBURRUASUZDEWEXPAVERUJUDUNZTULTTVCRMTKRMUNIIWDEZZ".toLowerCase()

    val tiles = parseSquareBoard(s)

    solveBoard(tiles, trie)
            .distinct()
            .sorted()
            .forEach {
                println(it)
            }

}

private fun buildDictionary(fileName: String): Trie {
    val inputStream = File(fileName).inputStream()

    val rawWords = inputStream.bufferedReader()
            .lineSequence()
            .map { it.toLowerCase() }

    return buildTrie(rawWords)
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