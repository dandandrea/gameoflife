package gameOfLife;

import java.io.*;
import java.util.*;

public class GameOfLife {
	// Enable debug output here
	public static final boolean DEBUG = false;

    // The main entry point for the program
    public static void main(String[] args) throws GameOfLifeException {
	    // Introductory message
		System.out.println("");
        System.out.println("----------------------------");
        System.out.println("Welcome to The Game of Life!");
        System.out.println("----------------------------");
		System.out.println("");

		// Validate command-line arguments
		if (args == null || args.length != 2) {
			System.out.println("Usage: java GameOfLife <configuration file> <tick delay in milliseconds>");
			System.exit(1);
		}

		// Get the arguments
	    String filename = args[0];
		int tickDelay = -1;
		try {
			tickDelay = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e) {
		    System.out.println("ERROR: Invalid tick delay provided");
			System.exit(1);
		}

		// Was a valid filename provided?
		File file = new File(filename);
		if (file.exists() == false) {
		    System.out.println("ERROR: Invalid filename provided");
			System.exit(1);
		}

		// Initialize the board (and validate the configuration file format)
		Board board = null;
		try {
           	board = initializeBoard(file);
		}
		catch (IllegalFileFormatException e) {
		    System.out.println("ERROR: " + e.getMessage());
			System.exit(1);
		}

		// Display the initial board state
		System.out.println("");
		System.out.println(board);
		System.out.println("");

		// Tick until no more changes
		while (true) {
			// Perform the tick
			boolean wasChange = false;
			try {
				wasChange = board.generate();
			}
			catch (GameOfLifeException e) {
				System.out.println("ERROR: Caught an exception during a tick: " + e.getMessage());
			}

			// Break if there wasn't a change
			if (wasChange == false) {
				// No change, break
				System.out.println("No more changes");
				break;
			}

			// Display the new board state
			System.out.println("");
			System.out.println(board);
			System.out.println("");

			// Sleep
			try {
				Thread.sleep(tickDelay);
			}
			catch (Exception e) {}
		}
	}

    // Initialize board
    private static Board initializeBoard(File file) throws IllegalFileFormatException {
	    // Get a Scanner for the file
		Scanner scanner = null;
		try {
		    scanner = new Scanner(file);
	    }
		catch (FileNotFoundException e) {
		    // This can't happen because we already checked for the existence of the file
			System.out.println("ERROR: Unexpected state: File not found: " + e.getMessage());
			System.exit(1);
		}

	    // Read board size from file
		System.out.println("Loading configuration file");
		System.out.println("");
		int boardSize;
		try {
		    boardSize = scanner.nextInt();
		}
		catch (NoSuchElementException e) {
			throw new IllegalFileFormatException("File format is incorrect: could not get board size");
		}

        // Initialize board along with its size
        Board board = new Board(boardSize);

        // Set values
		while (true) {
		    try {
			    // Get the coordinates of the value
				int row = scanner.nextInt();
				int column = scanner.nextInt();

				// Add the value
				try {
					System.out.println("Adding " + row + ", " + column);
				    board.setValue(row, column, 1);
				}
				catch (GameOfLifeException e) {
			    	throw new IllegalFileFormatException("File format is incorrect: error processing item with row " + row + ", column " + column + ": " + e.getMessage());
				}
		    }
			catch (NoSuchElementException e) {
				// Done reading
				System.out.println("");
				System.out.println("Done loading configuration file");
				System.out.println("");
				break;
			}
		}

       // Return the board
       return board;
    }
}
