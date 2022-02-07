package q2.program;

import java.util.ArrayList;
import java.util.LinkedList;

public class AStarSliding {
	// Board class (inner class)
	private class Board {
		private char[][] array; // board array
		private int gvalue; // path cost
		private int hvalue; // heuristic value
		private int fvalue; // gvalue plus hvalue
		private Board parent; // parent board

		// Constructor of board class
		private Board(char[][] array, int size) {
			this.array = new char[size][size]; // create board array

			for (int i = 0; i < size; i++) // copy given array
				for (int j = 0; j < size; j++)
					this.array[i][j] = array[i][j];

			this.gvalue = 0; // path cost, heuristic value,
			this.hvalue = 0; // fvalue are all 0
			this.fvalue = 0;

			this.parent = null; // no parent
		}
	}

	private class Pair {
		private int first;
		private int second;

		private Pair(int one, int two) {
			this.setFirst(one);
			this.setSecond(two);
		}

		public int getFirst() {
			return first;
		}

		public void setFirst(int first) {
			this.first = first;
		}

		public int getSecond() {
			return second;
		}

		public void setSecond(int second) {
			this.second = second;
		}

	}

	private Board initial; // initial board
	private Board goal; // goal board
	private int size; // board size

	// Outputs
	private int swaps;
	private int boardsSearched;
	// private int runTime;

	// Time counting functions
	private long runTime;
	// Tracking a cumulative gapTime, the amount of time needed to subtract from the
	// final runtime, to get the runtime of only the algorithm itself
	private long gapTime;

	// User options
	private int evaluationOption;
	private int heuristicOption;

	// Class made to handle output writing
	public ProgramOutputs OutputWriter;

	// Constructor of SlidingAstar class
	public AStarSliding(char[][] initial, int size) {
		this.OutputWriter = new ProgramOutputs();
		this.OutputWriter.determineOutputFile();

		this.size = size; // set size of board
		this.initial = new Board(initial, size); // create initial board

		System.out.println();
		System.out.println("InitialBoard:");
		displayBoard(this.initial);

//		this.goal = new Board(goal, size); // create goal board
		this.goal = this.findGoalBoard();
		this.runTime = 0;
		this.gapTime = 0;
		this.boardsSearched = 0;
		this.swaps = -1; // Starts at -1, to avoid counting the initial board

		// TODO: Make sure these are the right values
		this.evaluationOption = 3;
		this.heuristicOption = 1;

//		System.out.println("Run generate children on start:");
//		this.generate(this.initial);
//		System.out.println("Finished generating start children");
//		System.exit(0);
	}

	// Method solves sliding puzzle
	public void solve() {
		long startTime = System.nanoTime();

		LinkedList<Board> openList = new LinkedList<Board>(); // open list
		LinkedList<Board> closedList = new LinkedList<Board>();// closed list

		openList.addFirst(initial); // add initial board to open list

		while (!openList.isEmpty()) // while open list has more boards
		{
			int best = selectBest(openList); // select best board

			Board board = openList.remove(best); // remove board

			closedList.addLast(board); // add board to closed list

			// After adding a board to move to closed list would be when you realize that
			// you searched a board yeah?
			this.boardsSearched++;

			if (goal(board)) // if board is goal
			{
				// calculate runtime
				long endTime = System.nanoTime();
				this.runTime = endTime - startTime;

				displayPath(board); // display path to goal

				return; // stop search
			} else // if board is not goal
			{
				// I was planning on counting gaptime, but generation of children is included in
				// the runtime of the serach algorithm, correct?
				// At first I was thinking it wouldn't be, but it calculates the heuristic here,
				// which is an important part of the algorithm working.
				LinkedList<Board> children = generate(board);// create children

				for (int i = 0; i < children.size(); i++) { // for each child
					Board child = children.get(i);

					if (!exists(child, closedList)) // if child is not in closed list
					{
						if (!exists(child, openList))// if child is not in open list
							openList.addLast(child); // add to open list
						else { // if child is already in open list
							int index = find(child, openList);
							if (child.fvalue < openList.get(index).fvalue) { // if fvalue of new copy
								openList.remove(index); // is less than old copy
								openList.addLast(child); // replace old copy
							} // with new copy
						}
					}
				}
			}
		}

		System.out.println("no solution"); // no solution if there are

		long endTime = System.nanoTime();
		this.runTime = endTime - startTime;
	} // no boards in open list

