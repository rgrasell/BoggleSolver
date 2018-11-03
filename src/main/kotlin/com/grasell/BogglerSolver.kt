package com.grasell

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.immutableSetOf
import java.io.File

fun main(args: Array<String>) {
    val trie = buildDictionary("dictionary.txt")

    val tiles = buildSampleBoard()

    solveBoard(tiles, trie)
            .sorted()
            .forEach {
                println(it)
            }
}

/**
 * Recursively search the entire board for words that appear in the dictionary.
 * Returns as sequence for performance.
 */
private fun solveBoard(tiles: Set<Tile>, trie: Trie): Sequence<String> {
    return tiles.asSequence()
            .map { Pair(it, trie.next(it.character)) }
            .filter { it.second != null }
            .flatMap { searchOneTile(it.first, immutableSetOf(), it.second!!) }
            .filter { it.length > 1 }
}

/**
 * Start a recursive search on one tile.
 * This will find all words that begin on the input tile.
 * To solve an entire board, this must be called multiple times.
 */
private fun searchOneTile(tile: Tile, visitedTiles: ImmutableSet<Tile>, trieCursor: Trie): Sequence<String> {
    // If the current search path found a word, add it to the output sequence
    var maybeWord = sequenceOf<String>()
    if (trieCursor.fullString != null){
        maybeWord = sequenceOf(trieCursor.fullString!!)
    }

    // Recursively search from this tile to each of its neighbors
    return maybeWord + tile.neighbors()
            .filter { !visitedTiles.contains(it) }
            .filter { trieCursor.next(it.character) != null }
            .map { Pair(it, trieCursor.next(it.character)!!) }
            .flatMap { (nextTile, nextTrie) -> searchOneTile(nextTile, visitedTiles.add(tile), nextTrie) }
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

private fun buildDictionary(fileName: String): Trie {
    val inputStream = File(fileName).inputStream()

    val rawWords = inputStream.bufferedReader()
            .lineSequence()
            .map { it.toLowerCase() }

    return buildTrie(rawWords)
}