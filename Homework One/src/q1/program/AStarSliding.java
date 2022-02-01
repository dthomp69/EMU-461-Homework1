package q1.program;

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

	// Constructor of SlidingAstar class
	public AStarSliding(char[][] initial, char[][] goal, int size, int evaluationOption, int heuristicOption) {
		this.size = size; // set size of board
		this.initial = new Board(initial, size); // create initial board
		this.goal = new Board(goal, size); // create goal board
		this.runTime = 0;
		this.gapTime = 0;
		this.boardsSearched = 0;
		this.swaps = -1; // Starts at -1, to avoid counting the initial board

		this.evaluationOption = evaluationOption;
		this.heuristicOption = heuristicOption;

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

	// Method creates children of a board
	private LinkedList<Board> generate(Board board) {
		int i = 0, j = 0;
		boolean found = false;

		for (i = 0; i < size; i++) // find location of empty slot
		{ // of board
			for (j = 0; j < size; j++)
				// Need to be 0 or '0'?
				if (board.array[i][j] == '0') {
					found = true;
					break;
				}

			if (found)
				break;
		}

		boolean north, south, east, west; // decide whether empty slot
		north = i == 0 ? false : true; // has N, S, E, W neighbors
		south = i == size - 1 ? false : true;
		east = j == size - 1 ? false : true;
		west = j == 0 ? false : true;

		LinkedList<Board> children = new LinkedList<Board>();// list of children

		if (north)
			children.addLast(createChild(board, i, j, 'N')); // add N, S, E, W
		if (south)
			children.addLast(createChild(board, i, j, 'S')); // children if
		if (east)
			children.addLast(createChild(board, i, j, 'E')); // they exist
		if (west)
			children.addLast(createChild(board, i, j, 'W'));

		return children; // return children
	}

	// Method creates a child of a board by swapping empty slot in a
	// given direction
	private Board createChild(Board board, int i, int j, char direction) {
		Board child = copy(board); // create copy of board

		if (direction == 'N') // swap empty slot to north
		{
			child.array[i][j] = child.array[i - 1][j];
			child.array[i - 1][j] = '0';
		} else if (direction == 'S') // swap empty slot to south
		{
			child.array[i][j] = child.array[i + 1][j];
			child.array[i + 1][j] = '0';
		} else if (direction == 'E') // swap empty slot to east
		{
			child.array[i][j] = child.array[i][j + 1];
			child.array[i][j + 1] = '0';
		} else // swap empty slot to west
		{
			child.array[i][j] = child.array[i][j - 1];
			child.array[i][j - 1] = '0';
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
	}

	public InfoPasser getOutputs() {
		InfoPasser passer = new InfoPasser(this.runTime, this.swaps, this.boardsSearched);
		return passer;
	}
}
