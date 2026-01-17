package com.grasell

/**
 * Simple Trie class. Logic to build the Trie lives outside of the class, but in this file.
 * 
 * Note: For further optimization, the children map could be replaced with an array
 * indexed by character code for faster lookups.
 */
class Trie {

    private val children = mutableMapOf<Char, Trie>()
    var fullString: String? = null

    fun next(c: Char): Trie? {
        return children[c]
    }

    fun setNext(c: Char, trie: Trie) {
        children[c] = trie
    }

}

/**
 * Transform a sequence of Strings into a Trie
 */
fun buildTrie(input: Sequence<String>): Trie {
    val root = Trie()

    input.forEach { addWordToTrie(it, root) }

    return root
}

/**
 * Adds a single word to the Trie.
 */
private fun addWordToTrie(word: String, root: Trie) {
    var currentNode = root
    
    for (char in word) {
        val nextNode = currentNode.next(char)
        if (nextNode != null) {
            currentNode = nextNode
        } else {
            val newNode = Trie()
            currentNode.setNext(char, newNode)
            currentNode = newNode
        }
    }
    
    currentNode.fullString = word
}