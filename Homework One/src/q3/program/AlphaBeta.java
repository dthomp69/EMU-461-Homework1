package q3.program;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

//This program plays tic-tac game using min-max, depth limit,
//board evaluation, and alpha-beta pruning
public class AlphaBeta {
	private final char EMPTY = ' '; // empty slot
	private final char COMPUTER = 'X'; // computer
	private final char PLAYER = '0'; // player
	private final int MIN = 0; // min level
	private final int MAX = 1; // max level
	private final int LIMIT = 6; // depth limit

	// Board class (inner class)
	private class Board {
		private char[][] array; // board array

		// Constructor of Board class
		private Board(int size) {
			array = new char[size][size]; // create array

			for (int i = 0; i < size; i++) // fill array with empty slots
				for (int j = 0; j < size; j++)
					array[i][j] = EMPTY;
		}
	}

	private Board board; // game board
	private int size; // size of board

	private ProgramOutputs outputs;

	// Constructor of AlphaBeta class
	public AlphaBeta(int size) {
		this.board = new Board(size); // create game board
		this.size = size; // set board size

		ProgramOutputs outputs = new ProgramOutputs();
		this.outputs = outputs;
		this.outputs.determineOutputFile();
	}

	// Method plays game
	public void play() {
		while (true) // computer and player take turns
		{
			board = playerMove(board); // player makes a move

			if (playerWin(board)) // if player wins then game is over
			{
				System.out.println("Player wins");
				System.out.println("Final Computer Score: " + pointEvaluationComputer(board));
				System.out.println("Final Player Score: " + pointEvaluationPlayer(board));
				this.outputs.writeOutputs("Player", pointEvaluationComputer(board), pointEvaluationPlayer(board));
				break;
			}

			if (computerWin(board)) // if computer wins then game is over
			{
				System.out.println("Computer wins");
				System.out.println("Final Computer Score: " + pointEvaluationComputer(board));
				System.out.println("Final Player Score: " + pointEvaluationPlayer(board));
				this.outputs.writeOutputs("Computer", pointEvaluationComputer(board), pointEvaluationPlayer(board));

				break;
			}

			if (draw(board)) // if draw then game is over
			{
				System.out.println("Draw");
				System.out.println("Final Computer Score: " + pointEvaluationComputer(board));
				System.out.println("Final Player Score: " + pointEvaluationPlayer(board));
				this.outputs.writeOutputs("Noone", pointEvaluationComputer(board), pointEvaluationPlayer(board));
				break;
			}

			board = computerMove(board); // computer makes a move

			if (computerWin(board)) // if computer wins then game is over
			{
				System.out.println("Computer wins");
				System.out.println("Final Computer Score: " + pointEvaluationComputer(board));
				System.out.println("Final Player Score: " + pointEvaluationPlayer(board));
				this.outputs.writeOutputs("Computer", pointEvaluationComputer(board), pointEvaluationPlayer(board));
				break;
			}

			if (playerWin(board)) // if player wins then game is over
			{
				System.out.println("Player wins");
				System.out.println("Final Computer Score: " + pointEvaluationComputer(board));
				System.out.println("Final Player Score: " + pointEvaluationPlayer(board));
				this.outputs.writeOutputs("Player", pointEvaluationComputer(board), pointEvaluationPlayer(board));
				break;
			}

			if (draw(board)) // if draw then game is over
			{
				System.out.println("Draw");
				System.out.println("Final Computer Score: " + pointEvaluationComputer(board));
				System.out.println("Final Player Score: " + pointEvaluationPlayer(board));
				this.outputs.writeOutputs("Noone", pointEvaluationComputer(board), pointEvaluationPlayer(board));
				break;
			}
		}
	}

	// Method lets the player make a move
	private Board playerMove(Board board) {
		System.out.print("Player move: "); // prompt player

		Scanner scanner = new Scanner(System.in); // read player's move
		int i = scanner.nextInt();
		int j = scanner.nextInt();

		board.array[i][j] = PLAYER; // place player symbol

		displayBoard(board); // diplay board

		return board; // return updated board
	}

