package com.grasell

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class TrieTest {

    @Test
    fun testBuildTrie() {
        val words = listOf("but", "chunky", "peanut", "butter", "butler")
        val wordsAsSequence = words.asSequence()

        val trie = buildTrie(wordsAsSequence)

        val bNode = trie.next('b')
        assertNotNull(bNode)
        val uNode = bNode!!.next('u')
        assertNotNull(uNode)
        val tNode = uNode!!.next('t')
        assertNotNull(tNode)
        assertEquals("but", tNode!!.fullString)

        val secondTNode = tNode.next('t')
        assertNotNull(secondTNode)
        assertNull(secondTNode!!.fullString)

        val lNode = tNode.next('l')
        assertNotNull(lNode)
        val eNode = lNode!!.next('e')
        assertNotNull(eNode)
        val rNode = eNode!!.next('r')
        assertNotNull(rNode)
        assertEquals("butler", rNode!!.fullString)

        assertNull(trie.next('z'))
    }
}
