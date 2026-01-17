package com.grasell

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.immutableSetOf

/**
 * Recursively search the entire board for words that appear in the dictionary.
 * Returns as sequence for performance.
 */
fun solveBoard(tiles: Set<Tile>, trie: Trie): Sequence<String> {
    return tiles.asSequence()
            .mapNotNull { tile -> trie.next(tile.character)?.let { tile to it } }
            .flatMap { (tile, trieCursor) -> searchOneTile(tile, immutableSetOf(), trieCursor) }
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
    val foundWord = trieCursor.fullString?.let { sequenceOf(it) } ?: emptySequence()

    // Recursively search from this tile to each of its neighbors
    val neighborWords = tile.neighbors()
            .filter { !visitedTiles.contains(it) }
            .mapNotNull { neighbor -> trieCursor.next(neighbor.character)?.let { neighbor to it } }
            .flatMap { (nextTile, nextTrie) -> searchOneTile(nextTile, visitedTiles.add(tile), nextTrie) }
    
    return foundWord + neighborWords
}