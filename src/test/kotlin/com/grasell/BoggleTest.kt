package com.grasell

import org.junit.Test

class BoggleTest {

    @Test
    fun boggleTest() {
        val boardArray =
                arrayOf(
                        arrayOf('s', 'e', 'r', 's'),
                        arrayOf('p', 'a', 't', 'g'),
                        arrayOf('l', 'i', 'n', 'e'),
                        arrayOf('s', 'e', 'r', 's')
                )



        val tiles = buildBoard(boardArray)
    }
}