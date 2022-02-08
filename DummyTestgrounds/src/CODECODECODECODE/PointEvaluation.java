package CODECODECODECODE;

public class PointEvaluation {

	public static void main(String[] args) {

		char EMPTY; // empty slot
		EMPTY = ' ';

		class Board {
			private char[][] array; // board array

			// Constructor of Board class
			private Board(int size) {
				array = new char[size][size]; // create array

				for (int i = 0; i < size; i++) // fill array with empty slots
					for (int j = 0; j < size; j++)
						array[i][j] = EMPTY;
			}
		}
		// TODO Auto-generated method stub
		char[][] testBoard = { { 'O', 'O', 'X', 'O' }, { 'X', 'O', 'X', 'O' }, { 'X', 'X', 'X', 'O' },
				{ 'O', 'X', 'O', 'X' } };

		System.out.println("Board: ");

		for (int i = 0; i < testBoard.length; i++) {
			for (int j = 0; j < testBoard.length; j++) {
				System.out.print(testBoard[i][j]);
				// Count computer points:
				if (testBoard[i][j] == 'X') {
				}

				// Count player points:
			}
			System.out.println();
		}

		Board testBoardItself = new Board(4);
		testBoardItself.array = testBoard;
		int evaluationOutput = pointEvaluation(testBoardItself);

	}

	private int pointEvaluation(Board board) {
		int computerScore;
		int computerP;
		int computerQ;

		int playerScore;
		int playerP;
		int playerQ;

		char[][] boardArray = board.array;

		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray.length; j++) {
				// Count computer points:
				if (boardArray[i][j] == 'X') {
				}

				// Count player points:
			}
		}

		return 0;
	}
}
