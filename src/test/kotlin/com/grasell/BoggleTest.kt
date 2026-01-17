package com.grasell

import org.junit.Assert.*
import org.junit.Test

class BoggleTest {

    @Test
    fun `buildBoard creates correct number of tiles`() {
        val boardArray = arrayOf(
                arrayOf('s', 'e', 'r', 's'),
                arrayOf('p', 'a', 't', 'g'),
                arrayOf('l', 'i', 'n', 'e'),
                arrayOf('s', 'e', 'r', 's')
        )

        val tiles = buildBoard(boardArray)
        
        assertEquals(16, tiles.size)
    }

    @Test
    fun `buildBoard links neighbors correctly`() {
        val boardArray = arrayOf(
                arrayOf('a', 'b'),
                arrayOf('c', 'd')
        )

        val tiles = buildBoard(boardArray)
        val tileList = tiles.toList()
        
        // Find specific tiles by character
        val tileA = tileList.find { it.character == 'a' }!!
        val tileB = tileList.find { it.character == 'b' }!!
        val tileC = tileList.find { it.character == 'c' }!!
        val tileD = tileList.find { it.character == 'd' }!!
        
        // Check 'a' neighbors (top-left corner)
        assertEquals(tileB, tileA.right)
        assertEquals(tileC, tileA.down)
        assertEquals(tileD, tileA.downRight)
        assertNull(tileA.up)
        assertNull(tileA.left)
        
        // Check 'd' neighbors (bottom-right corner)
        assertEquals(tileC, tileD.left)
        assertEquals(tileB, tileD.up)
        assertEquals(tileA, tileD.upLeft)
    }

    @Test
    fun `parseSquareBoard parses string correctly`() {
        val tiles = parseSquareBoard("abcd")
        
        assertEquals(4, tiles.size)
        val characters = tiles.map { it.character }.toSet()
        assertEquals(setOf('a', 'b', 'c', 'd'), characters)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parseSquareBoard throws on non-square input`() {
        parseSquareBoard("abcde") // 5 is not a perfect square
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parseSquareBoard throws on empty input`() {
        parseSquareBoard("")
    }

    @Test
    fun `solveBoard finds simple words`() {
        // Create a board with known words
        val boardArray = arrayOf(
                arrayOf('c', 'a', 't'),
                arrayOf('o', 'r', 's'),
                arrayOf('w', 'e', 'd')
        )
        val tiles = buildBoard(boardArray)
        
        // Create a trie with some words
        val trie = buildTrie(sequenceOf("cat", "car", "cars", "rat", "rats", "art", "cow"))
        
        val foundWords = solveBoard(tiles, trie).toSet()
        
        assertTrue("Should find 'cat'", foundWords.contains("cat"))
        assertTrue("Should find 'car'", foundWords.contains("car"))
        assertTrue("Should find 'cars'", foundWords.contains("cars"))
        assertTrue("Should find 'art'", foundWords.contains("art"))
    }

    @Test
    fun `solveBoard does not reuse tiles in same word`() {
        // Board: a-a
        //        a-a
        val boardArray = arrayOf(
                arrayOf('a', 'a'),
                arrayOf('a', 'a')
        )
        val tiles = buildBoard(boardArray)
        
        // 'aaaa' would require reusing tiles
        val trie = buildTrie(sequenceOf("aa", "aaa", "aaaa", "aaaaa"))
        
        val foundWords = solveBoard(tiles, trie).toSet()
        
        assertTrue("Should find 'aa'", foundWords.contains("aa"))
        assertTrue("Should find 'aaa'", foundWords.contains("aaa"))
        assertTrue("Should find 'aaaa'", foundWords.contains("aaaa"))
        assertFalse("Should NOT find 'aaaaa' (would need 5 tiles)", foundWords.contains("aaaaa"))
    }

    @Test
    fun `solveBoard filters single character words`() {
        val boardArray = arrayOf(arrayOf('a', 'b'), arrayOf('c', 'd'))
        val tiles = buildBoard(boardArray)
        
        val trie = buildTrie(sequenceOf("a", "ab", "abc"))
        
        val foundWords = solveBoard(tiles, trie).toSet()
        
        assertFalse("Should filter single char 'a'", foundWords.contains("a"))
        assertTrue("Should find 'ab'", foundWords.contains("ab"))
    }
}