	// TODO:
	/*
	 * To generate children... For each R or G, check and see if there's a number
	 * next to it. If there is, swap it. For each R, see if there's a G. For each G,
	 * see if there's an R. If there are, swap them.
	 */

	// Method creates children of a board
	private LinkedList<Board> generate(Board board) {
//		int i = 0, j = 0;
//		boolean found = false;
//
//		for (i = 0; i < size; i++) // find location of empty slot
//		{ // of board
//			for (j = 0; j < size; j++)
//				// Need to be 0 or '0'?
////				if ((board.array[i][j] == 'R') || (board.array[i][j] == 'G')) {
//				if (board.array[i][j] == '0') {
//					found = true;
//					break;
//				}
//
//			if (found)
//				break;
//		}
//
//		boolean north, south, east, west; // decide whether empty slot
//		north = i == 0 ? false : true; // has N, S, E, W neighbors
//		south = i == size - 1 ? false : true;
//		east = j == size - 1 ? false : true;
//		west = j == 0 ? false : true;
//
//		LinkedList<Board> children = new LinkedList<Board>();// list of children
//
//		
//		//Do this for each R or G found.
//		if (north)
//			children.addLast(createChild(board, i, j, 'N')); // add N, S, E, W
//		if (south)
//			children.addLast(createChild(board, i, j, 'S')); // children if
//		if (east)
//			children.addLast(createChild(board, i, j, 'E')); // they exist
//		if (west)
//			children.addLast(createChild(board, i, j, 'W'));
//
//		return children; // return children

		// Find all R & G
		ArrayList<Pair> reds = new ArrayList<Pair>();
		ArrayList<Pair> greens = new ArrayList<Pair>();

		// The children to return
		LinkedList<Board> children = new LinkedList<Board>();// list of children

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (board.array[i][j] == 'R') {
					System.out.println("Found a Red");
					Pair location = new Pair(i, j);
					reds.add(location);
				} else if (board.array[i][j] == 'G') {
					System.out.println("Found a green");
					Pair location = new Pair(i, j);
					greens.add(location);
				}
			}
		}

		System.out.println("Initial R size: " + reds.size());
		System.out.println("Initial G size: " + greens.size());

		// Process reds
		for (int i = 0; i < reds.size(); i++) {
			boolean north, south, east, west; // decide whether empty slot

			Pair processing = reds.get(i);
			int itemFirst = processing.getFirst();
			int itemSecond = processing.getSecond();

			// Determine north
			if (itemFirst == 0) {
				north = false;
			} else if (Character.isDigit(board.array[itemFirst - 1][itemSecond])
					|| board.array[itemFirst - 1][itemSecond] == 'G') {
				System.out.println("N Tripped in reds, i=" + i);
				north = true;
			} else {
				north = false;
			}

			// Determine south
			if (itemFirst == this.size - 1) {
				south = false;
			} else if (Character.isDigit(board.array[itemFirst + 1][itemSecond])
					|| board.array[itemFirst + 1][itemSecond] == 'G') {
				System.out.println("S Tripped in reds, i=" + i);
				south = true;
			} else {
				south = false;
			}

			// Determine east
			if (itemSecond == this.size - 1) {
				east = false;
			} else if (Character.isDigit(board.array[itemFirst][itemSecond + 1])
					|| board.array[itemFirst][itemSecond + 1] == 'G') {
				System.out.println("E Tripped in reds, i=" + i);
				east = true;
			} else {
				east = false;
			}

			// Determine west
			if (itemSecond == 0) {
				west = false;
			} else if (Character.isDigit(board.array[itemFirst][itemSecond - 1])
					|| board.array[itemFirst][itemSecond - 1] == 'G') {
				System.out.println("W Tripped in reds, i=" + i);
				west = true;
			} else {
				west = false;
			}

			// Now, make a child for each direction
			if (north)
				children.addLast(createChild(board, itemFirst, itemSecond, 'N', 'R')); // add N, S, E, W
			if (south)
				children.addLast(createChild(board, itemFirst, itemSecond, 'S', 'R')); // children if
			if (east)
				children.addLast(createChild(board, itemFirst, itemSecond, 'E', 'R')); // they exist
			if (west)
				children.addLast(createChild(board, itemFirst, itemSecond, 'W', 'R'));
		}

		// Process greens
		for (int i = 0; i < greens.size(); i++) {
			boolean north, south, east, west; // decide whether empty slot

			Pair processing = greens.get(i);
			int itemFirst = processing.getFirst();
			int itemSecond = processing.getSecond();

			// Determine north
			if (itemFirst == 0) {
				north = false;
			} else if (Character.isDigit(board.array[itemFirst - 1][itemSecond])
					|| board.array[itemFirst - 1][itemSecond] == 'R') {
				System.out.println("N Tripped in greens, i=" + i);
				north = true;
			} else {
				north = false;
			}

			// Determine south
			if (itemFirst == this.size - 1) {
				south = false;
			} else if (Character.isDigit(board.array[itemFirst + 1][itemSecond])
					|| board.array[itemFirst + 1][itemSecond] == 'R') {
				System.out.println("S Tripped in greens, i=" + i);
				south = true;
			} else {
				south = false;
			}

			// Determine east
			if (itemSecond == this.size - 1) {
				east = false;
			} else if (Character.isDigit(board.array[itemFirst][itemSecond + 1])
					|| board.array[itemFirst][itemSecond + 1] == 'R') {
				System.out.println("E Tripped in greens, i=" + i);
				east = true;
			} else {
				east = false;
			}

			// Determine west
			if (itemSecond == 0) {
				west = false;
			} else if (Character.isDigit(board.array[itemFirst][itemSecond - 1])
					|| board.array[itemFirst][itemSecond - 1] == 'R') {
				System.out.println("W Tripped in greens, i=" + i);
				west = true;
			} else {
				west = false;
			}

			// Now, make a child for each direction
			if (north)
				children.addLast(createChild(board, itemFirst, itemSecond, 'N', 'G')); // add N, S, E, W
			if (south)
				children.addLast(createChild(board, itemFirst, itemSecond, 'S', 'G')); // children if
			if (east)
				children.addLast(createChild(board, itemFirst, itemSecond, 'E', 'G')); // they exist
			if (west)
				children.addLast(createChild(board, itemFirst, itemSecond, 'W', 'G'));
		}

		for (int i = 0; i < children.size(); i++) {
			displayBoard(children.get(i));
		}

		return children;
	}

	// Method creates a child of a board by swapping empty slot in a
	// given direction
	private Board createChild(Board board, int i, int j, char direction, char color) {
		Board child = copy(board); // create copy of board

		if (direction == 'N') // swap empty slot to north
		{
			char value = child.array[i][j];
			child.array[i][j] = child.array[i - 1][j];
			child.array[i - 1][j] = value;
		} else if (direction == 'S') // swap empty slot to south
		{
			char value = child.array[i][j];
			child.array[i][j] = child.array[i + 1][j];
			child.array[i + 1][j] = value;
		} else if (direction == 'E') // swap empty slot to east
		{
			char value = child.array[i][j];
			child.array[i][j] = child.array[i][j + 1];
			child.array[i][j + 1] = value;
		} else // swap empty slot to west
		{
			char value = child.array[i][j];
			child.array[i][j] = child.array[i][j - 1];
			child.array[i][j - 1] = value;
		}

		child.gvalue = board.gvalue + 1; // parent path cost plus one

		if (this.heuristicOption == 1) {
			child.hvalue = mismatchHeuristic(child); // heuristic value of child
		} else if (this.heuristicOption == 2) {
			child.hvalue = cityDistanceHeuristic(child);
		}

		if (this.evaluationOption == 1) {
			child.fvalue = child.hvalue;
		} else if (this.evaluationOption == 2) {
			child.fvalue = child.gvalue;
		} else if (this.evaluationOption == 3) {
			child.fvalue = child.gvalue + child.hvalue; // gvalue plus hvalue
		}

		child.parent = board; // assign parent to child

		return child; // return child
	}

	// Method computes heuristic value of board based on misplaced values
	private int mismatchHeuristic(Board board) {
		int value = 0; // initial heuristic value

		for (int i = 0; i < size; i++) // go thru board and
			for (int j = 0; j < size; j++) // count misplaced values
				if (board.array[i][j] != goal.array[i][j])
					value += 1;

		return value; // return heuristic value
	}

	/*
	 * //Method computes heuristic value of board //Heuristic value is the sum of
	 * distances of misplaced values private int heuristic(Board board) { //initial
	 * heuristic value int value = 0;
	 * 
	 * //go thru board for (int i = 0; i < size; i++) for (int j = 0; j < size; j++)
	 * //if value mismatches in goal board if (board.array[i][j] !=
	 * goal.array[i][j]) { //locate value in goal board int x = 0, y = 0; boolean
	 * found = false; for (x = 0; x < size; x++) { for (y = 0; y < size; y++) if
	 * (goal.array[x][y] == board.array[i][j]) { found = true; break; } if (found)
	 * break; }
	 * 
	 * //find city distance between two locations value += (int)Math.abs(x-i) +
	 * (int)Math.abs(y-j); }
	 * 
	 * //return heuristic value return value; }
	 */

	private int cityDistanceHeuristic(Board board) {
		// initial heuristic value
		int value = 0;

		// go thru board
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				// if value mismatches in goal board
				if (board.array[i][j] != goal.array[i][j]) { // locate value in goal board
					int x = 0, y = 0;
					boolean found = false;
					for (x = 0; x < size; x++) {
						for (y = 0; y < size; y++)
							if (goal.array[x][y] == board.array[i][j]) {
								found = true;
								break;
							}
						if (found)
							break;
					}

					// find city distance between two locations
					value += (int) Math.abs(x - i) + (int) Math.abs(y - j);
				}

		// return heuristic value
		return value;
	}

	// Method locates the board with minimum fvalue in a list of boards
	private int selectBest(LinkedList<Board> list) {
		int minValue = list.get(0).fvalue; // initialize minimum
		int minIndex = 0; // value and location

		for (int i = 0; i < list.size(); i++) {
			int value = list.get(i).fvalue;
			if (value < minValue) // updates minimums if
			{ // board with smaller
				minValue = value; // fvalue is found
				minIndex = i;
			}
		}

		return minIndex; // return minimum location
	}

	// Method creates copy of a board
	private Board copy(Board board) {
		return new Board(board.array, size);
	}

	// Method decides whether a board is goal
	private boolean goal(Board board) {
		return identical(board, goal); // compare board with goal
	}

	// Method decides whether a board exists in a list
	private boolean exists(Board board, LinkedList<Board> list) {
		for (int i = 0; i < list.size(); i++) // compare board with each
			if (identical(board, list.get(i))) // element of list
				return true;

		return false;
	}

	// Method finds location of a board in a list
	private int find(Board board, LinkedList<Board> list) {
		for (int i = 0; i < list.size(); i++) // compare board with each
			if (identical(board, list.get(i))) // element of list
				return i;

		return -1;
	}

	// Method decides whether two boards are identical
	private boolean identical(Board p, Board q) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (p.array[i][j] != q.array[i][j])
					return false; // if there is a mismatch then false

		return true; // otherwise true
	}

	// Method displays path from initial to current board
	private void displayPath(Board board) {
		LinkedList<Board> list = new LinkedList<Board>();

		Board pointer = board; // start at current board

		while (pointer != null) // go back towards initial board
		{
			list.addFirst(pointer); // add boards to beginning of list

			pointer = pointer.parent; // keep going back
		}
		// print boards in list
		for (int i = 0; i < list.size(); i++)
			displayBoard(list.get(i));
	}

	// Method displays board
	private void displayBoard(Board board) {
		this.swaps++;
		for (int i = 0; i < size; i++) // print each element of board
		{
			for (int j = 0; j < size; j++)
				System.out.print(board.array[i][j] + " ");
			System.out.println();
		}
		System.out.println();

		this.OutputWriter.writeBoard(board.array, this.size);
	}

