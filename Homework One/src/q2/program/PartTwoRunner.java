package q2.program;

public class PartTwoRunner {

	public static void main(String[] args) {
		UserInputs inputs = new UserInputs();

		inputs.askForInputs();

		int puzzleSize = inputs.getPuzzleSize();

		int[][] initial = inputs.getInitialBoard();

		char[][] initialChar = inputs.getInitialBoardChar();

		AStarSliding algorithm = new AStarSliding(initialChar, puzzleSize);
		algorithm.solve();

	}

}
