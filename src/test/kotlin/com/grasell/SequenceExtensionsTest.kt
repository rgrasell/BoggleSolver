package com.grasell

import org.junit.Assert.*
import org.junit.Test

class SequenceExtensionsTest {

    @Test
    fun `batch splits sequence into correct sized chunks`() {
        val input = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        
        val batches = input.batch(3).toList()
        
        assertEquals(4, batches.size)
        assertEquals(listOf(1, 2, 3), batches[0])
        assertEquals(listOf(4, 5, 6), batches[1])
        assertEquals(listOf(7, 8, 9), batches[2])
        assertEquals(listOf(10), batches[3]) // Last batch may be smaller
    }

    @Test
    fun `batch handles empty sequence`() {
        val input = emptySequence<Int>()
        
        val batches = input.batch(3).toList()
        
        assertTrue(batches.isEmpty())
    }

    @Test
    fun `batch handles batch size larger than sequence`() {
        val input = sequenceOf(1, 2, 3)
        
        val batches = input.batch(10).toList()
        
        assertEquals(1, batches.size)
        assertEquals(listOf(1, 2, 3), batches[0])
    }

    @Test
    fun `batch handles batch size of 1`() {
        val input = sequenceOf('a', 'b', 'c')
        
        val batches = input.batch(1).toList()
        
        assertEquals(3, batches.size)
        assertEquals(listOf('a'), batches[0])
        assertEquals(listOf('b'), batches[1])
        assertEquals(listOf('c'), batches[2])
    }

    @Test
    fun `batch handles batch size of 0 or negative`() {
        val input = sequenceOf(1, 2, 3)
        
        val batches = input.batch(0).toList()
        
        assertTrue(batches.isEmpty())
    }

    @Test
    fun `batchWhile stops when predicate fails`() {
        val input = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        
        // Take batches while sum of batch is less than 10
        val batches = input.batchWhile(3) { batch -> batch.sum() < 10 }.toList()
        
        assertEquals(2, batches.size)
        assertEquals(listOf(1, 2, 3), batches[0]) // sum = 6
        assertEquals(listOf(4, 5, 6), batches[1]) // sum = 15, but predicate checked before adding
    }

    @Test
    fun `sortedByDirection ascending works correctly`() {
        val input = sequenceOf(3, 1, 4, 1, 5, 9, 2, 6)
        
        val sorted = input.sortedByDirection(descending = false) { it }.toList()
        
        assertEquals(listOf(1, 1, 2, 3, 4, 5, 6, 9), sorted)
    }

    @Test
    fun `sortedByDirection descending works correctly`() {
        val input = sequenceOf(3, 1, 4, 1, 5, 9, 2, 6)
        
        val sorted = input.sortedByDirection(descending = true) { it }.toList()
        
        assertEquals(listOf(9, 6, 5, 4, 3, 2, 1, 1), sorted)
    }

    @Test
    fun `batch is lazy and only processes needed elements`() {
        var processedCount = 0
        val input = generateSequence(1) { 
            processedCount++
            it + 1 
        }
        
        // Only take first 2 batches of 3
        val batches = input.batch(3).take(2).toList()
        
        assertEquals(2, batches.size)
        assertEquals(listOf(1, 2, 3), batches[0])
        assertEquals(listOf(4, 5, 6), batches[1])
        // Should have only processed enough elements for 2 batches
        assertTrue(processedCount <= 6)
    }
}