//	public InfoPasser getOutputs() {
//		InfoPasser passer = new InfoPasser(this.runTime, this.swaps, this.boardsSearched);
//		return passer;
//	}

	// Don't even need to pass in size, size is a variable
	private Board findGoalBoard() {
		char[][] goalBoard = new char[this.size][this.size];

		// I'm 100% sure there is a better way to sort this 2d array...
		ArrayList<Character> elements = new ArrayList<Character>();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				elements.add(this.initial.array[i][j]);
			}
		}

		int greenCount = 0;
		int redCount = 0;

		ArrayList<Character> toRemove = new ArrayList<Character>();

		for (Character item : elements) {
			if (item.equals('G')) {
				greenCount++;
//				elements.remove(item);
				toRemove.add(item);
			}
			if (item.equals('R')) {
				redCount++;
//				elements.remove(item);
				toRemove.add(item);
			}
		}

		elements.removeAll(toRemove);

		// Size, or size -1?
		int startOfRedsIndexI = this.size - 1;
		int startOfRedsIndexJ = this.size - 1;

		// Put G's in
		for (int i = this.size - 1; i > -1; i--) {
			for (int j = this.size - 1; j > -1; j--) {
				startOfRedsIndexI = i;
				startOfRedsIndexJ = j;
				goalBoard[i][j] = 'G';

				greenCount--;
				if (greenCount == 0) {
					break;
				}
			}
			if (greenCount == 0) {
				break;
			}
		}

