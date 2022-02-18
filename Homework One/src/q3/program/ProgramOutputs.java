package q3.program;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import q1.program.InfoPasser;

public class ProgramOutputs {
	private String fileName;

	public ProgramOutputs() {
		this.fileName = "";
	}

	public void writeOutputs(String whoWon, int computerPoints, int playerPoints) {
//		System.out.println("RunTime (Nanoseconds): " + outputs.getRunTime());
//		System.out.println("RunTime (Milliseconds): " + outputs.getRunTime() / 1000000);
//		System.out.println("Swaps: " + outputs.getSwaps());
//		System.out.println("BoardsSearched: " + outputs.getBoardsSearched());

		try {
//			System.out.println("The file to write to is: " + this.fileName);
			File file = new File(System.getProperty("user.dir") + "\\src\\q3\\output\\" + this.fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(whoWon + " wins!");
			bufferedWriter.newLine();
			bufferedWriter.write("Computer points: " + computerPoints);
			bufferedWriter.newLine();
			bufferedWriter.write("Player points: " + playerPoints);
			bufferedWriter.newLine();

			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeBoard(char[][] array, int size) {
		try {
//			System.out.println("The file to write to is: " + this.fileName);
			File file = new File(System.getProperty("user.dir") + "\\src\\q3\\output\\" + this.fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < size; i++) // print each element of board
			{
				for (int j = 0; j < size; j++) {
//					bufferedWriter.write(array[i][j] + " ");
					bufferedWriter.write(String.valueOf(array[i][j]) + " ");
				}
				bufferedWriter.newLine();
			}
			bufferedWriter.newLine();

			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void determineOutputFile() {
		Scanner scanner = new Scanner(System.in);

		boolean validatingFileName = true;

		while (validatingFileName) {
			System.out.println("Please input the name of the file you'd like to write to:");
			String userInput = scanner.nextLine();
			this.fileName = userInput;

			try {
				File file = new File(System.getProperty("user.dir") + "\\src\\q3\\output\\" + userInput);

				if (!file.exists()) {
					file.createNewFile();
				}

				validatingFileName = false;

			} catch (IOException e) {
				System.out.println("Couldn't create or find output file.");
			}
		}
//		scanner.close();
	}

}
