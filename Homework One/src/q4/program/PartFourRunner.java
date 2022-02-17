package q4.program;

public class PartFourRunner {

	public static void main(String[] args) {
		UserInputs input = new UserInputs();
		input.askForInputs();

		int puzzleSize = input.getPuzzleSize();
		char[][] initialChar = new char[puzzleSize][puzzleSize];
		initialChar = input.getInitialBoardChar();

		int[][] convertedBoard = new int[puzzleSize][puzzleSize];

		// Code to print initialChar board for testing
//		for (int i = 0; i < initialChar.length; i++) {
//			for (int j = 0; j < initialChar.length; j++) {
//				System.out.print(initialChar[i][j]);
//			}
//			System.out.println();
//		}

		// Convert char board to int board using following key:
		/*
		 * b=-1, o=-2, e=-3, w=-4,
		 */
		for (int i = 0; i < initialChar.length; i++) {
			for (int j = 0; j < initialChar.length; j++) {
				if (initialChar[i][j] == 'b') {
					convertedBoard[i][j] = -1;
				} else if (initialChar[i][j] == 'o') {
					convertedBoard[i][j] = -2;
				} else if (initialChar[i][j] == 'e') {
					convertedBoard[i][j] = -3;
				} else if (initialChar[i][j] == 'w') {
					convertedBoard[i][j] = -4;
				} else {
					convertedBoard[i][j] = Character.getNumericValue(initialChar[i][j]);
				}
			}
		}

		// Code to print out the converted to int board for testing purposes
//		for (int i = 0; i < convertedBoard.length; i++) {
//			for (int j = 0; j < convertedBoard.length; j++) {
//				System.out.print(convertedBoard[i][j] + "   ");
//			}
//			System.out.println();
//		}

		Sudoku algo = new Sudoku(convertedBoard, puzzleSize);
		algo.solve();

	}

}
