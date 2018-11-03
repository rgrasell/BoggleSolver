package com.grasell

class Tile(val character: Char) {

    var up: Tile? = null
    var upright: Tile? = null
    var right: Tile? = null
    var downRight: Tile? = null
    var down: Tile? = null
    var downLeft: Tile? = null
    var left: Tile? = null
    var upLeft: Tile? = null


    fun neighbors(): Sequence<Tile> {
        return sequenceOf(up, upright, right, downRight, down, downLeft, left, upLeft)
                .filter { it != null }
                .map { it as Tile }
    }
}

/**
 * Create a Tile for each Char in the input, and link them together logically as a board
 */
fun buildBoard(input: Array<Array<Char>>): Set<Tile> {
    val tileMap = mutableMapOf<Pair<Int, Int>, Tile>()

    input.forEachIndexed { y, xAxis ->
        xAxis.forEachIndexed { x, element ->
            val tile = Tile(element)
            tileMap[Pair(x,y)] = tile

            if(tileMap.containsKey(Pair(x-1,y))){
                val foreigner = tileMap[Pair(x-1,y)]!!
                tile.left = foreigner
                foreigner.right = tile
            }

            if(tileMap.containsKey(Pair(x-1,y-1))){
                val foreigner = tileMap[Pair(x-1,y-1)]!!
                tile.upLeft = foreigner
                foreigner.downRight = tile
            }

            if(tileMap.containsKey(Pair(x,y-1))){
                val foreigner = tileMap[Pair(x,y-1)]!!
                tile.up = foreigner
                foreigner.down = tile
            }

            if(tileMap.containsKey(Pair(x-1,y+1))){
                val foreigner = tileMap[Pair(x-1,y+1)]!!
                tile.downLeft = foreigner
                foreigner.upright = tile
            }

            sequenceOf(Pair(x-1,y), Pair(x-1,y-1),Pair(x,y-1))
                    .map{tileMap[it]}
                    .filter { it != null }
                    .forEach {  }
        }
    }

    return tileMap.values.toSet()
}