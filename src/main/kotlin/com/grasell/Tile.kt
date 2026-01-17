package com.grasell

import kotlin.math.sqrt

/**
 * Represents one Tile on a Boggle board.
 * There is no board class. Use the properties in Tile to create a graph to represent the board.
 */
class Tile(val character: Char) {

    var up: Tile? = null
    var upRight: Tile? = null
    var right: Tile? = null
    var downRight: Tile? = null
    var down: Tile? = null
    var downLeft: Tile? = null
    var left: Tile? = null
    var upLeft: Tile? = null

    fun neighbors(): Sequence<Tile> {
        return sequenceOf(up, upRight, right, downRight, down, downLeft, left, upLeft)
                .filterNotNull()
    }
}

/**
 * Create a Tile for each Char in the input, and link them together logically as a board.
 * 
 * @param input A 2D array of characters representing the board
 * @return A set of all tiles on the board, linked to their neighbors
 * @throws IllegalArgumentException if the input is empty
 */
fun buildBoard(input: Array<Array<Char>>): Set<Tile> {
    require(input.isNotEmpty() && input[0].isNotEmpty()) { "Board cannot be empty" }
    
    val tileMap = mutableMapOf<Pair<Int, Int>, Tile>()

    input.forEachIndexed { y, xAxis ->
        xAxis.forEachIndexed { x, element ->
            val tile = Tile(element)
            tileMap[x to y] = tile

            // Link to left neighbor
            tileMap[x - 1 to y]?.let { neighbor ->
                tile.left = neighbor
                neighbor.right = tile
            }

            // Link to upper-left neighbor
            tileMap[x - 1 to y - 1]?.let { neighbor ->
                tile.upLeft = neighbor
                neighbor.downRight = tile
            }

            // Link to upper neighbor
            tileMap[x to y - 1]?.let { neighbor ->
                tile.up = neighbor
                neighbor.down = tile
            }

            // Link to upper-right neighbor
            tileMap[x + 1 to y - 1]?.let { neighbor ->
                tile.upRight = neighbor
                neighbor.downLeft = tile
            }
        }
    }

    return tileMap.values.toSet()
}

/**
 * Parse a String into a square board.
 * Characters are placed from left to right, top to bottom.
 * 
 * @param input A string of characters to form a square board
 * @return A set of tiles representing the board
 * @throws IllegalArgumentException if the input length is not a perfect square
 */
fun parseSquareBoard(input: String): Set<Tile> {
    require(input.isNotEmpty()) { "Input string cannot be empty" }
    
    val dimension = sqrt(input.length.toDouble()).toInt()
    require(dimension * dimension == input.length) { 
        "Input length ${input.length} is not a perfect square" 
    }

    val arrayArray = input.asSequence()
            .batch(dimension)
            .map { it.toTypedArray() }
            .toList().toTypedArray()

    return buildBoard(arrayArray)
}