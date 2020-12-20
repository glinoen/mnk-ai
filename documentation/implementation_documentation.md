# Implementation documentation

## Memory and time complexity

### MinMax

- Time complexity is the amount of legal moves to the power of depth: O(m^d). Alpha beta pruning limits the chance of worst case happening.
- Space complexity is the is the amount of legal moves multiplied by depth O(m*d)

### Ai and evaluation methods

- Finding empty squares: O(occupied squares)

- Evaluation of the board: If stored in hash map O(1), else O(all squares * 4)

- Checking for win O(all squares * 4)

### Data structures

- HashMap time complexity for add() and get() is O(1), for getting keys it's O(size of hash table)

- Stack put() and pop() are O(1)

Time: O(m^(d/2)) Space: O(md), where m is the number of legal moves and d is the max depth of the search.

## Logical structure

![simplestucture][mnksimplelogic.png]

## Pros and cons

- I'm pretty happy with the heuristic estimation, it is made for a k value of 5

- As the number of occupied squares the time complexity rises since there are more empty squares involved. The time also varies heavily depending on the size of the board and the depth of the minimax.

- Opening move book could be used.

- Zobrist hashing would be more efficent when repeatedely placing and removing pieces.

- Ai vs ai on graphical user interface is pretty buggy and only shows the end result.

## Sources

[https://lib.dr.iastate.edu/cgi/viewcontent.cgi?article=1491&context=creativecomponents]

[https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning]

[https://en.wikipedia.org/wiki/Gomoku]