//		int startOfNumbersIndexI = this.size - 1;
//		int startOfNumbersIndexJ = this.size - 1;

		boolean startedAtJ = false;
		// Put R's in
		for (int i = startOfRedsIndexI; i > -1; i--) {

			// You have to start at an offset J, only the first time you run the program. So
			// you have to make a boolean to check whether or not you've done this.
			if (startedAtJ == false) {
				startedAtJ = true;
				for (int j = startOfRedsIndexJ - 1; j > -1; j--) {
//					startOfNumbersIndexI = i;
//					startOfNumbersIndexJ = j;
					goalBoard[i][j] = 'R';

					redCount--;
					if (redCount == 0) {
						break;
					}
				}

			} else {
				for (int j = this.size - 1; j > -1; j--) {
//					startOfNumbersIndexI = i;
//					startOfNumbersIndexJ = j;
					goalBoard[i][j] = 'R';

					redCount--;
					if (redCount == 0) {
						break;
					}
				}
			}
			if (redCount == 0) {
				break;
			}
		}

		// Pull out numbers, sorted.
		// At this point, you can assume everything left in the array is a number.
		// Value for ;, the first item after numbers
		int lowest = 58;
		Character lowestChar = ';';
		int lowestIndex = -1;
		ArrayList<Character> sortedNumbers = new ArrayList<Character>();

