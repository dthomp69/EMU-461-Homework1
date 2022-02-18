package q4.program;

//This program solves sudoku puzzle using recursion, backtracking, and
//constraint satisfication.
public class Sudoku {
	private int[][] board; // sudoku board
	private int puzzleSize;

	// Constructor of Sudoku class
	public Sudoku(int[][] board, int puzzleSize) {
		this.board = board; // set initial board
		this.puzzleSize = puzzleSize;
	}

	// Method solves a given puzzle
	public void solve() { // fill the board starting
		if (fill(0)) // at the beginning
			display(); // if success display board
		else
			System.out.println("No solution"); // otherwise failure
	}

	// Method fills a board using recursion/backtracking.
	// It fills the board starting at a given location
	private boolean fill(int location) {
		int x = location / this.puzzleSize; // find x,y coordinates of
		int y = location % this.puzzleSize; // current location
		int value;

		if (location > (this.puzzleSize * this.puzzleSize) - 1) {// if location exceeds board
			return true; // whole board is filled

//		else if (board[x][y] != 0) // if location already has value
			// pretty sure you check for -1, which is b case, here like this, to see if this
			// is a spot you should put something in.
//		} else if (board[x][y] != -2 && board[x][y] != -3 && board[x][y] != -4 && board[x][y] == -1) { // if location
		} else if (board[x][y] == -1 || board[x][y] > 0) { // if location

			// already has
			// value
			return fill(location + 1); // fill the rest of board

		} else // otherwise
		{
			int startingValue = board[x][y];
//			for (value = 1; value <= this.puzzleSize; value++) {
//				board[x][y] = value; // try numbers 1-9 at the location
//
//				if (check(x, y) && fill(location + 1))
//					return true; // if number causes no conflicts and the rest
//			} // of board can be filled then done
			if (board[x][y] == -2) {
				for (value = 1; value <= this.puzzleSize; value++) {
					if (value % 2 == 1) {
						board[x][y] = value; // try numbers 1-9 at the location

						if (check(x, y) && fill(location + 1))
							return true; // if number causes no conflicts and the rest
					} // of board can be filled then done
				}
			} else if (board[x][y] == -3) {
				for (value = 1; value <= this.puzzleSize; value++) {
					if (value % 2 == 0) {
						board[x][y] = value; // try numbers 1-9 at the location

						if (check(x, y) && fill(location + 1))
							return true; // if number causes no conflicts and the rest
					} // of board can be filled then done
				}
			} else if (board[x][y] == -4) {
				for (value = 1; value <= this.puzzleSize; value++) {
					board[x][y] = value; // try numbers 1-9 at the location

					if (check(x, y) && fill(location + 1))
						return true; // if number causes no conflicts and the rest
				} // of board can be filled then done
			}

			board[x][y] = startingValue; // if none of numbers 1-9 work then
			return false; // empty the location and back track
		}
	}

	// Method checks whether a value at a given location causes any conflicts
	private boolean check(int x, int y) {
		int a, b, i, j;

		for (j = 0; j < this.puzzleSize; j++) // check value causes conflict in row
			if (j != y && board[x][j] == board[x][y])
				return false;

		for (i = 0; i < this.puzzleSize; i++) // check value causes conflict in column
			if (i != x && board[i][y] == board[x][y])
				return false;

//		a = (x / 3) * 3;
//		b = (y / 3) * 3; // check value causes conflict in
//		for (i = 0; i < 3; i++) // 3x3 region
//			for (j = 0; j < 3; j++)
//				if ((a + i != x) && (b + j != y) && board[a + i][b + j] == board[x][y])
//					return false;

		return true;
	}

	// Method displays a board
	private void display() {
		for (int i = 0; i < this.puzzleSize; i++) {
			for (int j = 0; j < this.puzzleSize; j++)
				System.out.print(board[i][j] + " ");
			System.out.println();
		}
	}
}