package com.grasell

import org.junit.Assert.assertEquals
import org.junit.Test

class BoggleTest {

    @Test
    fun boggleTest() {
        val boardArray =
                arrayOf(
                        arrayOf('a', 'b'),
                        arrayOf('c', 'd')
                )

        val tiles = buildBoard(boardArray)
        val words = listOf("ab", "ba", "abcd", "dcba", "acbd", "ad", "bd", "aa", "aba", "abcde")
        val trie = buildTrie(words.asSequence())

        val results = solveBoard(tiles, trie).toSet()
        val expected = setOf("ab", "ba", "abcd", "dcba", "acbd", "ad", "bd")

        assertEquals(expected, results)
    }
}