package com.grasell

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.immutableSetOf
import java.io.File

/**
 * Recursively search the entire board for words that appear in the dictionary.
 * Returns as sequence for performance.
 */
fun solveBoard(tiles: Set<Tile>, trie: Trie): Sequence<String> {
    return tiles.asSequence()
            .map { it to trie.next(it.character) }
            .filter { it.second != null }
            .flatMap { searchOneTile(it.first, immutableSetOf(), it.second!!) }
            .filter { it.length > 1 }
            .distinct()
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