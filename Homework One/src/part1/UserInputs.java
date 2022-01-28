package part1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserInputs {

	private Scanner scanner;

	private int puzzleSize;
	private int[][] initialBoard;
	private int[][] goalBoard;
	private int evaluationOption;
	private int heuristicOption;

	public UserInputs() {
		this.scanner = new Scanner(System.in);
	}

	public void askForInputs() {
		String userInput = "";
		System.out.println("Please input the name of the file you'd like to read from:");
		userInput = this.scanner.nextLine();
		System.out.println("You said: " + userInput);

		boolean validated = validateFileName(userInput);

		if (validated == false) {
			System.out.println("Invalid file name.");
		} else {
			System.out.println("Valid file name.");
			getFileInputs(userInput);
		}
	}

	public boolean validateFileName(String fileName) {
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(System.getProperty("user.dir") + "\\src\\part1\\" + fileName);
		try {
			FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\src\\part1\\" + fileName);
		} catch (FileNotFoundException e) {
			return false;
		}

		return true;
	}

	private void getFileInputs(String fileName) {
		int index = 0;

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "\\src\\part1\\" + fileName));
			try {
				String puzzleSize = bufferedReader.readLine();
				this.puzzleSize = Integer.parseInt(puzzleSize);

				// remember to construct the int[][]s

				this.initialBoard = new int[this.puzzleSize][this.puzzleSize];
				this.goalBoard = new int[this.puzzleSize][this.puzzleSize];

				// reads empty line
				bufferedReader.readLine();

				// For the amount of lines equal to puzzle size...
				for (int i = 0; i < this.puzzleSize; i++) {
					String nextLine = bufferedReader.readLine();
					String[] charArraySplit = nextLine.split(" ");
					for (int j = 0; j < this.puzzleSize; j++) {
						this.initialBoard[i][j] = Integer.parseInt(charArraySplit[j]);
					}
				}

				// Printed out the initial array for testing purposes.
//				for(int i=0; i<this.puzzleSize; i++) {
//					for(int j=0; j<this.puzzleSize; j++) {
//						System.out.print(this.initialBoard[i][j]);
//					}
//					System.out.println();
//				}

				// read again to clear the next empty line;
				bufferedReader.readLine();

				// Now read in the goal board
				for (int i = 0; i < this.puzzleSize; i++) {
					String nextLine = bufferedReader.readLine();
					String[] charArraySplit = nextLine.split(" ");
					for (int j = 0; j < this.puzzleSize; j++) {
						this.goalBoard[i][j] = Integer.parseInt(charArraySplit[j]);
					}
				}

				// Printed out the goal array for testing purposes.
//				for(int i=0; i<this.puzzleSize; i++) {
//					for(int j=0; j<this.puzzleSize; j++) {
//						System.out.print(this.goalBoard[i][j]);
//					}
//					System.out.println();
//				}

				// read again to clear the next empty line;
				bufferedReader.readLine();

				// Get evaluation option
				String evaluationOption = bufferedReader.readLine();
				this.evaluationOption = Integer.parseInt(evaluationOption);

				// read again to clear the next empty line;
				bufferedReader.readLine();

				// Get heuristic option
				String heuristicOption = bufferedReader.readLine();
				this.heuristicOption = Integer.parseInt(heuristicOption);

				
				//Remember to close the fileReader.
				bufferedReader.close();

			} catch (IOException e) {
				System.out.println("Error grabbing puzzleSize");
			}

		} catch (FileNotFoundException e) {
		}
	}

	public int getPuzzleSize() {
		return puzzleSize;
	}

	public void setPuzzleSize(int puzzleSize) {
		this.puzzleSize = puzzleSize;
	}

	public int[][] getInitialBoard() {
		return initialBoard;
	}

	public void setInitialBoard(int[][] initialBoard) {
		this.initialBoard = initialBoard;
	}

	public int[][] getGoalBoard() {
		return goalBoard;
	}

	public void setGoalBoard(int[][] goalBoard) {
		this.goalBoard = goalBoard;
	}

	public int getEvaluationOption() {
		return evaluationOption;
	}

	public void setEvaluationOption(int evaluationOption) {
		this.evaluationOption = evaluationOption;
	}

	public int getHeuristicOption() {
		return heuristicOption;
	}

	public void setHeuristicOption(int heuristicOption) {
		this.heuristicOption = heuristicOption;
	}
}
