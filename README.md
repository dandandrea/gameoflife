This is a simple implementation of Conway's Game of Life

Read more about the Game of Life here: http://en.wikipedia.org/wiki/Conway's_Game_of_Life

Usage: java GameOfLife <configuration file> <tick delay in milliseconds>

The configuration file holds the size of the board and which cells should be "alive" initially

The format for the configuration file is as follows:

The first line contains the size of the board (e.g. enter 5 for a 5x5 board)
The remaining lines contain the row and column of a "live" cell
See glider.txt in the project root for an example configuration file

The tick delay determines how long of a delay is provided between displaying subsequent generations

The program runs until there are no changes between two subsequent generations
