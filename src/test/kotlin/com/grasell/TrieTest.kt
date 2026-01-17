package com.grasell

import org.junit.Assert.*
import org.junit.Test

class TrieTest {

    @Test
    fun `buildTrie creates trie with all words`() {
        val words = listOf("but", "chunky", "peanut", "butter", "butler")
        
        val trie = buildTrie(words.asSequence())
        
        // Verify each word can be traversed and is marked as complete
        for (word in words) {
            var cursor: Trie? = trie
            for (char in word) {
                cursor = cursor?.next(char)
                assertNotNull("Should find path for '$word' at char '$char'", cursor)
            }
            assertEquals("Word '$word' should be marked as complete", word, cursor?.fullString)
        }
    }

    @Test
    fun `trie correctly identifies word prefixes vs complete words`() {
        val trie = buildTrie(sequenceOf("butter", "but"))
        
        // "but" is a complete word
        var cursor = trie.next('b')?.next('u')?.next('t')
        assertEquals("but", cursor?.fullString)
        
        // "butt" is not a word, just a prefix
        cursor = cursor?.next('t')
        assertNull(cursor?.fullString)
        
        // "butter" is a complete word
        cursor = cursor?.next('e')?.next('r')
        assertEquals("butter", cursor?.fullString)
    }

    @Test
    fun `trie returns null for non-existent paths`() {
        val trie = buildTrie(sequenceOf("cat", "car"))
        
        // Valid path
        assertNotNull(trie.next('c'))
        assertNotNull(trie.next('c')?.next('a'))
        
        // Invalid path - 'z' doesn't exist
        assertNull(trie.next('z'))
        
        // Invalid continuation - "caz" doesn't exist
        assertNull(trie.next('c')?.next('a')?.next('z'))
    }

    @Test
    fun `buildTrie handles empty sequence`() {
        val trie = buildTrie(emptySequence())
        
        // Should create an empty trie
        assertNull(trie.next('a'))
        assertNull(trie.fullString)
    }

    @Test
    fun `buildTrie handles single character words`() {
        val trie = buildTrie(sequenceOf("a", "i"))
        
        assertEquals("a", trie.next('a')?.fullString)
        assertEquals("i", trie.next('i')?.fullString)
    }

    @Test
    fun `buildTrie handles words with shared prefixes`() {
        val trie = buildTrie(sequenceOf("test", "testing", "tested", "tester"))
        
        // All should be findable
        var cursor = trie.next('t')?.next('e')?.next('s')?.next('t')
        assertEquals("test", cursor?.fullString)
        
        cursor = cursor?.next('i')?.next('n')?.next('g')
        assertEquals("testing", cursor?.fullString)
    }

    @Test
    fun `trie setNext overwrites existing child`() {
        val trie = Trie()
        val child1 = Trie()
        val child2 = Trie()
        
        trie.setNext('a', child1)
        assertEquals(child1, trie.next('a'))
        
        trie.setNext('a', child2)
        assertEquals(child2, trie.next('a'))
    }
}
