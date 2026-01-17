# BoggleSolver

A Kotlin-based Boggle word solver that efficiently finds all valid words on a Boggle board using a Trie data structure.

## Features

- **Efficient word lookup**: Uses a Trie (prefix tree) for O(k) word lookups where k is word length
- **Graph-based board representation**: Tiles are linked to their neighbors for efficient traversal
- **Lazy evaluation**: Uses Kotlin sequences for memory-efficient processing of large boards
- **Immutable state tracking**: Uses immutable collections to track visited tiles during recursion

## Building

Requires Java 17+ and Maven.

```bash
mvn clean compile
```

## Running Tests

```bash
mvn test
```

## Usage

Run with default dictionary and board:
```bash
mvn exec:java -Dexec.mainClass="com.grasell.MainKt"
```

Run with custom dictionary and board:
```bash
mvn exec:java -Dexec.mainClass="com.grasell.MainKt" -Dexec.args="path/to/dictionary.txt BOARDSTRING"
```

The board string should be a sequence of characters that forms a perfect square (e.g., 16 characters for a 4x4 board).

## Project Structure

- `BogglerSolver.kt` - Main solving algorithm using recursive DFS
- `Trie.kt` - Trie data structure for efficient dictionary lookups
- `Tile.kt` - Board tile representation and board building functions
- `SequenceExtensions.kt` - Utility extensions for Kotlin sequences
- `Main.kt` - Entry point with example usage

## Algorithm

1. Build a Trie from the dictionary for efficient prefix checking
2. Parse the board string into a graph of connected Tiles
3. For each tile, perform a depth-first search:
   - Track visited tiles using an immutable set
   - At each step, check if the current path forms a valid prefix in the Trie
   - If a complete word is found, add it to results
   - Continue to unvisited neighbors that have valid Trie continuations
4. Return all unique words longer than 1 character

## Dictionary

Dictionary in this repo courtesy of http://www.gwicks.net/dictionaries.htm