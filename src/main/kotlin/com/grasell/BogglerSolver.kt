package com.grasell

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.immutableSetOf
import java.io.File

fun main(args: Array<String>) {
    val trie = buildDictionary()

    val tiles = boggleBoard()

    findWords(tiles, trie)
            .sorted()
            .forEach {
                println(it)
            }
}

private fun search(tile: Tile, visitedTiles: ImmutableSet<Tile>, trieCursor: Trie): Sequence<String> {
    var maybeWord = sequenceOf<String>()

    if (trieCursor.fullString != null){
        maybeWord = sequenceOf(trieCursor.fullString!!)
    }

    return maybeWord + tile.neighbors()
            .filter { !visitedTiles.contains(it) }
            .filter { trieCursor.next(it.character) != null }
            .map { Pair(it, trieCursor.next(it.character)!!) }
            .flatMap { (nextTile, nextTrie) -> search(nextTile, visitedTiles.add(tile), nextTrie) }
}

private fun findWords(tiles: Set<Tile>, trie: Trie): Sequence<String> {
    return tiles.asSequence()
            .map { Pair(it, trie.next(it.character)) }
            .filter { it.second != null }
            .flatMap { search(it.first, immutableSetOf(), it.second!!) }
            .filter { it.length > 1 }
}


private fun buildDictionary(): Trie {
    val inputStream = File("dictionary.txt").inputStream()

    val rawWords = inputStream.bufferedReader()
            .lineSequence()
            .map { it.toLowerCase() }

    return buildTrie(rawWords)
}

fun boggleBoard(): Set<Tile> {
    val boardArray =
            arrayOf(
                    arrayOf('s', 'e', 'r', 's'),
                    arrayOf('p', 'a', 't', 'g'),
                    arrayOf('l', 'i', 'n', 'e'),
                    arrayOf('s', 'e', 'r', 's')
            )

    return buildBoard(boardArray)
}