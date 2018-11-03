package com.grasell

import org.junit.Test

class TrieTest {

    @Test
    fun testBuildTrie() {
        val words = listOf("but", "chunky", "peanut", "butter", "butler")
        val wordsAsSequence = words.asSequence()

        val trie = buildTrie(wordsAsSequence)

        println("done")
    }
}
