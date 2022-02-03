package q2.program;

public class PartTwoRunner {

	public static void main(String[] args) {
		UserInputs inputs = new UserInputs();

		inputs.askForInputs();

		int puzzleSize = inputs.getPuzzleSize();

		int[][] initial = inputs.getInitialBoard();
//		int[][] goal = inputs.getGoalBoard();

		char[][] initialChar = inputs.getInitialBoardChar();
//		char[][] goalChar = inputs.getGoalBoardChar();

//		for (int i = 0; i < initialChar.length; i++) {
//			for (int j = 0; j < initialChar.length; j++) {
//				System.out.print(initialChar[i][j]);
//			}
//			System.out.println();
//		}
//		
//		for (int i = 0; i < goalChar.length; i++) {
//			for (int j = 0; j < goalChar.length; j++) {
//				System.out.print(goalChar[i][j]);
//			}
//			System.out.println();
//		}

//		System.out.println(inputs.getPuzzleSize());
//		System.out.println(inputs.getInitialBoard());
//		System.out.println(inputs.getGoalBoard());
//		System.out.println(inputs.getEvaluationOption());
//		System.out.println(inputs.getHeuristicOption());

//		int evaluationOption = inputs.getEvaluationOption();
//		int heuristicOption = inputs.getHeuristicOption();

		AStarSliding algorithm = new AStarSliding(initialChar, puzzleSize);
		algorithm.solve();

		// Write to output file
		// only write outputs to file if you found and read a file
		if (inputs.getReadFile()) {
//			ProgramOutputs outputs = new ProgramOutputs();
//			outputs.writeOutputs(algorithm.getOutputs());

			// Don't have to write outputs like one for this problem, just the boards
			// themselves.
//			algorithm.OutputWriter.writeOutputs(algorithm.getOutputs());
		}
	}

}
