package com.grasell

import java.io.File

fun main(args: Array<String>) {
    val dictionaryFile = if (args.isNotEmpty()) args[0] else "dictionary.txt"
    val trie = buildDictionary(dictionaryFile)

    val boardString = if (args.size > 1) {
        args[1]
    } else {
        "IYSWLASIZFNGDHNOCOEEEPDRDPAOEBBOQCJIUGEIAIDDWHEGUHYIBURRUASUZDEWEXPAVERUJUDUNZTULTTVCRMTKRMUNIIWDEZZ"
    }

    val tiles = parseSquareBoard(boardString.lowercase())

    solveBoard(tiles, trie)
            .sorted()
            .forEach { println(it) }
}

private fun buildDictionary(fileName: String): Trie {
    val inputStream = File(fileName).inputStream()

    val rawWords = inputStream.bufferedReader()
            .lineSequence()
            .map { it.lowercase() }

    return buildTrie(rawWords)
}