package q2.program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserInputs {

	private Scanner scanner;

	private int puzzleSize;
	private int[][] initialBoard;

	private char[][] initialBoardChar;
	private char[][] goalBoardChar;

	private boolean readFile;

	public UserInputs() {
		this.scanner = new Scanner(System.in);
		this.setReadFile(false);
	}

	public void askForInputs() {
		boolean verifyingInput = true;

		while (verifyingInput) {
			String userInput = "";
			System.out.println("Please input the name of the file you'd like to read from:");
			userInput = this.scanner.nextLine();
			System.out.println("You said: " + userInput);

			boolean validated = validateFileName(userInput);

			if (validated == false) {
				System.out.println("Invalid file name.");
			} else {
				System.out.println("Valid file name.");
				verifyingInput = false;
				getFileInputs(userInput);
			}
		}
	}

	public boolean validateFileName(String fileName) {
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(System.getProperty("user.dir") + "\\src\\part1\\" + fileName);
		try {
			FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\src\\q2\\program\\" + fileName);
		} catch (FileNotFoundException e) {
			return false;
		}

		this.setReadFile(true);

		return true;
	}

	private void getFileInputs(String fileName) {
		int index = 0;

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "\\src\\q2\\program\\" + fileName));
			try {
				// Get the puzzle size
				String puzzleSize = bufferedReader.readLine();
				this.puzzleSize = Integer.parseInt(puzzleSize);

				// remember to construct the int[][]s

				this.initialBoard = new int[this.puzzleSize][this.puzzleSize];
//				this.goalBoard = new int[this.puzzleSize][this.puzzleSize];

				this.initialBoardChar = new char[this.puzzleSize][this.puzzleSize];
				this.goalBoardChar = new char[this.puzzleSize][this.puzzleSize];

				// reads empty line
				bufferedReader.readLine();

				// For the amount of lines equal to puzzle size...
				for (int i = 0; i < this.puzzleSize; i++) {
					String nextLine = bufferedReader.readLine();
					String[] charArraySplit = nextLine.split(" ");
					for (int j = 0; j < this.puzzleSize; j++) {
						char[] charArray = charArraySplit[j].toCharArray();
						this.initialBoardChar[i][j] = charArray[0];
						// TODO: make sure this is the right way to do this
//						this.initialBoard[i][j] = Integer.parseInt(charArraySplit[j]);
					}
				}

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

	public char[][] getInitialBoardChar() {
		return initialBoardChar;
	}

	public void setInitialBoardChar(char[][] initialBoardChar) {
		this.initialBoardChar = initialBoardChar;
	}

	public char[][] getGoalBoardChar() {
		return goalBoardChar;
	}

	public void setGoalBoardChar(char[][] goalBoardChar) {
		this.goalBoardChar = goalBoardChar;
	}

	public boolean getReadFile() {
		return readFile;
	}

	public void setReadFile(boolean readFile) {
		this.readFile = readFile;
	}
}