	// Method determines computer's move
	private Board computerMove(Board board) { // generate children of board
		LinkedList<Board> children = generate(board, COMPUTER);

		int maxIndex = -1;
		int maxValue = Integer.MIN_VALUE;
		// find the child with
		for (int i = 0; i < children.size(); i++) // largest minmax value
		{
			int currentValue = minmax(children.get(i), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
			if (currentValue > maxValue) {
				maxIndex = i;
				maxValue = currentValue;
			}
		}

		Board result = children.get(maxIndex); // choose the child as next move

		System.out.println("Computer move:");

		displayBoard(result); // print next move

		return result; // retun updated board
	}

	// Method computes minmax value of a board
	private int minmax(Board board, int level, int depth, int alpha, int beta) {
		if (computerWin(board) || playerWin(board) || draw(board) || depth >= LIMIT)
			return evaluate(board); // if board is terminal or depth limit is reached
		else // evaluate board
		{
			if (level == MAX) // if board is at max level
			{
				LinkedList<Board> children = generate(board, COMPUTER);
				// generate children of board
				int maxValue = Integer.MIN_VALUE;

				for (int i = 0; i < children.size(); i++) { // find minmax values of children
					int currentValue = minmax(children.get(i), MIN, depth + 1, alpha, beta);

					if (currentValue > maxValue) // find maximum of minmax values
						maxValue = currentValue;

					if (maxValue >= beta) // if maximum exceeds beta stop
						return maxValue;

					if (maxValue > alpha) // if maximum exceeds alpha update alpha
						alpha = maxValue;
				}

				return maxValue; // return maximum value
			} else // if board is at min level
			{
				LinkedList<Board> children = generate(board, PLAYER);
				// generate children of board
				int minValue = Integer.MAX_VALUE;

				for (int i = 0; i < children.size(); i++) { // find minmax values of children
					int currentValue = minmax(children.get(i), MAX, depth + 1, alpha, beta);

					if (currentValue < minValue) // find minimum of minmax values
						minValue = currentValue;

					if (minValue <= alpha) // if minimum is less than alpha stop
						return minValue;

					if (minValue < beta) // if minimum is less than beta update beta
						beta = minValue;
				}

				return minValue; // return minimum value
			}
		}
	}

	// Method generates children of board using a symbol
	private LinkedList<Board> generate(Board board, char symbol) {
		LinkedList<Board> children = new LinkedList<Board>();
		// empty list of children
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) // go thru board
				if (board.array[i][j] == EMPTY) { // if slot is empty
					Board child = copy(board); // put the symbol and
					child.array[i][j] = symbol; // create child board
					children.addLast(child);
				}

		return children; // return list of children
	}

	private boolean checkFull(Board board) {
		boolean isFull = true;
		for (int i = 0; i < board.array.length; i++) {
			for (int j = 0; j < board.array.length; j++) {
				if (board.array[i][j] == ' ') {
					isFull = false;
				}

			}
		}
		return isFull;
	}

	public boolean testCheckFull() {
		char[][] testBoardArray = { { 'O', 'O', 'X', 'O' }, { 'X', 'O', 'X', 'O' }, { 'X', 'X', 'X', 'O' },
				{ 'O', 'X', 'O', 'X' } };
		Board testBoard = new Board(4);
		testBoard.array = testBoardArray;

		return checkFull(testBoard);
	}

	public boolean testCheckFull2() {
		char[][] testBoardArray = { { ' ', 'O', 'X', 'O' }, { 'X', 'O', 'X', 'O' }, { 'X', 'X', 'X', 'O' },
				{ 'O', 'X', 'O', ' ' } };
		Board testBoard = new Board(4);
		testBoard.array = testBoardArray;

		return checkFull(testBoard);
	}

	// Method checks whether computer wins
	private boolean computerWin(Board board) {
		if (checkFull(board)) {
			if (pointEvaluationPlayer(board) < pointEvaluationComputer(board)) {
				return true;
			}
		}
		return false; // check computer wins
	} // somewhere in board

	// Method checks whether player wins
	private boolean playerWin(Board board) {
		if (checkFull(board)) {
			if (pointEvaluationPlayer(board) > pointEvaluationComputer(board)) {
				return true;
			}
		}
		return false; // check player wins
	} // somewhere in board

	// Method checks whether board is draw
	private boolean draw(Board board) {
		if (checkFull(board)) {
			if (pointEvaluationPlayer(board) == pointEvaluationComputer(board)) {
				return true;
			}
		}
		return false; // check player wins
	}

