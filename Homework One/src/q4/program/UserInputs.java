package q4.program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * Article on replacing multiple white spaces with a single white space:
 * (all testing files before this one had only one white space between characters. So I had to find this to be able to read in file 7 & 8.)
 * https://reactgo.com/java-replace-multiple-spaces-string/
 */
public class UserInputs {
	private Scanner scanner;

	private int puzzleSize;

	private int[][] initialBoard;
	private char[][] initialBoardChar;

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
			FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\src\\q4\\program\\" + fileName);
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
					new FileReader(System.getProperty("user.dir") + "\\src\\q4\\program\\" + fileName));
			try {
				// Get the puzzle size
				String puzzleSize = bufferedReader.readLine();
				this.puzzleSize = Integer.parseInt(puzzleSize);

				System.out.println("Puzzle size in UserInputs: " + puzzleSize);

				// remember to construct the int[][]s

//				this.goalBoard = new int[this.puzzleSize][this.puzzleSize];

//				this.setInitialBoard(new int[this.puzzleSize][this.puzzleSize]);
				this.initialBoard = new int[this.puzzleSize][this.puzzleSize];
				this.initialBoardChar = new char[this.puzzleSize][this.puzzleSize];

				// reads empty line
				bufferedReader.readLine();

				// For the amount of lines equal to puzzle size...
				for (int i = 0; i < this.puzzleSize; i++) {
					String nextLine = bufferedReader.readLine();

					nextLine = nextLine.replaceAll("\\s+", " ");

					String[] charArraySplit = nextLine.split(" ");

					for (int j = 0; j < this.puzzleSize; j++) {
						// charArraySplit[j].replace(" ", "");
						char[] charArray = charArraySplit[j].toCharArray();
//						System.out.println("i: " + i + ", j: " + j + ", value of Char[0]: " + charArray[0]);
						if (charArray[0] == 'b') {
							this.initialBoard[i][j] = -1;
						} else if (charArray[0] == 'o') {
							this.initialBoard[i][j] = -2;
						} else if (charArray[0] == 'e') {
							this.initialBoard[i][j] = -3;
						} else if (charArray[0] == 'w') {
							this.initialBoard[i][j] = -4;
						} else {
							try {
								// charArraySplit[j].replace(" ", "");
								int stringInt = Integer.parseInt(charArraySplit[j]);
//								System.out.println("Parsed int: " + stringInt);
								this.initialBoard[i][j] = stringInt;
							} catch (NumberFormatException ex) {

							}
						}

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

	public char[][] getInitialBoardChar() {
		return initialBoardChar;
	}

	public void setInitialBoardChar(char[][] initialBoardChar) {
		this.initialBoardChar = initialBoardChar;
	}

	public boolean getReadFile() {
		return readFile;
	}

	public void setReadFile(boolean readFile) {
		this.readFile = readFile;
	}

	public int[][] getInitialBoard() {
		return initialBoard;
	}

	public void setInitialBoard(int[][] initialBoard) {
		this.initialBoard = initialBoard;
	}

}
