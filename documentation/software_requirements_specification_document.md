# Software requirements specification document

AI for a n,m,k game(k-in-a-row on a m x n board) using Java.

## Algorithms and data structures

Minimax algorithm with alpha-beta pruning and a game tree that is a directed graph.

### Problems and solutions

N,m,k games are perfect information games, thus solvable with the minimax algorithm. Depth of the search must be limited and an evaluation function used when the size of the board grows.

### Goals for time and space complexity

Time: O(m^(d/2)) Space: O(md), where m is the number of legal moves and d is the max depth of the search.

## Sources 

[Minimax](https://en.wikipedia.org/wiki/Minimax)

[Game tree](https://en.wikipedia.org/wiki/Game_tree)

[m,n,k game](https://en.wikipedia.org/wiki/M,n,k-game)

* Curriculum: Bachelor of Computer Science