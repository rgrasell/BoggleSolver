package com.grasell

/**
 * Recursively search the entire board for words that appear in the dictionary.
 * Returns as sequence for performance.
 */
fun solveBoard(tiles: Set<Tile>, trie: Trie): Sequence<String> {
    return tiles.asSequence()
            .mapNotNull { tile -> trie.next(tile.character)?.let { tile to it } }
            .flatMap { (tile, nextTrie) -> searchOneTile(tile, mutableSetOf(), nextTrie) }
            .filter { it.length > 1 }
            .distinct()
}

/**
 * Start a recursive search on one tile.
 * This will find all words that begin on the input tile.
 * To solve an entire board, this must be called multiple times.
 */
private fun searchOneTile(tile: Tile, visitedTiles: MutableSet<Tile>, trieCursor: Trie): Sequence<String> = sequence {
    // If the current search path found a word, add it to the output sequence
    trieCursor.fullString?.let { yield(it) }

    // Recursively search from this tile to each of its neighbors
    visitedTiles.add(tile)
    for (neighbor in tile.neighbors()) {
        if (!visitedTiles.contains(neighbor)) {
            val nextTrie = trieCursor.next(neighbor.character)
            if (nextTrie != null) {
                yieldAll(searchOneTile(neighbor, visitedTiles, nextTrie))
            }
        }
    }
    visitedTiles.remove(tile)
}