	// Method checks whether a board is full
	private boolean full(Board board) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board.array[i][j] == EMPTY)
					return false;

		return true;
	}

	// Method makes copy of a board
	private Board copy(Board board) {
		Board result = new Board(size);

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				result.array[i][j] = board.array[i][j];

		return result;
	}

	// Method displays a board
	private void displayBoard(Board board) {
		this.outputs.writeBoard(this.board.array, size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				System.out.print(board.array[i][j]);
			System.out.println();
		}
	}

	// Method evaluates a board
	private int evaluate(Board board) {
		// TODO: determine proper winn and loss values. Raising them to be within bounds
		// of the point evaluation function.
		if (computerWin(board)) // utility is 4*size if computer wins
			return 400 * size;
		else if (playerWin(board)) // utility is -4*size if player wins
			return -400 * size;
		else if (draw(board)) // utility is 3*size if draw
//			return 3 * size;
			return 0;
		else
//			return count(board, COMPUTER) - count(board, PLAYER);
			return pointEvaluationComputer(board) - pointEvaluationPlayer(board);
	} // utility is difference between computer
		// and player winnings if depth limit
		// is reached

	private int pointEvaluationComputer(Board board) {
		int computerScore = 0;
		int computerP = 0;
		int computerQ = 0;

		int playerScore = 0;
		int playerP = 0;
		int playerQ = 0;

		char[][] boardArray = board.array;

		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray.length; j++) {
				// Count computer points:
				if (boardArray[i][j] == 'X') {
					// Check vertical points
					// TODO: Should be checking things above it?
					// TODO: how to properly check verticals?
					if (!(i + 1 >= boardArray.length)) {
						if (boardArray[i + 1][j] == 'X') {
							computerP++;
							if (!(i + 2 >= boardArray.length)) {
								if (boardArray[i + 2][j] == 'X') {
									computerQ++;
								}
							}
						}
					}
					// Check horizontal points
					if (!(j + 1 >= boardArray.length)) {
						if (boardArray[i][j + 1] == 'X') {
							computerP++;
							if (!(j + 2 >= boardArray.length)) {
								if (boardArray[i][j + 2] == 'X') {
									computerQ++;
								}
							}
						}
					}
				}

				// Count player points:
				if (boardArray[i][j] == 'O') {
					// Check vertical points
					// TODO: Should be checking things above it?
					// TODO: how to properly check verticals?
					if (!(i + 1 >= boardArray.length)) {
						if (boardArray[i + 1][j] == 'O') {
							playerP++;
							if (!(i + 2 >= boardArray.length)) {
								if (boardArray[i + 2][j] == 'O') {
									playerQ++;
								}
							}
						}
					}
					// Check horizontal points
					if (!(j + 1 >= boardArray.length)) {
						if (boardArray[i][j + 1] == 'O') {
							playerP++;
							if (!(j + 2 >= boardArray.length)) {
								if (boardArray[i][j + 2] == 'O') {
									playerQ++;
								}
							}
						}
					}
				}
			}
		}

		return 2 * computerP + 3 * computerQ;
	}

	private int pointEvaluationPlayer(Board board) {

		int playerP = 0;
		int playerQ = 0;

		char[][] boardArray = board.array;

		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray.length; j++) {

				// Count player points:
				if (boardArray[i][j] == '0') {
					// Check vertical points
					// TODO: Should be checking things above it?
					// TODO: how to properly check verticals?
					if (!(i + 1 >= boardArray.length)) {
						if (boardArray[i + 1][j] == '0') {
							playerP++;
							if (!(i + 2 >= boardArray.length)) {
								if (boardArray[i + 2][j] == '0') {
									playerQ++;
								}
							}
						}
					}
					// Check horizontal points
					if (!(j + 1 >= boardArray.length)) {
						if (boardArray[i][j + 1] == '0') {
							playerP++;
							if (!(j + 2 >= boardArray.length)) {
								if (boardArray[i][j + 2] == '0') {
									playerQ++;
								}
							}
						}
					}
				}
			}
		}

		return 2 * playerP + 3 * playerQ;
	}

	public void testPointEvaluation() {
		char[][] testBoardArray = { { 'O', 'O', 'X', 'O' }, { 'X', 'O', 'X', 'O' }, { 'X', 'X', 'X', 'O' },
				{ 'O', 'X', 'O', 'X' } };
		Board testBoard = new Board(4);
		testBoard.array = testBoardArray;

		// Print the board to verify
		for (int i = 0; i < testBoardArray.length; i++) {
			for (int j = 0; j < testBoardArray.length; j++) {
				System.out.print(testBoardArray[i][j]);
			}
			System.out.println();
		}

		int evaluateOutComputer = pointEvaluationComputer(testBoard);
		int evaluateOutPlayer = pointEvaluationPlayer(testBoard);

		System.out.println("Evaluate out computer: " + evaluateOutComputer);
		System.out.println("Evaluate out player: " + evaluateOutPlayer);
	}

	// Method counts possible ways a symbol can win
	private int count(Board board, char symbol) {
		int answer = 0;

		for (int i = 0; i < size; i++)
			if (testRow(board, i, symbol)) // count winning rows
				answer++;

		for (int i = 0; i < size; i++)
			if (testColumn(board, i, symbol)) // count winning columns
				answer++;

		if (testLeftDiagonal(board, symbol)) // count winning left diagonal
			answer++;

		if (testRightDiagonal(board, symbol)) // count winning right diagonal
			answer++;

		return answer;
	}

	// Method checks whether a row is occupied by a symbol or empty
	private boolean testRow(Board board, int i, char symbol) {
		for (int j = 0; j < size; j++)
			if (board.array[i][j] != symbol && board.array[i][j] != EMPTY)
				return false;

		return true;
	}

	// Method checks whether a column is occupied by a symbol or empty
	private boolean testColumn(Board board, int i, char symbol) {
		for (int j = 0; j < size; j++)
			if (board.array[j][i] != symbol && board.array[j][i] != EMPTY)
				return false;

		return true;
	}

	// Method checks whether left diagonal is occupied by a symbol or empty
	private boolean testLeftDiagonal(Board board, char symbol) {
		for (int i = 0; i < size; i++)
			if (board.array[i][i] != symbol && board.array[i][i] != EMPTY)
				return false;

		return true;
	}

	// Method checks whether right diagonal is occupied by a symbol or empty
	private boolean testRightDiagonal(Board board, char symbol) {
		for (int i = 0; i < size; i++)
			if (board.array[i][size - 1 - i] != symbol && board.array[i][size - 1 - i] != EMPTY)
				return false;

		return true;
	}
}