//		System.out.println("Printing out elements going into number sorting: ");
//		for (int i = 0; i < elements.size(); i++) {
//			System.out.println(elements.get(i));
//		}
		while (!elements.isEmpty()) {
//			for (Character item : elements) {
//				if (item.charValue() < lowest) {
//					lowest = item.charValue();
//					lowestChar = item;
//				}
//			}
//			elements.remove(lowestChar);
//			sortedNumbers.add(lowestChar);
//			lowest = 58;
			for (int i = 0; i < elements.size(); i++) {
				int comparisonValue = Character.valueOf(elements.get(i)).compareTo(lowestChar);
//				System.out.println(comparisonValue);
				if (comparisonValue < 0) {
					lowestChar = elements.get(i);
					lowestIndex = i;
				}
			}
			sortedNumbers.add(lowestChar);
			elements.remove(lowestIndex);
			lowestIndex = -1;
			lowestChar = ';';
		}

//		System.out.println("Printing out the sorted numbers array: ");
//		for (int i = 0; i < sortedNumbers.size(); i++) {
//			System.out.println(sortedNumbers.get(i));
//		}

		// Have to track this separately, since it's not iterating in the same way as
		// the array iterations
//		int placementIndex = 0;
//		for (int i = 0; i < this.size; i++) {
//			if (placementIndex == sortedNumbers.size()) {
//				break;
//			}
//			for (int j = 0; i < this.size; j++) {
//				if (placementIndex + 1 == sortedNumbers.size()) {
//					goalBoard[i][j] = sortedNumbers.get(placementIndex);
//					placementIndex++;
//					break;
//				} else {
//					goalBoard[i][j] = sortedNumbers.get(placementIndex);
//					placementIndex++;
//
//				}
//			}
//		}
		for (int i = 0; i < this.size; i++) {
			if (sortedNumbers.isEmpty()) {
				break;
			}
			for (int j = 0; j < this.size; j++) {
				if (sortedNumbers.isEmpty()) {
					break;
				} else {
					goalBoard[i][j] = sortedNumbers.get(0);
					sortedNumbers.remove(0);
				}
			}
		}

		// Print goalBoard for testing purposes
		System.out.println();
		System.out.println("Goal board:");
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				System.out.print(goalBoard[i][j]);
			}
			System.out.println();
		}

		return new Board(goalBoard, this.size);
	}
}
