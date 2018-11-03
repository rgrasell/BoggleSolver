package com.grasell

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

fun addSequenceToTrie(seq: Iterator<Char>,
                      fullString: String,
                      maybeNode: Trie?): Trie {

    val node = maybeNode ?: Trie()

    if (!seq.iterator().hasNext()) {
        node.fullString = fullString
    } else {
        val nextC = seq.next()
        val nextNode = node.next(nextC)
        node.setNext(nextC, addSequenceToTrie(seq, fullString, nextNode))
    }

    return node
}

fun buildTrie(input: Sequence<String>): Trie {
    val root = Trie()

    input.forEach { addSequenceToTrie(it.iterator(), it, root) }

    return root
}