package gameOfLife;

import java.util.*;

public class Board {
    // Board size
    private int size;
 
    // Row list
    private List<List<Integer>> rowList;

	// Generation number
	int generationNumber;

    // Constructor
    public Board(int size) {
        // Set board size
        this.size = size;

		// Initialize generation number
		generationNumber = 1;

        // Instantiate row list
        rowList = new ArrayList<List<Integer>>();

        // Instantiate column lists
        for (int row = 0; row < size; row++) {
            rowList.add(new ArrayList<Integer>());
        }

        // Initialize column values
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                rowList.get(row).add(0);
            }
        }
    }

	// Advance the state of the board by one step
	// Returns true if there were any changes, false otherwise
	public boolean generate() throws GameOfLifeException {
		// Increment the generation number
		generationNumber++;

		// These are the changes that will take place, if any
		List<Change> changeList = new ArrayList<Change>();

		// Scan each cell for live neighbors and perform an evolution
		for (int row = 1; row <= size; row++) {
			for (int column = 1; column <= size; column++) {
				// Check this cell for live neighbors
				// Display the live neighbor count if this cell has any
				int liveNeighborCount = getLiveNeighborCount(row, column);
				if (liveNeighborCount > 0) {
					// Display the live neighbor count if debug enabled
					if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has " + liveNeighborCount + " live neighbors");
				}

				// Did it survive?
				// If live cell with less than 2 live neighbors then no
				// If live cell with 2 or 3 live neighbors then yes
				// If live cell with more than 4 live neighbors then no
				// If dead cell with 3 live neighbors then yes
				if (getValue(row, column) == 1) {
					// Live cell with less than 2 live neigbors or more than 4 live neighbors?
					// If so then it doesn't survive
					if (liveNeighborCount < 2 || liveNeighborCount > 3) {
						// Didn't survive
						changeList.add(new Change(row, column, 0));
					}
				}
				else {
					// Dead cell with 3 live neighbors?
					// If so then it comes to life
					if (liveNeighborCount == 3) {
						// Comes to life
						changeList.add(new Change(row, column, 1));
					}
				}
			}
		}

		// Apply the changes, if any
		for (int i = 0; i < changeList.size(); i++) {
			// Apply the change
			setValue(changeList.get(i).getRow(), changeList.get(i).getColumn(), changeList.get(i).getValue());
		}

		// Return true if there were any changes, false otherwise
		if (changeList.size() > 0) {
			// Changes
			return true;
		}
		else {
			// No changes
			return false;
		}
	}

	// Set a value
	public void setValue(int row, int column, int value) throws GameOfLifeException {
	    // Valid row and column?
		if (row < 1 || row > size || column < 1 || column > size) {
		    throw new GameOfLifeException("Row and column out of bounds");
		}

		// Value value?
		if (value != 0 && value != 1) {
		    throw new GameOfLifeException("Value must be 0 or 1");
		}

		// Set the value
		rowList.get(row - 1).set(column - 1, value);
	}

	// Get the generation number
	public int getGenerationNumber() {
		// Return the generation number
		return generationNumber;
	}

	// Determine how many live neighbors a cell has
	private int getLiveNeighborCount(int row, int column) {
		// The live neighbor count
		int liveNeighborCount = 0;

		// Live neighbor above?
		if (row < size && getValue(row + 1, column) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor above");
			liveNeighborCount++;
		}

		// Live neighbor below?
		if (row > 1 && getValue(row - 1, column) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor below");
			liveNeighborCount++;
		}

		// Live neighbor to the left?
		if (column > 1 && getValue(row, column - 1) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor to the left");
			liveNeighborCount++;
		}

		// Live neighbor to the right?
		if (column < size && getValue(row, column + 1) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor to the right");
			liveNeighborCount++;
		}

		// Live neighbor to the upper-left?
		if (row < size && column > 1 && getValue(row + 1, column - 1) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor to the upper-left");
			liveNeighborCount++;
		}

		// Live neighbor to the upper-right?
		if (row < size && column < size && getValue(row + 1, column + 1) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor to the upper-right");
			liveNeighborCount++;
		}

		// Live neighbor to the lower-left?
		if (row > 1 && column > 1 && getValue(row - 1, column - 1) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor to the lower-left");
			liveNeighborCount++;
		}

		// Live neighbor to the lower-right?
		if (row > 1 && column < size && getValue(row - 1, column + 1) == 1) {
			// Live neighbor
			if (GameOfLife.DEBUG) System.out.println("(" + row + ", " + column + ") has a live neighbor to the lower-right");
			liveNeighborCount++;
		}

		// Return the live neighbor count
		return liveNeighborCount;
	}

	// Get a value
	// Convenience method to allow one-based row and column numbers
	private int getValue(int row, int column) {
	    // Return the value
		return rowList.get(row - 1).get(column - 1);
	}

    // Override toString() in order to display the board
    public String toString() {
        // Get a StringBuilder
        StringBuilder stringBuilder = new StringBuilder();

		// Add the generation number
		stringBuilder.append("Generation " + generationNumber + ":\n");
		stringBuilder.append("\n");

        // Add the column header
        stringBuilder.append("       ");
        for (int column = 1; column <= size; column++) {
            stringBuilder.append(String.format("%-3d ", column));
        }
        stringBuilder.append("\n");

        // Display a line underneath the column header
        stringBuilder.append("-----");
        for (int column = 1; column <= size; column++) {
            stringBuilder.append("----");
        }
        stringBuilder.append("\n");

        // Iterate the rows
        for (int row = size - 1; row >= 0; row--) {
            // Display the row number followed by a "|" separator at
            // the beginning of each row
            stringBuilder.append(String.format("%3d ", row + 1) + " | ");

            // Iterate the columns
            for (int column = 0; column < size; column++) {
                // Display a "-" if the value is 0, otherwise display the value
                if (rowList.get(row).get(column) == 0) {
                    stringBuilder.append("-   ");
                } else {
                    stringBuilder.append("X   ");
                }
            }

            // New line if there is another row
            if (row != 0) {
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }

        // Return the String
        return stringBuilder.toString();
    }

	// Private inner-class for holding changes
	private class Change {
		// Row, column, and value
		private int row;
		private int column;
		private int value;

		// Constructor
		public Change(int row, int column, int value) {
			// Set row, column, and value
			this.row = row;
			this.column = column;
			this.value = value;
		}

		// Getter for row
		public int getRow() {
			return row;
		}

		// Getter for column
		public int getColumn() {
			return column;
		}

		// Getter for value
		public int getValue() {
			return value;
		}
	}
}
